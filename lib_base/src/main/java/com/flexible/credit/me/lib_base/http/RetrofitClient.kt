package com.flexible.credit.me.lib_base.http

import android.content.Context
import com.flexible.credit.me.lib_base.base.Const
import com.flexible.credit.me.lib_base.utils.AESEncryptionUtil
import com.flexible.credit.me.lib_base.utils.DataAssembler
import com.flexible.credit.me.lib_base.utils.JsonUtils
import com.flexible.credit.me.lib_base.utils.LoggerUtils
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = Const.BASE_URL
    private const val APP_ID = "ZDGSR6EC"

    private lateinit var appContext: Context

    // 初始化方法，用于设置 context
    fun init(context: Context) {
        appContext = context.applicationContext // 保存 context 供后续使用
    }

    // 日志拦截器，打印请求和响应的详细信息
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // 自定义拦截器，用于拼接 TokenL0W0TL7 头部参数
    private val tokenInterceptor = Interceptor { chain ->
        val original = chain.request()

        // 保留原始请求的 body，拼接自定义的 TokenL0W0TL7 头部
        val jsonData = DataAssembler.assembleData(appContext).toString()
        val desJosnData = AESEncryptionUtil.encrypt(jsonData, AESEncryptionUtil.AESKEY)
        val tokenL0W0TL7 = APP_ID + desJosnData
        val newRequest = original.newBuilder()
            .header("TokenL0W0TL7", tokenL0W0TL7)
            .method(original.method, original.body) // 保持原请求方法和 body
            .build()

        // 获取原始请求的 body 和 headers
        val requestBody = original.body
        val headers = original.headers
        LoggerUtils.d("Request Headers size: ${headers.size}")
        LoggerUtils.d("Request Headers: ${newRequest.headers}")
        LoggerUtils.d("Request Body: ${requestBody?.let { JsonUtils.toJson(it) } ?: "No Body"}")

        chain.proceed(newRequest)
    }

    // 解密拦截器，用于对响应体进行 AES 解密
    private val decryptionInterceptor = Interceptor { chain ->
        val response = chain.proceed(chain.request())
        val responseBody = response.body

        // 解密响应体
        val decryptedBody = responseBody?.let { body ->
            val bodyString = body.string()

            // AES 解密响应体
            val decryptedData = AESEncryptionUtil.decrypt(bodyString, AESEncryptionUtil.AESKEY)

            // 重新构建解密后的响应体
            decryptedData.toResponseBody("application/json; charset=utf-8".toMediaTypeOrNull())
        }

        // 返回解密后的响应体
        response.newBuilder()
            .body(decryptedBody ?: responseBody)
            .build()
    }

    // OkHttpClient 配置，添加 token 和解密拦截器
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor) // 日志拦截器
        .addInterceptor(tokenInterceptor) // Token 头部拼接拦截器
        .addInterceptor(decryptionInterceptor) // 响应解密拦截器
        .build()

    // Retrofit 实例配置
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // API Service
    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
