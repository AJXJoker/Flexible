package com.flexible.credit.me.lib_base.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

object SharedPreferencesUtil {

    private const val PREFERENCE_NAME = "app_shared_prefs"  // SharedPreferences 文件名
    private lateinit var sharedPreferences: SharedPreferences
    private val gson = Gson()

    // 初始化方法，在 Application 或 Activity 中调用一次
    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    // 保存字符串数据
    fun saveString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    // 获取字符串数据
    fun getString(key: String, defaultValue: String = ""): String? {
        return sharedPreferences.getString(key, defaultValue)
    }

    // 保存对象为 JSON 格式
    fun <T> saveObject(key: String, obj: T) {
        val jsonString = gson.toJson(obj)
        sharedPreferences.edit().putString(key, jsonString).apply()
    }

    // 获取 JSON 格式对象
    fun <T> getObject(key: String, clazz: Class<T>): T? {
        val jsonString = sharedPreferences.getString(key, null)
        return if (!jsonString.isNullOrEmpty()) {
            gson.fromJson(jsonString, clazz)
        } else {
            null
        }
    }

    // 清除所有数据
    fun clear() {
        sharedPreferences.edit().clear().apply()
    }

    // 根据 key 清除数据
    fun remove(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

}