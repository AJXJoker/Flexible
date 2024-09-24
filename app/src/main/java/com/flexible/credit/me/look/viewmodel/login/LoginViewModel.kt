package com.flexible.credit.me.look.viewmodel.login

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.flexible.credit.me.lib_base.base.BaseViewModel
import com.flexible.credit.me.lib_base.base.Const
import com.flexible.credit.me.lib_base.http.LoginResponse
import com.flexible.credit.me.lib_base.http.LoginSMSResponse
import com.flexible.credit.me.lib_base.http.ModelContentLoginSMS
import com.flexible.credit.me.lib_base.http.NetworkResponseHandler
import com.flexible.credit.me.lib_base.http.RequestModelLogin
import com.flexible.credit.me.lib_base.http.RequestModelLoginSMS
import com.flexible.credit.me.lib_base.http.RetrofitClient
import com.flexible.credit.me.lib_base.utils.AESEncryptionUtil
import com.flexible.credit.me.lib_base.utils.GsonUtil
import com.flexible.credit.me.lib_base.utils.JsonUtils
import com.flexible.credit.me.lib_base.utils.LoggerUtils
import com.flexible.credit.me.lib_base.utils.SharedPreferencesUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * 注册登录
 */
class LoginViewModel : BaseViewModel() {

    private val _responseLiveData = MutableLiveData<String>()

    var sendCode: MutableLiveData<Int> = MutableLiveData()
    var isLoginStatus: MutableLiveData<Boolean> = MutableLiveData()


    fun sendCode(mobile: String, phoneCode: String) {
        launchUI {
            try {
                // 发起网络请求
                val formattedJson = buildLoginSMSRequestBody(mobile, phoneCode)
                val reqBody = AESEncryptionUtil.encrypt(formattedJson, AESEncryptionUtil.AESKEY)
                val response = RetrofitClient.apiService.draghound(reqBody)
                NetworkResponseHandler.handleResponse(
                    response,
                    onSuccess = { encryptedText ->
                        // 成功时处理加密文本
                        val loginResponse: LoginSMSResponse? =
                            GsonUtil.fromJson(encryptedText, LoginSMSResponse::class.java)
                        if (loginResponse != null) {
                            sendCode.value = loginResponse.statusHbpsDDn
                        } else {
                            sendCode.value = -1
                        }
                        LoggerUtils.d("sms get Response: $encryptedText")
                        // 继续处理其他逻辑
                    },
                    onFailure = { code, message ->
                        // 失败时处理错误信息
                        sendCode.value = -1
                        LoggerUtils.d("sms get  Failed with code: $code, message: $message")
                    }
                )
            } catch (e: Exception) {
                // 捕获网络异常并更新 UI
                withContext(Dispatchers.Main) {
                    _responseLiveData.value = "请求失败: ${e.message}"
                }
            }
        }
    }


    fun startLogin(mobile: String, phoneCode: String, verificationCode: String) {
        launchUI {
            try {
                // 发起网络请求
                val formattedJson = buildLoginRequestBody(mobile, phoneCode, verificationCode)
                val reqBody = AESEncryptionUtil.encrypt(formattedJson, AESEncryptionUtil.AESKEY)
                val response = RetrofitClient.apiService.login(reqBody)
                NetworkResponseHandler.handleResponse(
                    response,
                    onSuccess = { encryptedText ->
                        // 成功时处理加密文本
                        val loginResponse: LoginResponse? =
                            GsonUtil.fromJson(encryptedText, LoginResponse::class.java)

                        if (loginResponse != null) {
                            if (loginResponse.statusHbpsDDn == 0) {
                                saveLoginResponseToLocal(loginResponse)
                                isLoginStatus.value = true
                                LoggerUtils.e("登录成功")
                            } else {
                                isLoginStatus.value = false
                                LoggerUtils.d("${loginResponse.statusHbpsDDn}:登录失败:${loginResponse.messageqxtEU52}")
                            }
                        }


                        LoggerUtils.d("Decrypted Response: $encryptedText")
                        // 继续处理其他逻辑
                    },
                    onFailure = { code, message ->
                        // 失败时处理错误信息
                        sendCode.value = -1
                        LoggerUtils.d("Failed with code: $code, message: $message")
                    }
                )
            } catch (e: Exception) {
                // 捕获网络异常并更新 UI
                withContext(Dispatchers.Main) {
                    _responseLiveData.value = "请求失败: ${e.message}"
                }
            }
        }
    }

    private fun saveLoginResponseToLocal(loginResponse: LoginResponse) {
        // 保存 LoginResponse 对象
        SharedPreferencesUtil.saveObject("login_response", loginResponse)
    }

    fun getLoginResponseFromLocal(): LoginResponse? {
        // 从 SharedPreferences 获取 LoginResponse 对象
        return SharedPreferencesUtil.getObject("login_response", LoginResponse::class.java)
    }


    /**
     * 构建请求体
     */
    fun buildLoginSMSRequestBody(mobile: String, phoneCode: String): String {
        val requestBody = RequestModelLoginSMS(
            modelzzBvcDJ = ModelContentLoginSMS(
                mobileBQQORrp = mobile,
                phoneCodeUZfqOdW = phoneCode
            )
        )
        return JsonUtils.removeWhitespaceFromJson(requestBody)
    }


    /**
     * 构建登录请求体
     */
    private fun buildLoginRequestBody(
        mobile: String,
        phoneCode: String,
        verificationCode: String
    ): String {
        // 创建登录请求体模型
        val requestBody = RequestModelLogin(
            mobileBQQORrp = mobile,            // 手机号
            phoneCodeUZfqOdW = phoneCode,      // 国家编码
            codeSoYhzML = verificationCode     // 验证码
        )
        // 返回去除多余空格的 JSON 字符串
        return JsonUtils.removeWhitespaceFromJson(requestBody)
    }

}