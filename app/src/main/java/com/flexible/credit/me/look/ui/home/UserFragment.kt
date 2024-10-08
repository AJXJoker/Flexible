package com.flexible.credit.me.look.ui.home

import android.view.View
import com.flexible.credit.me.lib_base.base.BaseDataBindingFragment
import com.flexible.credit.me.lib_base.utils.LoggerUtils
import com.flexible.credit.me.lib_base.utils.StatusBarUtil
import com.flexible.credit.me.lib_base.utils.route.RouteTable
import com.flexible.credit.me.lib_base.utils.route.Router
import com.flexible.credit.me.look.R
import com.flexible.credit.me.look.databinding.FragmentHomeBinding
import com.flexible.credit.me.look.databinding.FragmentUserBinding
import com.flexible.credit.me.look.viewmodel.home.HomeViewModel

class UserFragment : BaseDataBindingFragment<HomeViewModel, FragmentUserBinding>() {


    override fun getLayoutId(): Int = R.layout.fragment_user


    override fun initView(view: View) {
        // 初始化视图
        StatusBarUtil.addStatusBarMargin(mDataBinding.clHeader)
        LoggerUtils.d("用户中心初始化视图")
    }

    override fun loadData() {
        // 加载数据，仅在懒加载条件满足时调用
        LoggerUtils.d("用户中心加载数据，仅在懒加载条件满足时调用")
    }

    override fun initEvent() {
        // 初始化事件

        mDataBinding.clLoanHistory.setOnClickListener {
            Router.navigate(requireActivity(), RouteTable.ORROWINGHISTORY)
        }

        mDataBinding.clBankAccount.setOnClickListener {
            Router.navigate(requireActivity(), RouteTable.BANKMANAGEMENT)
        }

        mDataBinding.clAboutMy.setOnClickListener {
            Router.navigate(requireActivity(), RouteTable.ABOUTMY)
        }

        mDataBinding.clSet.setOnClickListener {
            Router.navigate(requireActivity(), RouteTable.SET)
        }
    }

}