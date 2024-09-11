package com.flexible.credit.me.lib_base.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.util.DisplayMetrics
import androidx.core.content.edit
import java.util.Locale

object LanguageUtil {

    // 语言类型
    private const val ENGLISH = "en"
    private const val CHINESE = "zh"
    private const val TRADITIONAL_CHINESE = "zh_rTW"
    private const val THAI = "th"  // 添加泰语

    // 存储语言的键
    private const val LANGUAGE_KEY = "language_key"

    // 支持的语言列表
    private val languagesList = mapOf(
        ENGLISH to Locale.ENGLISH,
        CHINESE to Locale.CHINESE,
        TRADITIONAL_CHINESE to Locale.TRADITIONAL_CHINESE,
        THAI to Locale("th")  // 加入泰语
    )

    // 获取 SharedPreferences
    private fun getPreferences(context: Context) =
        context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

    /**
     * 修改语言
     *
     * @param activity 上下文
     * @param language 传入的语言代码，例如修改为英文传 "en"
     * @param cls 要跳转的类（一般为应用入口类）
     */
    fun changeAppLanguage(activity: Activity, language: String, cls: Class<*>) {
        val locale = getLocaleByLanguage(language)

        // 更新配置
        updateLocale(activity, locale)

        // 保存语言设置到 SharedPreferences
        getPreferences(activity).edit {
            putString(LANGUAGE_KEY, language)
        }

        // 打印当前语言
        LoggerUtils.d("当前语言: ${locale.language}")

        // 重启应用
        restartApp(activity, cls)
    }

    /**
     * 更新 Locale 设置
     */
    private fun updateLocale(context: Context, locale: Locale) {
        Locale.setDefault(locale)
        val resources: Resources = context.resources
        val config: Configuration = resources.configuration
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale)
        } else {
            config.locale = locale
        }
        val dm: DisplayMetrics = resources.displayMetrics
        resources.updateConfiguration(config, dm)
    }

    /**
     * 重启应用
     */
    private fun restartApp(activity: Activity, cls: Class<*>) {
        val intent = Intent(activity, cls).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        activity.startActivity(intent)
    }

    /**
     * 获取指定语言的 Locale 信息，如果指定语言不存在，则返回系统语言
     */
    private fun getLocaleByLanguage(language: String): Locale {
        return languagesList[language] ?: Locale.getDefault().run {
            languagesList.values.find { it.language == this.language } ?: Locale.ENGLISH
        }
    }

    /**
     * 加载已保存的语言设置
     */
    fun loadSavedLanguage(context: Context): Locale {
        val language = getPreferences(context).getString(LANGUAGE_KEY, null)
        return getLocaleByLanguage(language ?: Locale.getDefault().language)
        //return getLocaleByLanguage(language ?: ENGLISH) // 将默认语言改为英文
    }

    /**
     * 在 Application 中使用的语言初始化方法
     */
    fun applySavedLanguage(context: Context): Context {
        val locale = loadSavedLanguage(context)
        // 打印初始化选择的语言
        //LoggerUtils.d("初始化选择的语言: ${locale.language}")
        updateLocale(context, locale)
        return context
    }
}
