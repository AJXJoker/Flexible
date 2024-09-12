package com.flexible.credit.me.look.ui.history

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.flexible.credit.me.lib_base.base.BaseDataBindingActivity
import com.flexible.credit.me.lib_base.utils.StatusBarUtil
import com.flexible.credit.me.lib_base.utils.route.Route
import com.flexible.credit.me.lib_base.utils.route.RouteTable
import com.flexible.credit.me.look.R
import com.flexible.credit.me.look.adapter.history.BorrowingHistoryAdapter
import com.flexible.credit.me.look.adapter.order.OrderAdapter
import com.flexible.credit.me.look.databinding.ActivityBorrowingHistoryBinding
import com.flexible.credit.me.look.viewmodel.history.HistoryViewModel
import com.flexible.credit.me.look.viewmodel.login.LoginViewModel

@Route(path = RouteTable.ORROWINGHISTORY)
class BorrowingHistoryActivity :
    BaseDataBindingActivity<HistoryViewModel, ActivityBorrowingHistoryBinding>() {

    private lateinit var historyAdapter: BorrowingHistoryAdapter

    override fun getLayoutId(): Int = R.layout.activity_borrowing_history

    override fun initView() {
        StatusBarUtil.addStatusBarMargin(mDataBinding.incTop.clHeader)
        mDataBinding.incTop.tvTitle.text = "借款历史"
    }

    override fun initData() {

        historyAdapter = BorrowingHistoryAdapter(listOf()) // 初始化空的订单列表
        mDataBinding.rvHistoryList.layoutManager = LinearLayoutManager(this)
        mDataBinding.rvHistoryList.adapter = historyAdapter

        viewModel.getMockOrders()

    }

    override fun initEvent() {

        viewModel.orders.observe(this, Observer { orders ->
            historyAdapter.updateData(orders ?: emptyList())
        })

    }
}