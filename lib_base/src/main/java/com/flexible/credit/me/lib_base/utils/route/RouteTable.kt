package com.flexible.credit.me.lib_base.utils.route

object RouteTable {
    const val LOGIN = "/login/LoginActivity"
    const val MAIN = "/main/MainActivity"
    const val ORDERCONFIRMATION = "/order/OrderConfirmation"
    const val ORROWINGHISTORY = "/history/BorrowingHistory"
    const val BANKMANAGEMENT = "/history/BankManagement"
    const val ABOUTMY = "/about/AboutMy"
    const val SET = "/set/SetActivity"

    val routeMap = mapOf(
        LOGIN to "com.flexible.credit.me.look.ui.login.LoginActivity",
        MAIN to "com.flexible.credit.me.look.MainActivity",
        ORDERCONFIRMATION to "com.flexible.credit.me.look.ui.order.OrderConfirmationActivity",
        ORROWINGHISTORY to "com.flexible.credit.me.look.ui.history.BorrowingHistoryActivity",
        BANKMANAGEMENT to "com.flexible.credit.me.look.ui.bank.BankManagementActivity",
        ABOUTMY to "com.flexible.credit.me.look.ui.about.AboutMyActivity",
        SET to "com.flexible.credit.me.look.ui.set.SetActivity",
    )
}