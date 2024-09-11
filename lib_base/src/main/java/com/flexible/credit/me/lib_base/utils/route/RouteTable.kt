package com.flexible.credit.me.lib_base.utils.route

object RouteTable {
    const val LOGIN = "/login/LoginActivity"
    const val MAIN = "/main/MainActivity"


    // 使用 Map 来关联路径与类名
    val routeMap = mapOf(
        LOGIN to "com.flexible.credit.me.look.ui.login.LoginActivity",
        MAIN to "com.flexible.credit.me.look.MainActivity"
    )
}