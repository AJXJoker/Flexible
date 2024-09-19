package com.flexible.credit.me.lib_base.http

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {


    // 发送验证码接口
    @POST("/allometry/draghound")
    suspend fun draghound(@Body body: String): Response<ResponseBody> // 返回加密后的字符串

    // 用户登录接口
    @POST("ductility-biosonar-footfault/latish-dodgery")
    suspend fun login(@Body body: String): Response<ResponseBody>
}
