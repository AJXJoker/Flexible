package com.flexible.credit.me.lib_base.utils

import android.util.Log
import com.flexible.credit.me.lib_base.base.Const

object LoggerUtils {


    private var logTag: String = "LoggerUtils"

    private const val MAX_LOG_LENGTH = 4000

    fun d(message: String) {
        if (Const.isLogEnabled) {
            Log.e(logTag,message)
           // logLongMessage(Log.DEBUG, logTag, message)
        }
    }

    fun d(tag: String, message: String) {
        if (Const.isLogEnabled) {
            Log.e(tag,message)
            //logLongMessage(Log.DEBUG, tag, message)
        }
    }

    fun e(message: String) {
        if (Const.isLogEnabled) {
            Log.e(logTag,message)
          //  logLongMessage(Log.ERROR, logTag, message)
        }
    }

    fun e(tag: String, message: String) {
        if (Const.isLogEnabled) {
            Log.e(tag,message)
          //  logLongMessage(Log.ERROR, tag, message)
        }
    }

    fun enableLog(enabled: Boolean) {
        Const.isLogEnabled = enabled
    }

    private fun logLongMessage(priority: Int, tag: String, message: String) {
        var i = 0
        val length = message.length
        while (i < length) {
            val end = Math.min(length, i + MAX_LOG_LENGTH)
            Log.println(priority, tag, message.substring(i, end))
            i += MAX_LOG_LENGTH
        }
    }
}
