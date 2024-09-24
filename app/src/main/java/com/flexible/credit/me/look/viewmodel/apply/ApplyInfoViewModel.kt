package com.flexible.credit.me.look.viewmodel.apply

import androidx.lifecycle.MutableLiveData
import com.flexible.credit.me.lib_base.base.BaseViewModel
import com.flexible.credit.me.lib_base.http.Address
import com.flexible.credit.me.lib_base.http.AddressInfo
import com.flexible.credit.me.lib_base.http.AddressResponseData
import com.flexible.credit.me.lib_base.http.BigAddress
import com.flexible.credit.me.lib_base.http.IncompleteFormRequest
import com.flexible.credit.me.lib_base.http.IncompleteFormRequestData
import com.flexible.credit.me.lib_base.http.IncompleteFormResponse
import com.flexible.credit.me.lib_base.http.Job
import com.flexible.credit.me.lib_base.http.JobAddressFormResponse
import com.flexible.credit.me.lib_base.http.JobList
import com.flexible.credit.me.lib_base.http.JobResponse
import com.flexible.credit.me.lib_base.http.LoginSMSResponse
import com.flexible.credit.me.lib_base.http.ModelContentLoginSMS
import com.flexible.credit.me.lib_base.http.ModelData
import com.flexible.credit.me.lib_base.http.NetworkResponseHandler
import com.flexible.credit.me.lib_base.http.Option
import com.flexible.credit.me.lib_base.http.PersonalInfo
import com.flexible.credit.me.lib_base.http.RequestJobAddressGetForm
import com.flexible.credit.me.lib_base.http.RequestModelGetSpecificForm
import com.flexible.credit.me.lib_base.http.RequestModelLoginSMS
import com.flexible.credit.me.lib_base.http.RequestSubmitDataGetForm
import com.flexible.credit.me.lib_base.http.RetrofitClient
import com.flexible.credit.me.lib_base.http.SpecificFormRequestData
import com.flexible.credit.me.lib_base.http.SpecificFormResponse
import com.flexible.credit.me.lib_base.http.SubmitData
import com.flexible.credit.me.lib_base.utils.AESEncryptionUtil
import com.flexible.credit.me.lib_base.utils.GsonUtil
import com.flexible.credit.me.lib_base.utils.JsonUtils
import com.flexible.credit.me.lib_base.utils.LoggerUtils
import com.google.gson.Gson

class ApplyInfoViewModel : BaseViewModel() {

    //未完成表单
    var incompleteFormData: MutableLiveData<IncompleteFormResponse?> = MutableLiveData()

    //指定表单
    var specificFormData: MutableLiveData<SpecificFormResponse?> = MutableLiveData()

    /**
     * 教育
     */
    var educationListLiveData: MutableLiveData<List<Option>?> = MutableLiveData()

    /**
     * 性别
     */
    var genderListLiveData: MutableLiveData<List<Option>?> = MutableLiveData()

    /**
     * 孩子数量
     */
    var childrenCountListLiveData: MutableLiveData<List<Option>?> = MutableLiveData()

    /**
     * 婚姻
     */
    var maritalListLiveData: MutableLiveData<List<Option>?> = MutableLiveData()

    /**
     * 工作岗位
     */
    var jobListLiveData: MutableLiveData<List<JobList>?> = MutableLiveData()


    /**
     * 省
     */
    var provinceListLiveData: MutableLiveData<List<AddressResponseData>> = MutableLiveData()

    /**
     * 市
     */
    var cityListLiveData: MutableLiveData<List<AddressResponseData>> = MutableLiveData()


    /**
     * 获取一个未完成表单
     */
    fun getIncompleteForm(node1: String) {
        launchUI {
            try {
                val formattedJson = buildIncompleteFormRequestBody(node1)
                val reqBody = AESEncryptionUtil.encrypt(formattedJson, AESEncryptionUtil.AESKEY)
                val response = RetrofitClient.apiService.quasimodoNo(reqBody)
                NetworkResponseHandler.handleResponse(
                    response,
                    onSuccess = { encryptedText ->
                        // 成功时处理加密文本
                        val incompleteFormResponse: IncompleteFormResponse? =
                            GsonUtil.fromJson(encryptedText, IncompleteFormResponse::class.java)
                        if (incompleteFormResponse != null) {
                            if (incompleteFormResponse.statusHbpsDDn == 0) {
                                incompleteFormData.value = incompleteFormResponse
                            } else {
                                incompleteFormData.value = null
                            }
                        } else {
                            incompleteFormData.value = null
                        }
                        LoggerUtils.d("获取一个未完成表单 Response: $encryptedText")
                        // 继续处理其他逻辑
                    },
                    onFailure = { code, message ->
                        // 失败时处理错误信息
                        incompleteFormData.value = null
                        LoggerUtils.d("获取一个未完成表单  Failed with code: $code, message: $message")
                    }
                )

            } catch (e: Exception) {
                incompleteFormData.value = null
            }
        }
    }


    /**
     * 获取一个指定表单
     */
    fun getSpecifyForm(formId: String, node1: String) {
        launchUI {
            try {
                val formattedJson = buildSpecificFormRequestBody(formId, node1)
                val reqBody = AESEncryptionUtil.encrypt(formattedJson, AESEncryptionUtil.AESKEY)
                LoggerUtils.d("请求内容:${formattedJson}")
                LoggerUtils.d("加密后的请求内容:${reqBody}")
                val response = RetrofitClient.apiService.revaluation(reqBody)
                NetworkResponseHandler.handleResponse(
                    response,
                    onSuccess = { encryptedText ->
                        // 成功时处理加密文本
                        val specificFormResponse: SpecificFormResponse? =
                            GsonUtil.fromJson(encryptedText, SpecificFormResponse::class.java)
                        if (specificFormResponse != null) {
                            if (specificFormResponse.statusHbpsDDn == 0) {
                                specificFormData.value = specificFormResponse
                                initList()
                            } else {
                                specificFormData.value = null
                            }
                        } else {
                            specificFormData.value = null
                        }
                        LoggerUtils.logLongMessage("获取一个指定表单 Response: $encryptedText")
                        // 继续处理其他逻辑
                    },
                    onFailure = { code, message ->
                        // 失败时处理错误信息
                        specificFormData.value = null
                        LoggerUtils.d("获取一个指定表单  Failed with code: $code, message: $message")
                    }
                )

            } catch (e: Exception) {
                specificFormData.value = null
            }
        }
    }

    /**
     * 获取工作岗位
     */
    fun getJoBPositions() {
        launchUI {
            try {
                /*val formattedJson = buildSpecificFormRequestBody(formId, node1)
                val reqBody = AESEncryptionUtil.encrypt(formattedJson, AESEncryptionUtil.AESKEY)*/
                val response = RetrofitClient.apiService.getJoBPositions("")
                NetworkResponseHandler.handleResponse(
                    response,
                    onSuccess = { encryptedText ->
                        // 成功时处理加密文本
                        val jobResponse: JobResponse? =
                            GsonUtil.fromJson(encryptedText, JobResponse::class.java)
                        if (jobResponse != null) {
                            if (jobResponse.statusHbpsDDn == 0) {
                                val jobList: List<JobList> =
                                    jobResponse.modelzzBvcDJ
                                jobListLiveData.value = jobList
                            } else {
                                jobListLiveData.value = null
                            }
                        } else {
                            jobListLiveData.value = null
                        }
                        LoggerUtils.logLongMessage("获取工作岗位 Response: $encryptedText")
                        // 继续处理其他逻辑
                    },
                    onFailure = { code, message ->
                        // 失败时处理错误信息
                        jobListLiveData.value = null
                        LoggerUtils.d("获取工作岗位  Failed with code: $code, message: $message")
                    }
                )

            } catch (e: Exception) {
                jobListLiveData.value = null
            }
        }
    }


    /**
     * 获取地址信息
     */
    fun getJobAddress(level: Int, modelzzBvcDJ: String) {
        launchUI {
            try {
                val formattedJson = buildJobAddressRequestBody(modelzzBvcDJ)
                val reqBody = AESEncryptionUtil.encrypt(formattedJson, AESEncryptionUtil.AESKEY)
                LoggerUtils.d("请求内容:${formattedJson}")
                val response = RetrofitClient.apiService.getJoBAddress(reqBody)
                NetworkResponseHandler.handleResponse(
                    response,
                    onSuccess = { encryptedText ->
                        val jobAddressFormResponse: JobAddressFormResponse? =
                            GsonUtil.fromJson(encryptedText, JobAddressFormResponse::class.java)
                        if (jobAddressFormResponse != null) {
                            if (jobAddressFormResponse.statusHbpsDDn == 0) {
                                if (level == 1) {
                                    provinceListLiveData.value = jobAddressFormResponse.modelzzBvcDJ
                                } else if (level == 2) {
                                    cityListLiveData.value = jobAddressFormResponse.modelzzBvcDJ
                                }
                            }
                        }
                        LoggerUtils.logLongMessage("获取一个地址 Response: $encryptedText")
                        // 继续处理其他逻辑
                    },
                    onFailure = { code, message ->
                        // 失败时处理错误信息
                        LoggerUtils.d("获取一个地址  Failed with code: $code, message: $message")
                    }
                )

            } catch (e: Exception) {
            }
        }
    }


    private fun initList() {
        val specificFormResponse = specificFormData.value
        val formList = specificFormResponse?.modelzzBvcDJ?.formsP334IKR?.get(0)
        if (formList != null) {
            val educationOptions: List<Option>? =
                formList.contentcQBAh1g.firstOrNull { it.idFIfKrAE == "education" }?.options
            educationListLiveData.value = educationOptions

            val genderOptions: List<Option>? =
                formList.contentcQBAh1g.firstOrNull { it.idFIfKrAE == "sex" }?.options
            genderListLiveData.value = genderOptions


            val childrenCountOptions: List<Option>? =
                formList.contentcQBAh1g.firstOrNull { it.idFIfKrAE == "childrenCount" }?.options
            childrenCountListLiveData.value = childrenCountOptions

            val maritalListOptions: List<Option>? =
                formList.contentcQBAh1g.firstOrNull { it.idFIfKrAE == "marital" }?.options
            maritalListLiveData.value = maritalListOptions

        }
    }


    /**
     * 提交表单
     */
    fun submitUserInfoForm(msg: String) {
        launchUI {
            try {
                val formattedJson = buildIncompleteFormRequestBody(msg)
                val reqBody = AESEncryptionUtil.encrypt(formattedJson, AESEncryptionUtil.AESKEY)

                LoggerUtils.d("提交表单请求内容:${formattedJson}")
                LoggerUtils.d("提交表单加密后的请求内容:${reqBody}")

                val response = RetrofitClient.apiService.submitForm(reqBody)
                NetworkResponseHandler.handleResponse(
                    response,
                    onSuccess = { encryptedText ->
                        // 成功时处理加密文本
                        /*val incompleteFormResponse: IncompleteFormResponse? =
                            GsonUtil.fromJson(encryptedText, IncompleteFormResponse::class.java)
                        if (incompleteFormResponse != null) {
                            if (incompleteFormResponse.statusHbpsDDn == 0) {
                                incompleteFormData.value = incompleteFormResponse
                            } else {
                                incompleteFormData.value = null
                            }
                        } else {
                            incompleteFormData.value = null
                        }*/
                        LoggerUtils.d("提交表单 Response: $encryptedText")
                        // 继续处理其他逻辑
                    },
                    onFailure = { code, message ->
                        // 失败时处理错误信息
                        incompleteFormData.value = null
                        LoggerUtils.d("提交表单  Failed with code: $code, message: $message")
                    }
                )

            } catch (e: Exception) {
                incompleteFormData.value = null
            }
        }
    }


    /**
     * 构建获取未完成表单的请求体
     */
    fun buildIncompleteFormRequestBody(nodeType: String = "NODE1"): String {
        val requestBody = IncompleteFormRequest(
            modelzzBvcDJ = IncompleteFormRequestData(
                nodeTypeM0nNC9q = nodeType
            )
        )
        return JsonUtils.removeWhitespaceFromJson(requestBody)
    }


    // 构建获取指定表单的请求体
    fun buildSpecificFormRequestBody(formId: String, nodeType: String = "NODE1"): String {
        val requestBody = RequestModelGetSpecificForm(
            modelzzBvcDJ = SpecificFormRequestData(
                formIdgsMz3K4 = formId,
                nodeTypeM0nNC9q = nodeType
            )
        )
        return JsonUtils.removeWhitespaceFromJson(requestBody)
    }


    // 构建获取指定表单的请求体
    fun buildJobAddressRequestBody(modelzzBvcDJ: String): String {
        val requestBody = RequestJobAddressGetForm(
            modelzzBvcDJ = modelzzBvcDJ
        )
        return JsonUtils.removeWhitespaceFromJson(requestBody)
    }


    fun buildJobAddressRequestBody(
        formIdgsMz3K4: String,
        personalInfo: PersonalInfo,
        addressInfo: AddressInfo
    ): String {

        val gson = Gson()

        // 构建地址信息
        val address = Address(
            bigAddressTrJGb4j = BigAddress(
                statecgPKZIc = addressInfo.statecgPKZIc,
                citybqr99jo = addressInfo.citybqr99jo
            ),
            detailAddressBVKP8jM = addressInfo.detailAddressBVKP8jM
        )

        // 构建提交数据
        val submitData = SubmitData(
            educationyG36FwB = personalInfo.educationyG36FwB,
            sexqMEMZU4 = personalInfo.sexqMEMZU4,
            maritalohPONU0 = personalInfo.maritalohPONU0,
            childrenCountvnKYOsG = personalInfo.childrenCountvnKYOsG,
            postalCodeZGXuNce = personalInfo.postalCodeZGXuNce,
            residencev3Biuqj = personalInfo.residencev3Biuqj,
            addressokOo0L0 = address,
            emailnJqLXeM = personalInfo.emailnJqLXeM
        )

        val modelData = ModelData(
            formIdgsMz3K4 = formIdgsMz3K4,
            submitDatae6oQYty = submitData
        )


        // 构建顶级对象
        val requestBody = RequestSubmitDataGetForm(
            modelzzBvcDJ = modelData
        )

        // 将对象转换为 JSON 字符串并去除空白字符
        return JsonUtils.removeWhitespaceFromJson(requestBody)
    }


}