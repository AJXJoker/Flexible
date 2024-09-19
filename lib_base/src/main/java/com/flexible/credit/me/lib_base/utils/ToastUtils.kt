package com.flexible.credit.me.lib_base.utils

import android.content.Context
import android.view.Gravity
import android.widget.Toast

object ToastUtils {

    fun showToast(context: Context, message: String) {
        val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }
}
