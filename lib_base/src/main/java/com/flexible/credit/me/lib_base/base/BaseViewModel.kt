package com.flexible.credit.me.lib_base.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flexible.credit.me.lib_base.utils.PassRoomException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

open class BaseViewModel : ViewModel(), LifecycleObserver {


    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val passRoom by lazy { MutableLiveData<Exception>() }

    private val error by lazy { MutableLiveData<Exception>() }
    private val finally by lazy { MutableLiveData<Int>() }

    //运行在UI线程的协程
    fun launchUI(block: suspend CoroutineScope.() -> Unit) = viewModelScope.launch {
        try {
            block()
        } catch (e: Exception) {
            if (e is PassRoomException) {
                passRoom.value = e
            } else {
                error.value = e
//                throw e
            }

        } finally {
            finally.value = 200

        }
    }
}