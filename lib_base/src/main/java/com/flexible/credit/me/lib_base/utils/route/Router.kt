package com.flexible.credit.me.lib_base.utils.route

import android.content.Context
import android.content.Intent

object Router {
    private val routeMap = mutableMapOf<String, Class<*>>()

    // 注册Activity，将其路径和对应的Activity Class保存到map中
    fun registerActivity(path: String, clazz: Class<*>) {
        routeMap[path] = clazz
    }

    // 跳转到目标Activity
    fun navigate(context: Context, path: String, params: Map<String, Any>? = null) {
        val clazz = routeMap[path]
        if (clazz != null) {
            val intent = Intent(context, clazz)
            // 将传入的参数放入Intent中
            params?.forEach { (key, value) ->
                when (value) {
                    is String -> intent.putExtra(key, value)
                    is Int -> intent.putExtra(key, value)
                    is Boolean -> intent.putExtra(key, value)
                    // 你可以根据需要扩展更多类型
                }
            }
            context.startActivity(intent)
        } else {
            // 处理找不到对应Activity的情况
            throw IllegalArgumentException("No activity found for path: $path")
        }
    }
}
