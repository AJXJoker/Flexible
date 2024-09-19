package com.flexible.credit.me.lib_base.utils

import java.io.IOException

/**
 * 自定义异常信息显示
 */
data class PassRoomException(var msg: String,var code: Int) : IOException()