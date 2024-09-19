package com.flexible.credit.me.lib_base.http

import com.flexible.credit.me.lib_base.utils.LoggerUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.IOException

object NetworkResponseHandler {

    // 封装处理请求响应的函数
    suspend fun handleResponse(
        response: Response<ResponseBody>,
        onSuccess: (String) -> Unit,  // 成功时的回调，传入加密文本
        onFailure: (Int, String) -> Unit  // 失败时的回调，传入错误码和错误消息
    ) {
        withContext(Dispatchers.Main) {
            try {
                val responseBody = response.body()

                if (response.isSuccessful && responseBody != null) {
                    val encryptedText = responseBody.string()  // 获取响应体字符串
                   // LoggerUtils.d("Encrypted Response: $encryptedText")

                    // 调用成功时的回调，传入加密文本
                    onSuccess(encryptedText)
                } else {
                    val errorCode = response.code()
                    val errorMessage = response.message()

                  //  LoggerUtils.d("Error: $errorCode, Message: $errorMessage")

                    // 调用失败时的回调，传入错误码和错误消息
                    onFailure(errorCode, errorMessage)
                }
            } catch (e: IOException) {
                LoggerUtils.e("Exception occurred: ${e.message}")
                onFailure(-1, "Network Error")  // 网络或其他异常
            }
        }
    }
}
