package com.flexible.credit.me.look.ui.about

import androidx.recyclerview.widget.LinearLayoutManager
import com.flexible.credit.me.lib_base.base.BaseDataBindingActivity
import com.flexible.credit.me.lib_base.utils.StatusBarUtil
import com.flexible.credit.me.lib_base.utils.route.Route
import com.flexible.credit.me.lib_base.utils.route.RouteTable
import com.flexible.credit.me.look.R
import com.flexible.credit.me.look.databinding.ActivityAboutMyBinding
import com.flexible.credit.me.look.viewmodel.history.HistoryViewModel
@Route(path = RouteTable.ABOUTMY)
class AboutMyActivity :
    BaseDataBindingActivity<HistoryViewModel, ActivityAboutMyBinding>() {


    override fun getLayoutId(): Int = R.layout.activity_about_my

    override fun initView() {
        StatusBarUtil.addStatusBarMargin(mDataBinding.incTop.clHeader)
        mDataBinding.incTop.tvTitle.text = "关于我们"
    }

    override fun initData() {


    }

    override fun initEvent() {


    }
}