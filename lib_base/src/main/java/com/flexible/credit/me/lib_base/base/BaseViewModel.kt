package com.flexible.credit.me.lib_base.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flexible.credit.me.lib_base.http.ApiResponse
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

open class BaseViewModel : ViewModel(), LifecycleObserver {


    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    protected fun <T> apiCall(
        call: suspend () -> ApiResponse<T>,
        onSuccess: (T) -> Unit
    ) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = call()
                if (response.success) {
                    response.data?.let { onSuccess(it) } ?: run {
                        _error.value = "Data is null"
                    }
                } else {
                    _error.value = response.errorMessage ?: "Unknown error"
                }
            } catch (e: HttpException) {
                _error.value = "HTTP error: ${e.message()}"
            } catch (e: IOException) {
                _error.value = "Network error: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

}