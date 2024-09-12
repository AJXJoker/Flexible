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