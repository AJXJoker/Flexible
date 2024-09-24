package com.flexible.credit.me.lib_base.utils

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException

// JSON 处理工具类
object JsonUtils {

    // Gson 实例可以作为单例
    private val gson: Gson by lazy { Gson() }

    // 移除 JSON 字符串中的所有空格
    fun removeWhitespaceFromJson(requestBody: Any): String {
        // 使用 Gson 序列化为 JSON 字符串
        val jsonString = gson.toJson(requestBody)

        // 移除 JSON 字符串中的所有空格
        return jsonString.replace("\\s".toRegex(), "")
    }

    // 将对象转换为 JSON 字符串
    fun toJson(obj: Any?): String {
        return try {
            gson.toJson(obj)
        } catch (e: Exception) {
            "Error converting to JSON: ${e.message}"
        }
    }

    // 将 JSON 字符串转换为对象
    fun <T> fromJson(json: String, clazz: Class<T>): T? {
        return try {
            gson.fromJson(json, clazz)
        } catch (e: JsonSyntaxException) {
            null // 解析失败返回 null
        }
    }
}