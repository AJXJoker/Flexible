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
    @POST("/ductility-biosonar-footfault/latish-dodgery")
    suspend fun login(@Body body: String): Response<ResponseBody>


    // 获取一个未完成表单
    @POST("/adducent-grouper/quasimodo-no")
    suspend fun quasimodoNo(@Body body: String): Response<ResponseBody>


    // 获取指定表单
    @POST("/unsurmountable-cowardly-chromiderosis/fos-antitoxic/tyrannical-revaluation")
    suspend fun revaluation(@Body body: String): Response<ResponseBody>

    // 提交表单
    @POST("/unknot-bedabble/carouser")
    suspend fun submitForm(@Body body: String): Response<ResponseBody>


    // 获取工作岗位信息
    @POST("/replamineform/fringy-differ-baalism")
    suspend fun getJoBPositions(@Body body: String): Response<ResponseBody>


    // 获取工作岗位信息
    @POST("/symbiosis/biochrome-catalonian/reproducible")
    suspend fun getJoBAddress(@Body body: String): Response<ResponseBody>


    // 获取订单列表
    @POST("/gunning-catchup/scarecrow-plagiocephaly")
    suspend fun getOrderList(@Body body: String): Response<ResponseBody>


    // 产品手续费试算
    @POST("/polysyllable-feminist-flite/halieutic-crossable/ignore")
    suspend fun ignore(@Body body: String): Response<ResponseBody>


    // 产品首页展示
    @POST("/pople-enwomb-courage/vorticose")
    suspend fun vorticose(@Body body: String): Response<ResponseBody>


    // 获取产品列表
    @POST("/experiment/monoacid/polyanthus-maximum")
    suspend fun polyanthusMaximum(@Body body: String): Response<ResponseBody>

}
