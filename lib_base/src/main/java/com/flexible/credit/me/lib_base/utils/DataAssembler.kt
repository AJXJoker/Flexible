package com.flexible.credit.me.lib_base.utils

import android.content.Context
import com.flexible.credit.me.lib_base.base.Const
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject


object DataAssembler {

    /**
     * 构造包含版本信息和设备信息的 JSON 数据结构
     */
    fun assembleData(context: Context): JSONObject {
        // 获取应用版本信息
        val appInfo = AppInfoUtil.getAppInfo(context)

        // 组装 JSON 结构
        val jsonData = JSONObject().apply {
            put("VERSIONJhgter6", appInfo.versionName) // App 版本
            put("RVp6KPpWv", "1.0.0") // 内部定义的版本
            put("AIDSUyRgrb", Const.deviceIds.androidId ?: "0000000000000000") // Android ID
            put(
                "gaidLEUOEAg",
                Const.deviceIds.googleAdId ?: "00000000-0000-0000-0000-000000000000"
            ) // Google 广告 ID (GAID)
        }

        if (Const.cachedLoginResponse != null) {
            val token = Const.cachedLoginResponse!!.usert4WYzkt.tokenymkFo2c
            LoggerUtils.d("获取到Token:${token}")
            if (token.isNotEmpty())
                jsonData.put("Auth", token)
        }

        return jsonData
    }

}