package com.flexible.credit.me.lib_base.utils

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build

object AppInfoUtil {

    /**
     * 获取应用的版本号 (versionCode)
     */
    fun getVersionCode(context: Context): Long {
        return try {
            val packageInfo: PackageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageInfo.longVersionCode // Android 9（API 28）及以上使用 longVersionCode
            } else {
                @Suppress("DEPRECATION")
                packageInfo.versionCode.toLong() // 低版本使用 versionCode
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            -1L // 获取失败返回 -1
        }
    }

    /**
     * 获取应用的版本名称 (versionName)
     */
    fun getVersionName(context: Context): String {
        return try {
            val packageInfo: PackageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.versionName ?: "Unknown"
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            "Unknown" // 获取失败返回 "Unknown"
        }
    }

    /**
     * 获取应用的包名
     */
    fun getPackageName(context: Context): String {
        return context.packageName
    }

    /**
     * 获取应用的名称
     */
    fun getAppName(context: Context): String {
        return try {
            val appInfo = context.packageManager.getApplicationInfo(context.packageName, 0)
            context.packageManager.getApplicationLabel(appInfo).toString()
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            "Unknown" // 获取失败返回 "Unknown"
        }
    }

    /**
     * 获取应用的所有信息
     */
    fun getAppInfo(context: Context): AppInfo {
        return AppInfo(
            appName = getAppName(context),
            packageName = getPackageName(context),
            versionName = getVersionName(context),
            versionCode = getVersionCode(context)
        )
    }

    /**
     * 数据类，存储应用信息
     */
    data class AppInfo(
        val appName: String,
        val packageName: String,
        val versionName: String,
        val versionCode: Long
    )
}
