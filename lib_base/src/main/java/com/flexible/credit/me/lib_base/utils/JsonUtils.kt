package com.flexible.credit.me.lib_base.utils

import com.google.gson.Gson

// JSON 处理工具类
object JsonUtils {

    // 移除 JSON 字符串中的所有空格
    fun removeWhitespaceFromJson(requestBody: Any): String {
        // 使用 Gson 序列化为 JSON 字符串
        val gson = Gson()
        val jsonString = gson.toJson(requestBody)

        // 移除 JSON 字符串中的所有空格
        return jsonString.replace("\\s".toRegex(), "")
    }
}