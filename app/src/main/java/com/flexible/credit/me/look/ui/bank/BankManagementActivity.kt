package com.flexible.credit.me.look.ui.bank

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.flexible.credit.me.lib_base.base.BaseDataBindingActivity
import com.flexible.credit.me.lib_base.utils.StatusBarUtil
import com.flexible.credit.me.lib_base.utils.route.Route
import com.flexible.credit.me.lib_base.utils.route.RouteTable
import com.flexible.credit.me.look.R
import com.flexible.credit.me.look.adapter.bank.BankManagementAdapter
import com.flexible.credit.me.look.databinding.ActivityBorrowingHistoryBinding
import com.flexible.credit.me.look.viewmodel.history.HistoryViewModel

@Route(path = RouteTable.BANKMANAGEMENT)
class BankManagementActivity :
    BaseDataBindingActivity<HistoryViewModel, ActivityBorrowingHistoryBinding>() {

    private lateinit var bankManagementAdapter: BankManagementAdapter


    override fun getLayoutId(): Int = R.layout.activity_borrowing_history

    override fun initView() {
        StatusBarUtil.addStatusBarMargin(mDataBinding.incTop.clHeader)
        mDataBinding.incTop.tvTitle.text = "银行卡列表"
    }

    override fun initData() {

        bankManagementAdapter = BankManagementAdapter(listOf())
        mDataBinding.rvHistoryList.layoutManager = LinearLayoutManager(this)
        mDataBinding.rvHistoryList.adapter = bankManagementAdapter

        viewModel.getMockOrders()

    }

    override fun initEvent() {

        viewModel.orders.observe(this, Observer { orders ->
            bankManagementAdapter.updateData(orders ?: emptyList())
        })

    }
}