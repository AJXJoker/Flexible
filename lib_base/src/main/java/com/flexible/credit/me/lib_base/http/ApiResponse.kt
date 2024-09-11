package com.flexible.credit.me.lib_base.http

// 通用数据类，用于封装返回结果和状态

// 公共返回字段基类
open class BaseResponse(
    open val statusHbpsDDn: Int = 0, // 0-成功，其他为失败
    open val messageqxtEU52: String = "", // 状态描述
    open val modelzzBvcDJ: Any? = null // 具体的数据结构
)

data class ApiResponse<T>(
    val success: Boolean,
    val data: T?,
    val errorMessage: String? = null
)

