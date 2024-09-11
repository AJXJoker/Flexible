package com.flexible.credit.me.lib_base.http

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    // 发送验证码接口
    @POST("allometry/draghound")
    suspend fun sendCode(@Body params: Map<String, String>): ApiResponse<SendCodeResponse>

    // 用户登录接口
    @POST("ductility-biosonar-footfault/latish-dodgery")
    suspend fun login(@Body params: Map<String, String>): ApiResponse<LoginResponse>
}
