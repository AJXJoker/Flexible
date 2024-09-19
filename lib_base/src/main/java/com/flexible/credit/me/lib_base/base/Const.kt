package com.flexible.credit.me.lib_base.base

import com.flexible.credit.me.lib_base.http.LoginResponse
import com.flexible.credit.me.lib_base.utils.DeviceIdUtil

object Const {

    const val LANGUAGECODE = "th"
    var isLogEnabled: Boolean = true
    const val BASE_URL = "https://app.flexiblecredit.live"  //api接口域名
    const val BASE_PAGE_URL = "https://page.flexiblecredit.live"  //page接口域名

    lateinit var deviceIds: DeviceIdUtil.DeviceIds

    // 登录返回的用户信息
    private var cachedLoginResponse: LoginResponse? = null


}