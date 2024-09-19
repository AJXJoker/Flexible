package com.flexible.credit.me.lib_base.utils

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import java.lang.reflect.Type

object GsonUtil {

    private val gson = Gson()

    /**
     * 将 JSON 字符串解析为指定的类型对象
     * @param json 要解析的 JSON 字符串
     * @param typeOfT 目标对象的类型
     * @return 成功时返回解析后的对象，失败时返回 null
     */
    fun <T> fromJson(json: String, typeOfT: Type): T? {
        return try {
            gson.fromJson(json, typeOfT)
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 将对象转换为 JSON 字符串
     * @param src 要转换的对象
     * @return 转换后的 JSON 字符串
     */
    fun toJson(src: Any): String {
        return gson.toJson(src)
    }
}
