package com.flexible.credit.me.look.ui.splash

import com.flexible.credit.me.lib_base.base.BaseDataBindingActivity
import com.flexible.credit.me.lib_base.utils.route.RouteTable
import com.flexible.credit.me.lib_base.utils.route.Router
import com.flexible.credit.me.look.R
import com.flexible.credit.me.look.databinding.ActivityLaunchBinding
import com.flexible.credit.me.look.viewmodel.home.MainViewModel

class SplashActivity : BaseDataBindingActivity<MainViewModel, ActivityLaunchBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_launch


    override fun initView() {
        Router.navigate(this, RouteTable.LOGIN)

    }

    override fun initData() {
    }

    override fun initEvent() {
    }

}