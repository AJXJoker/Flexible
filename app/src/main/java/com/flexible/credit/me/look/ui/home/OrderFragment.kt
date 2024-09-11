package com.flexible.credit.me.look.ui.home

import android.view.View
import com.flexible.credit.me.lib_base.base.BaseDataBindingFragment
import com.flexible.credit.me.lib_base.utils.LoggerUtils
import com.flexible.credit.me.look.R
import com.flexible.credit.me.look.databinding.FragmentOrderBinding
import com.flexible.credit.me.look.viewmodel.home.HomeViewModel

class OrderFragment : BaseDataBindingFragment<HomeViewModel, FragmentOrderBinding>() {


    override fun getLayoutId(): Int = R.layout.fragment_order

    override fun initView(view: View) {
        // 初始化视图
        LoggerUtils.d("订单页面初始化视图")
    }

    override fun loadData() {
        // 加载数据，仅在懒加载条件满足时调用
        LoggerUtils.d("订单页面加载数据，仅在懒加载条件满足时调用")
    }

    override fun initEvent() {
        // 初始化事件
    }

}