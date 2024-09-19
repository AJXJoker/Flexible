package com.flexible.credit.me.look.ui.splash

import android.content.Context
import com.flexible.credit.me.lib_base.FlexibleApplication
import com.flexible.credit.me.lib_base.base.BaseDataBindingActivity
import com.flexible.credit.me.lib_base.base.Const
import com.flexible.credit.me.lib_base.utils.DeviceIdUtil
import com.flexible.credit.me.lib_base.utils.LoggerUtils
import com.flexible.credit.me.lib_base.utils.route.RouteTable
import com.flexible.credit.me.lib_base.utils.route.Router
import com.flexible.credit.me.look.R
import com.flexible.credit.me.look.databinding.ActivityLaunchBinding
import com.flexible.credit.me.look.viewmodel.home.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashActivity : BaseDataBindingActivity<MainViewModel, ActivityLaunchBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_launch


    override fun initView() {
    }

    override fun initData() {
        fetchDeviceIds(this)
    }

    override fun initEvent() {
    }


    fun fetchDeviceIds(context: Context) {
        CoroutineScope(Dispatchers.Main).launch {
            // 在协程中调用 suspend 函数 getDeviceIds
            val deviceIdUtil = DeviceIdUtil()

            // 在协程中调用 suspend 函数 getDeviceIds
            val deviceIds = deviceIdUtil.getDeviceIds(context)
            Const.deviceIds = deviceIds
            LoggerUtils.d("Android ID: ${deviceIds.androidId}")
            LoggerUtils.d("Google Ad ID: ${deviceIds.googleAdId}")

            Router.navigate(this@SplashActivity, RouteTable.LOGIN)
            finish()
        }
    }

}