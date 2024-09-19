package com.flexible.credit.me.lib_base.http

// 通用的响应数据模型，包含所有接口共有的字段
open class BaseResponse(
    val messageqxtEU52: String,  // 状态描述（例如：错误信息、成功信息等）
    val timekFFnWwl: Long,       // 时间戳
    val maxAgeAhPzCnz: Long,     // 最大存活时间（或数据缓存时间）
    val statusHbpsDDn: Int       // 请求状态码
)


// 数据模型
data class RequestModelLoginSMS(
    val modelzzBvcDJ: ModelContentLoginSMS
)

data class ModelContentLoginSMS(
    val mobileBQQORrp: String,
    val phoneCodeUZfqOdW: String
)


data class LoginSMSResponse(
    val modelzzBvcDJ: String? = null // 验证码数据，可能为空
) : BaseResponse(
    messageqxtEU52 = "",  // 这里可以默认值或从外部传入
    timekFFnWwl = 0L,
    maxAgeAhPzCnz = 0L,
    statusHbpsDDn = 0
)

data class RequestModelLogin(
    val mobileBQQORrp: String,  // 手机号
    val phoneCodeUZfqOdW: String,  // 国家编码
    val codeSoYhzML: String  // 验证码
)



// 登录结果数据模型
data class LoginResponse(
    val usert4WYzkt: UserInfo,        // 用户信息
    val googleTestVRiKiXz: Boolean,   // 是否 Google 测试账号
    val surfaceApijaJPI: Boolean,     // 是否 A 面用户，非金融面
    val extraove3bBx: Any? = null     // 额外数据，可以为任意对象类型
) : BaseResponse(
    messageqxtEU52 = "",  // 这里可以默认值或从外部传入
    timekFFnWwl = 0L,
    maxAgeAhPzCnz = 0L,
    statusHbpsDDn = 0
)


// 用户信息模型
data class UserInfo(
    val userIddiPK5Ot: String,  // 用户编号
    val userNameBvvOZ91: String,  // 用户姓名 (需要根据实际情况，当前数据结构中没有提供该字段)
    val tokenymkFo2c: String,  // 授权 token
    val auth2Verify: Boolean,  // 是否通过二次验证
    val phone: String,  // 用户手机号
    val root: Boolean,  // 是否为超级用户
    val createdTime: Long,  // 创建时间（时间戳）
    val phoneCode: String,  // 手机号区域码
    val googleTestVRiKiXz: String,  // Google 测试字段
    val enabled2Verify: Boolean  // 是否启用二次验证
)


data class VerificationCode(
    val code: String? = null // 验证码
)


data class User(
    val userIddiPK5Ot: String? = null, // 用户编号
    val userNameBvvOZ91: String? = null, // 用户姓名
    val tokenymkFo2c: String? = null // 授权token
)


//订单数据结构-------

data class OrderResponse(
    val numberOmCXxit: Int, // 当前页码
    val numberOfElementsHnIlTvA: Int, // 每页记录数
    val totalElementsLUNX0Sq: Int, // 总记录数
    val totalPagespzjUt22: Int, // 总页数
    val contentcQBAh1g: List<Order> // 订单内容列表
)

data class Order(
    val idFIfKrAE: String, // 订单编号
    val amountAs1NQoA: Double, // 订单金额
    val statusHbpsDDn: String, // 订单状态
    val statusNotey6OivtC: String, // 状态描述
    val createdxxPxuKQ: String, // 申请时间
    val termW1yCzAm: Int, // 借款周期
    val termUnitEBqOUgA: String, // 周期单位
    val delayAmountJ4tGjnt: Double? = null, // 展期金额
    val productgtUIkIB: Product, // 产品信息
    val nameverpNre: String? = null, // 姓名
    val genderWmoO5BV: String? = null, // 性别
    val workingConditionsPJwluac: String? = null, // 工作
    val basicLevelNbPk2nt: String? = null, // 基础信息评级
    val basicDescMOiUINn: String? = null, // 基础信息描述
    val basicFlagg54BdEh: Boolean? = null, // 是否有基础信息
    val contactFlaglZZ73OX: Boolean? = null, // 是否有联系人
    val contactLevelK4pXcNM: String? = null, // 联系人评级
    val contactDescLkY3gGY: String? = null, // 联系人描述
    val smsFlagUYJrHRW: Boolean? = null, // 是否有短信
    val smsLeveld43m7ya: String? = null, // 短信评级
    val smsDescLXyU2SM: String? = null, // 短信描述
    val bankFlagusTq7iz: Boolean? = null, // 是否有银行卡
    val bankLevelFiGGe9A: String? = null, // 银行卡评级
    val bankDescHevHig1: String? = null, // 银行卡描述
    val summaryScorecFQwNMB: Long? = null, // 总得分
    val summaryLevelMTyJSHz: String? = null, // 总评级
    val summaryDesclpMPiQQ: String? = null, // 总描述
    val identityFlagrx4gw17: Boolean? = null // 是否有身份信息
)

data class Product(
    val nameverpNre: String, // 产品名称
    val iconjmFxqYT: String, // 产品图标
    val termW1yCzAm: String, // 产品周期
    val amountAs1NQoA: String // 产品金额
)


