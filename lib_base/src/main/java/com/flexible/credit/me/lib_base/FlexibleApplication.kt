package com.flexible.credit.me.lib_base

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.flexible.credit.me.lib_base.utils.ActivityStackManager
import com.flexible.credit.me.lib_base.utils.LanguageUtil
import com.flexible.credit.me.lib_base.utils.LoggerUtils
import com.flexible.credit.me.lib_base.utils.route.RouteTable
import com.flexible.credit.me.lib_base.utils.route.Router

class FlexibleApplication : MultiDexApplication() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LanguageUtil.applySavedLanguage(base))
        // 打印当前语言
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        // 全局初始化
        LoggerUtils.e("APPLICATION 初始化...")
        initGlobalSettings()
        initThirdPartyServices()

        // 注册 Activity 生命周期监听
        registerActivityLifecycleCallbacks(activityLifecycleCallbacks)
    }

    // 拆分的全局设置初始化方法
    private fun initGlobalSettings() {
        initLogging()
        initNetwork()
        initDatabase()
    }

    // 拆分的第三方服务初始化方法
    private fun initThirdPartyServices() {
        initLibraries()
        initAuthentication()
    }

    private fun initLibraries() {
        // 第三方库初始化
        registerActivitiesByReflection()
    }

    private fun initLogging() {
        // 日志系统初始化
    }

    private fun initNetwork() {
        // 网络配置初始化
    }

    private fun initDatabase() {
        // 数据库初始化
    }

    private fun initAuthentication() {
        // 用户认证系统初始化
    }

    private fun registerActivitiesByReflection() {
        try {
            // 遍历 RouteTable 中的路径与类名的映射关系
            RouteTable.routeMap.forEach { (path, className) ->
                try {
                    // 通过反射加载类
                    val activityClass = Class.forName(className)
                    // 注册 Activity
                    Router.registerActivity(path, activityClass as Class<out Activity>)
                } catch (e: ClassNotFoundException) {
                    e.printStackTrace()
                    LoggerUtils.d("Class not found for path: $path, className: $className")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // 定义 ActivityLifecycleCallbacks
    private val activityLifecycleCallbacks = object : Application.ActivityLifecycleCallbacks {

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
           // LoggerUtils.e("Activity Created: ${activity.localClassName}")
            // 添加到堆栈
            ActivityStackManager.instance.addActivity(activity)
        }

        override fun onActivityStarted(activity: Activity) {
            // LoggerUtils.e("Activity Started: ${activity.localClassName}")
        }

        override fun onActivityResumed(activity: Activity) {
            //  LoggerUtils.e("Activity Resumed: ${activity.localClassName}")
        }

        override fun onActivityPaused(activity: Activity) {
            // LoggerUtils.e("Activity Paused: ${activity.localClassName}")
        }

        override fun onActivityStopped(activity: Activity) {
            // LoggerUtils.e("Activity Stopped: ${activity.localClassName}")
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            //LoggerUtils.e("Activity SaveInstanceState: ${activity.localClassName}")
        }

        override fun onActivityDestroyed(activity: Activity) {
            // LoggerUtils.e("Activity Destroyed: ${activity.localClassName}")
            // 从堆栈中移除
            ActivityStackManager.instance.removeActivity(activity)
        }
    }

}