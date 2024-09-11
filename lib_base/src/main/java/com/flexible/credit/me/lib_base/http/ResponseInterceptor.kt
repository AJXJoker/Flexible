package com.flexible.credit.me.lib_base.http

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException

class ResponseInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val body = response.body?.string() ?: ""

        val apiResponse: BaseResponse = try {
            Gson().fromJson(body, BaseResponse::class.java)
        } catch (e: JsonSyntaxException) {
            // 如果 JSON 解析出错，返回一个默认的 BaseResponse 对象
            BaseResponse(statusHbpsDDn = -1, messageqxtEU52 = "解析错误")
        }

        // 重新构建响应体
        val newBody = ResponseBody.create(response.body?.contentType(), Gson().toJson(apiResponse))
        return response.newBuilder().body(newBody).build()
    }
}

class ApiException(message: String) : Exception(message)
