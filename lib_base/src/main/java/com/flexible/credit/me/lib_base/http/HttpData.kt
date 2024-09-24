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

//----App表单流程---------------
//获取一个未完成表单
// 请求数据模型
data class IncompleteFormRequest(
    val modelzzBvcDJ: IncompleteFormRequestData
)

data class IncompleteFormRequestData(
    val nodeTypeM0nNC9q: String // 节点类型，默认为：NODE1
)

// 响应数据模型
data class IncompleteFormResponse(
    val modelzzBvcDJ: FormDataResponse,
    val status: Int, // 状态码，0-成功，其他为失败，1012为需要重新登录
    val message: String, // 返回消息
    val extra: Map<String, Any>?, // 额外数据
    val time: Long, // 时间戳
    val maxAge: Long // 最大有效时间
) : BaseResponse(
    messageqxtEU52 = "",  // 这里可以默认值或从外部传入
    timekFFnWwl = 0L,
    maxAgeAhPzCnz = 0L,
    statusHbpsDDn = 0
)

data class FormDataResponse(
    val productId: String, // 产品ID
    val formsP334IKR: List<FormInfo> // 表单列表
)

data class FormInfo(
    val formIdgsMz3K4: String, // 表单ID
    val formTypemtfufUo: FormType, // 表单类型
    val formNametrPuPpn: String, // 表单名称
    val formDescfevuaOo: String, // 表单描述
    val sort: String, //
    val columnField: String, //
    val content: List<Map<String, Any>>, // 表单字段及其限制条件等
    val contentcQBAh1g: List<FormContentQBAh1g>
)

enum class FormType {
    BASIC, // 基础信息
    OCR, // 证件识别
    ALIVE, // 活体检测
    ALIVE_H5 // 活体检测H5
}


data class FormContentQBAh1g(
    val typeKAK6F9C: ContentType, // 字段类型
    val nameverpNre: String, // 显示名称
    val idFIfKrAE: String, // 标识符
    val required: Boolean, // 是否必填
    val secondConfirm: Boolean, // 是否需要二次确认
    val props: Map<String, Any>, // 额外属性
    val options: List<Option>? = null // 选项列表，对于 select 类型字段
)

enum class ContentType {
    SELECT,
    TEXT,
    ADDRESS_TYPE,
    // 其他可能的内容类型
}

data class Option(
    val color: String?, // 颜色
    val values: OptionValues, // 多语言值
    val countryIds: List<String>, // 适用的国家ID列表
    val sort: Int, // 排序
    val modified: String,
    val nameverpNre: String, // 显示名称
    val idFIfKrAE: String, // 标识符
    val value: String, // 值
    val categoryId: String? = null // 分类ID
)

data class OptionValues(
    val _t: String?, // 当前语言
    val values: Map<String, String>,
)

//-------------end


//---------构建指定表单 start--------
// 请求数据模型
data class RequestModelGetSpecificForm(
    val modelzzBvcDJ: SpecificFormRequestData
)

data class SpecificFormRequestData(
    val formIdgsMz3K4: String, // 表单ID
    val nodeTypeM0nNC9q: String = "NODE1" // 节点类型，默认为：NODE1
)


// 返回数据模型
data class SpecificFormResponse(
    val modelzzBvcDJ: SpecificFormResponseData
) : BaseResponse(
    messageqxtEU52 = "",  // 这里可以默认值或从外部传入
    timekFFnWwl = 0L,
    maxAgeAhPzCnz = 0L,
    statusHbpsDDn = 0
)

data class SpecificFormResponseData(
    val formsP334IKR: List<FormInfo>, // 表单列表
    var mobileBQQORrp: String,//手机号"
    val baseInfopCySQPy: BaseInfopCySQPyData // 基础信息
)

// 基础信息数据
data class BaseInfopCySQPyData(
    val birthdaysvXVkJI: String, // 出生日期
    val sexqMEMZU4: String, // 性别
    val maritalohPONU0: String, // 婚姻状态
    val emailnJqLXeM: String, // 邮箱
    val userTypeumWuEjI: String, // 用户类型
    val professionInfou03MKI7: ProfessionInfoData, // 职业信息
    val companyNameu3VGRGm: String, // 公司名称
    val companyPhoneg5tUvy0: String, // 公司电话
    val companyPinCoderTqXVik: String, // 公司营业执行编码
    val companyAddressDefoNDc: AddressData, // 公司地址
    val postalCodeZGXuNce: String, // 邮编
    val residentCommunityltnTvtA: String, // 居住社区
    val educationyG36FwB: String, // 教育程度
    val monthIncomeRct5W2z: Double, // 月收入
    val religionO5I2WSe: String, // 宗教信仰
    val addressokOo0L0: AddressData, // 居住地址
    val emergencyContactJP8TIKY: List<EmergencyContactData>, // 紧急联系人
    val userBankikh0AWr: UserBankData, // 银行信息
    val childrenCountvnKYOsG: Int, // 孩子数量
    val workingMonthu85UIIV: Int, // 工作时长
    val incomeSourceZtKfP9q: String, // 收入来源
    val payDatedFVZbKT: PayDateData, // 发薪日期信息
    val payDayIeSqxYM: Int, // 发薪日期
    val fatherLastNameIjVEMoc: String, // 父系姓氏
    val motherLastNameI6ynAN3: String, // 母系姓氏
    val taxNoezIpjCi: String, // 税号
    val residencev3Biuqj: String, // 住宅类型
    val socialAccountsgn8xtaO: SocialAccountsData, // 社交账号
    val uploadFilesNmoKEn2: List<UploadFileData>, // 上传文件
    val uploadVideoocCFpvd: String // 上传视频
)


// 职业信息数据
data class ProfessionInfoData(
    val workTypeDZzWdLU: String, // 工作类型
    val professionffRUE9l: String // 职业
)

// 地址数据
data class AddressData(
    val bigAddressTrJGb4j: BigAddressData, // 大地址
    val detailAddressBVKP8jM: String, // 详细地址
    val postalCodeZGXuNce: String // 邮编
)

// 大地址数据
data class BigAddressData(
    val statecgPKZIc: String, // 省/州
    val citybqr99jo: String, // 市
    val districtfKomy49: String, // 区
    val townumKEutx: String // 城镇
)

// 紧急联系人数据
data class EmergencyContactData(
    val nameverpNre: String, // 姓名
    val mobileBQQORrp: String, // 电话
    val relationyPxbCTg: String // 关系
)

// 银行信息数据
data class UserBankData(
    val bankIdbPN3dor: String, // 收款银行编号
    val bankCardnHha2lH: String, // 收款银行卡号
    val cardholderNamedgWfLUx: String, // 收款人姓名
    val ifscCodei7s400E: String, // 银行编码
    val accountTypeR7XCrRs: String // 账号类型
)

// 发薪日期数据
data class PayDateData(
    val typeKAK6F9C: String, // 发薪类型
    val weekDaypAVRHfJ: Int, // 每周几号发
    val monthDayW6ZwgYZ: Int, // 每月几号发
    val secondMonthDayVOJqi4d: Int // 下个月几号发
)

// 社交账号数据
data class SocialAccountsData(
    val facebookzliVhPN: String, // Facebook账号
    val zaloNmQ033O: String, // Zalo账号
    val lineASNYHey: String, // Line账号
    val whatsappYRo1Wuj: String // WhatsApp账号
)

// 上传文件数据
data class UploadFileData(
    val idFIfKrAE: String, // 文件编号
    val nameverpNre: String // 文件名称
)


// 个人信息类
data class PersonalInfo(
    val educationyG36FwB: String,
    val sexqMEMZU4: String,
    val maritalohPONU0: String,
    val childrenCountvnKYOsG: String,
    val postalCodeZGXuNce: String,
    val residencev3Biuqj: String,
    val emailnJqLXeM: String
)

// 地址信息类
data class AddressInfo(
    val statecgPKZIc: String,
    val citybqr99jo: String,
    val detailAddressBVKP8jM: String
)


//工作地址
data class RequestJobAddressGetForm(
    val modelzzBvcDJ: String
)

data class JobAddressFormResponse(
    val modelzzBvcDJ: List<AddressResponseData>
) : BaseResponse(
    messageqxtEU52 = "",  // 这里可以默认值或从外部传入
    timekFFnWwl = 0L,
    maxAgeAhPzCnz = 0L,
    statusHbpsDDn = 0
)

data class AddressResponseData(
    val typeKAK6F9C: String, //
    val created: String, //
    val root: Boolean, //
    val haveChildIfQqq4o: Boolean, //
    val modified: String, //
    val nameverpNre: String, //
    val idFIfKrAE: String, //
    val countryId: String, //
)


//---------指定表单  end--------


//---------岗位信息  end--------
// 返回数据模型
data class JobResponse(
    val modelzzBvcDJ: List<JobList>
) : BaseResponse(
    messageqxtEU52 = "",  // 这里可以默认值或从外部传入
    timekFFnWwl = 0L,
    maxAgeAhPzCnz = 0L,
    statusHbpsDDn = 0
)

data class JobList(
    var idFIfKrAE: String,
    val nameverpNre: String,
    val childrenr3xoueW: List<Job>
)

data class Job(
    val nameverpNre: String,
    val idFIfKrAE: String
)

//---------指定表单  end--------

//----个人信息提交-----
// 顶级数据类，包含 modelzzBvcDJ 字段
data class RequestUserInfoGetForm(
    val modelzzBvcDJ: ModelData
)

// modelzzBvcDJ 数据类，包含表单 ID 和提交数据
data class ModelData(
    val formIdgsMz3K4: String,
    val submitDatae6oQYty: SubmitData
)

data class RequestSubmitDataGetForm(
    val modelzzBvcDJ: ModelData
)

// 提交数据类，包含个人信息和地址
data class SubmitData(
    val educationyG36FwB: String, // 教育信息
    val sexqMEMZU4: String, // 性别
    val maritalohPONU0: String, // 婚姻状态
    val childrenCountvnKYOsG: String, // 孩子数量
    val postalCodeZGXuNce: String, // 邮政编码
    val residencev3Biuqj: String, // 居住情况
    val addressokOo0L0: Address, // 地址信息
    val emailnJqLXeM: String // 邮箱
)

// 地址数据类，包含大地址和详细地址
data class Address(
    val bigAddressTrJGb4j: BigAddress, // 大地址
    val detailAddressBVKP8jM: String // 详细地址
)

// 大地址数据类，包含州和城市信息
data class BigAddress(
    val statecgPKZIc: String, // 州
    val citybqr99jo: String // 城市
)


//---------订单列表 start--------

data class OrderListRequest(
    val queryCoJgzSW: OrderListRequestData
)

data class OrderListRequestData(
    // val statusHbpsDDn: String, // enum 订单状态，返回所有订单为null或者不传，支持的状态值：PASS-审批通过、DENY-审批拒绝、LOAN_SUCCESS-放款成功、LOAN_FAIL-放款失败、DUNNING-逾期、FINISH-结清"
    var pageNoXCuUUqT: Int,////页码，默认为1
    var pageSizea23cerF: Int,//int //每页记录数，默认为10
    //  var statusListYztS2i7: String,//"Array //订单状态 enum"
    //var ddCreatedGOwIpiV:String,
)

data class OrderResponse(
    val pageL9DQhgx: OrderResponseData
) : BaseResponse(
    messageqxtEU52 = "",  // 这里可以默认值或从外部传入
    timekFFnWwl = 0L,
    maxAgeAhPzCnz = 0L,
    statusHbpsDDn = 0
)

data class OrderResponseData(
    val numberOmCXxit: Int, // 当前页码
    val last: Boolean,
    val pageable: String,
    val hasNext: Boolean,
    val first: Boolean,
    val numberOfElementsHnIlTvA: Int, // 每页记录数
    val totalElementsLUNX0Sq: Int, // 总记录数
    val totalPagespzjUt22: Int, // 总页数
    val contentcQBAh1g: List<Order> // 订单内容列表
)

data class Order(
    /* val idFIfKrAE: String, // 订单编号
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
     val identityFlagrx4gw17: Boolean? = null // 是否有身份信息*/
    val idFIfKrAE: String, // 订单编号
    val amountAs1NQoA: Double, // 订单金额
    val statusHbpsDDn: String, // 订单状态
    val statusNotey6OivtC: String, // 状态描述
    val createdxxPxuKQ: String, // 申请时间
    val termW1yCzAm: Int, // 借款周期
    val termUnitEBqOUgA: String, // 周期单位：DAY、MONTH
    val delayAmountJ4tGjnt: Double? = null, // 展期金额（可选）
    val productgtUIkIB: Product // 产品信息
)

data class Product(
    val nameverpNre: String, // 产品名称
    val iconjmFxqYT: String, // 产品图标
    val termW1yCzAm: String, // 产品周期
    val amountAs1NQoA: String // 产品金额
)


