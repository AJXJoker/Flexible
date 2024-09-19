package com.flexible.credit.me.lib_base.utils

import android.content.Context
import android.provider.Settings
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeviceIdUtil {

    // 获取 Android ID
    fun getAndroidId(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    // 异步获取 Google 广告 ID (GAID)
    suspend fun getGoogleAdId(context: Context): String? {
        return withContext(Dispatchers.IO) {
            try {
                // 使用 Google 的 AdvertisingIdClient 获取 GAID
                val adInfo = AdvertisingIdClient.getAdvertisingIdInfo(context)
                if (adInfo != null && !adInfo.isLimitAdTrackingEnabled) {
                    adInfo.id // 返回 GAID
                } else {
                    null // 如果无法获取 GAID 或者用户限制广告追踪
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    // 组合方法，返回 Android ID 和 Google 广告 ID 的结果
    suspend fun getDeviceIds(context: Context): DeviceIds {
        val androidId = getAndroidId(context)
        val googleAdId = getGoogleAdId(context)
        return DeviceIds(androidId, googleAdId)
    }

    // 数据类，存储 Android ID 和 Google 广告 ID
    data class DeviceIds(
        val androidId: String,
        val googleAdId: String?
    )
}