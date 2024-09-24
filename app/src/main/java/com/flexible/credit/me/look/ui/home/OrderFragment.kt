package com.flexible.credit.me.look.ui.home

import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.flexible.credit.me.lib_base.base.BaseDataBindingFragment
import com.flexible.credit.me.lib_base.utils.LoggerUtils
import com.flexible.credit.me.lib_base.utils.StatusBarUtil
import com.flexible.credit.me.lib_base.utils.route.RouteTable
import com.flexible.credit.me.lib_base.utils.route.Router
import com.flexible.credit.me.look.R
import com.flexible.credit.me.look.adapter.order.OrderAdapter
import com.flexible.credit.me.look.databinding.FragmentOrderBinding
import com.flexible.credit.me.look.viewmodel.home.HomeViewModel
import com.flexible.credit.me.look.viewmodel.home.OrderViewModel

class OrderFragment : BaseDataBindingFragment<OrderViewModel, FragmentOrderBinding>() {

    private lateinit var orderAdapter: OrderAdapter

    override fun getLayoutId(): Int = R.layout.fragment_order

    override fun initView(view: View) {
        StatusBarUtil.addStatusBarMargin(mDataBinding.clHeader)
    }

    override fun loadData() {
        orderAdapter = OrderAdapter(listOf()) // 初始化空的订单列表
        mDataBinding.rvOrderList.layoutManager = LinearLayoutManager(activity)
        mDataBinding.rvOrderList.adapter = orderAdapter

        viewModel.getMockOrders()

        viewModel.getOrderList()
    }

    override fun initEvent() {
        // 初始化事件

        viewModel.orders.observe(viewLifecycleOwner, Observer { orders ->
            if (orders.isEmpty()) {
                mDataBinding.clEmpty.visibility = View.VISIBLE
            } else {
                mDataBinding.clEmpty.visibility = View.GONE
            }
            orderAdapter.updateData(orders ?: emptyList())
        })

        mDataBinding.tvOk.setOnClickListener {
            Router.navigate(requireActivity(), RouteTable.ORDERCONFIRMATION)
        }

    }

}