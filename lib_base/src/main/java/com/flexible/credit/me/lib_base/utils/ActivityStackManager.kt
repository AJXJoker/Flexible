package com.flexible.credit.me.lib_base.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import java.lang.ref.WeakReference
import java.util.Stack
import kotlin.system.exitProcess

/**
 * 管理Activity堆栈
 */
class ActivityStackManager private constructor() {

    private val activityStack: Stack<WeakReference<Activity>> = Stack()

    companion object {
        val instance: ActivityStackManager by lazy { ActivityStackManager() }
    }

    /**
     * 添加Activity到堆栈。
     */
    fun addActivity(activity: Activity) {
        activityStack.add(WeakReference(activity))
    }

    /**
     * 从堆栈中移除指定的Activity。
     */
    fun removeActivity(activity: Activity) {
        activityStack.removeAll { it.get() == activity || it.get() == null }
    }

    /**
     * 获取当前栈顶的Activity。
     */
    fun currentActivity(): Activity? {
        return activityStack.lastOrNull()?.get()
    }

    /**
     * 结束指定的Activity并从堆栈中移除。
     */
    fun finishActivity(activity: Activity) {
        activity.finish()
        removeActivity(activity)
    }

    /**
     * 结束所有的Activity。
     */
    fun finishAllActivities() {
        while (activityStack.isNotEmpty()) {
            activityStack.pop().get()?.finish()
        }
    }

    /**
     * 返回首页，结束其他Activity。
     */
    fun finishToHome() {
        while (activityStack.size > 1) {
            activityStack.pop().get()?.finish()
        }
    }

    /**
     * 返回指定的Activity，结束其他Activity。
     */
    fun <T : Activity> finishToActivity(activityClass: Class<T>) {
        while (activityStack.size > 1) {
            val activity = activityStack.pop().get()
            if (activity != null && activity.javaClass == activityClass) {
                activityStack.push(WeakReference(activity))
                break
            }
            activity?.finish()
        }
    }

    /**
     * 退出应用程序。
     */
    fun appExit(context: Context) {
        try {
            finishAllActivities()
            val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as android.app.ActivityManager
            activityManager.restartPackage(context.packageName)  // 注意：restartPackage 已过时
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            LoggerUtils.e("终极退出程序")
            exitProcess(0)
        }
    }

    /**
     * 关闭当前页面。
     */
    fun closeCurrentActivity() {
        currentActivity()?.let { finishActivity(it) }
    }

    /**
     * 判断当前展示的Activity是否是指定的Activity。
     */
    fun isCurrentActivity(activityClass: Class<out Activity>): Boolean {
        return currentActivity()?.javaClass == activityClass
    }
}