package com.flexible.credit.me.look.viewmodel.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.flexible.credit.me.lib_base.base.BaseViewModel
import com.flexible.credit.me.lib_base.http.ApiServiceProvider
import com.flexible.credit.me.lib_base.http.LoginResponse
import com.flexible.credit.me.lib_base.http.SendCodeResponse

/**
 * 注册登录
 */
class LoginViewModel : BaseViewModel() {


    private val apiService = ApiServiceProvider.apiService

    private val _sendCodeResponse = MutableLiveData<SendCodeResponse>()
    val sendCodeResponse: LiveData<SendCodeResponse> get() = _sendCodeResponse

    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> get() = _loginResponse

    fun sendCode(params: Map<String, String>) {
        apiCall(
            call = { apiService.sendCode(params) },
            onSuccess = { response ->
                _sendCodeResponse.value = response
            }
        )
    }

    fun login(params: Map<String, String>) {
        apiCall(
            call = { apiService.login(params) },
            onSuccess = { response ->
                _loginResponse.value = response
            }
        )
    }

}