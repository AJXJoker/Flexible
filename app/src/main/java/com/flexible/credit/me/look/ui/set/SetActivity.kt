package com.flexible.credit.me.look.ui.set

import androidx.recyclerview.widget.LinearLayoutManager
import com.flexible.credit.me.lib_base.base.BaseDataBindingActivity
import com.flexible.credit.me.lib_base.utils.StatusBarUtil
import com.flexible.credit.me.lib_base.utils.route.Route
import com.flexible.credit.me.lib_base.utils.route.RouteTable
import com.flexible.credit.me.look.R
import com.flexible.credit.me.look.databinding.ActivityAboutMyBinding
import com.flexible.credit.me.look.databinding.ActivitySetBinding
import com.flexible.credit.me.look.viewmodel.history.HistoryViewModel

@Route(path = RouteTable.SET)
class SetActivity :
    BaseDataBindingActivity<HistoryViewModel, ActivitySetBinding>() {


    override fun getLayoutId(): Int = R.layout.activity_set

    override fun initView() {
        StatusBarUtil.addStatusBarMargin(mDataBinding.incTop.clHeader)
        mDataBinding.incTop.tvTitle.text = "设置"
    }

    override fun initData() {


    }

    override fun initEvent() {


    }
}