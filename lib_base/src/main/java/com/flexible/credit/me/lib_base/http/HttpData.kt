package com.flexible.credit.me.lib_base.http

data class SendCodeResponse(
    override val statusHbpsDDn: Int = 0,
    override val messageqxtEU52: String = "",
    override val modelzzBvcDJ: VerificationCode? = null, // 登录验证码
    val timekFFnWwl: Long = 0,
    val maxAgeAhPzCnz: Long = 0
) : BaseResponse(statusHbpsDDn, messageqxtEU52, modelzzBvcDJ)

data class VerificationCode(
    val code: String? = null // 验证码
)

data class LoginResponse(
    override val statusHbpsDDn: Int = 0,
    override val messageqxtEU52: String = "",
    override val modelzzBvcDJ: User? = null, // 用户信息
    val googleTestVRiKiXz: Boolean = false, // 是否google测试账号
    val surfaceApijaJPI: Boolean = false, // 是否a面用户
    val extraove3bBx: Any? = null, // 额外信息
    val timekFFnWwl: Long = 0,
    val maxAgeAhPzCnz: Long = 0
) : BaseResponse(statusHbpsDDn, messageqxtEU52, modelzzBvcDJ)

data class User(
    val userIddiPK5Ot: String? = null, // 用户编号
    val userNameBvvOZ91: String? = null, // 用户姓名
    val tokenymkFo2c: String? = null // 授权token
)