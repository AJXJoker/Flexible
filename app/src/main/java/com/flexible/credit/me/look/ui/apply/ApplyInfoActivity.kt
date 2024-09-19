package com.flexible.credit.me.look.ui.apply

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.flexible.credit.me.lib_base.base.BaseDataBindingActivity
import com.flexible.credit.me.lib_base.model.Step
import com.flexible.credit.me.lib_base.utils.StatusBarUtil
import com.flexible.credit.me.lib_base.utils.route.Route
import com.flexible.credit.me.lib_base.utils.route.RouteTable
import com.flexible.credit.me.look.R
import com.flexible.credit.me.look.adapter.ProgressAdapter
import com.flexible.credit.me.look.databinding.ActivityApplyInfoBinding
import com.flexible.credit.me.look.databinding.ActivityLoginBinding
import com.flexible.credit.me.look.view.DividerItemDecoration
import com.flexible.credit.me.look.viewmodel.login.LoginViewModel

@Route(path = RouteTable.APPLYINFO)
class ApplyInfoActivity : BaseDataBindingActivity<LoginViewModel, ActivityApplyInfoBinding>() {

    private lateinit var adapter: ProgressAdapter


    override fun getLayoutId(): Int = R.layout.activity_apply_info

    override fun initView() {
        updateTitle("个人信息")
        StatusBarUtil.addStatusBarMargin(mDataBinding.incTop.clHeader)

        val steps = arrayListOf(
            Step("个人信息", isSelected = true),
            Step("联系人", isSelected = false),
            Step("银行卡", isSelected = false),
            Step("OCR", isSelected = false),
            Step("活体认证", isSelected = false)
        )

        val gridLayoutManager =
            GridLayoutManager(this, steps.size, GridLayoutManager.VERTICAL, false)
        mDataBinding.rvStateList.layoutManager = gridLayoutManager

        // 设置 Adapter
        adapter = ProgressAdapter(steps)
        mDataBinding.rvStateList.adapter = adapter

    }

    override fun initData() {
    }

    override fun initEvent() {
    }


    private fun updateTitle(title: String) {
        if (title.isEmpty())
            return
        mDataBinding.incTop.tvTitle.text = title
    }

}