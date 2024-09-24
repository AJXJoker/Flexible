package com.flexible.credit.me.look.viewmodel.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.flexible.credit.me.lib_base.base.BaseViewModel
import com.flexible.credit.me.lib_base.http.Order
import com.flexible.credit.me.lib_base.http.Product
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HistoryViewModel : BaseViewModel() {




    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>> get() = _orders

    // 模拟获取订单数据
    fun getMockOrders() {
        viewModelScope.launch {
            // 创建模拟数据
            val mockOrders = listOf(
                Order(
                    idFIfKrAE = "Order001",
                    amountAs1NQoA = 1500.0,
                    statusHbpsDDn = "NEW",
                    statusNotey6OivtC = "New order",
                    createdxxPxuKQ = "2024-01-01",
                    termW1yCzAm = 30,
                    termUnitEBqOUgA = "DAY",
                    delayAmountJ4tGjnt = null,
                    productgtUIkIB = Product(
                        nameverpNre = "Loan Product",
                        iconjmFxqYT = "icon_url",
                        termW1yCzAm = "30 days",
                        amountAs1NQoA = "1500"
                    )
                ),
                Order(
                    idFIfKrAE = "Order002",
                    amountAs1NQoA = 2000.0,
                    statusHbpsDDn = "PASS",
                    statusNotey6OivtC = "Order approved",
                    createdxxPxuKQ = "2024-01-02",
                    termW1yCzAm = 60,
                    termUnitEBqOUgA = "DAY",
                    delayAmountJ4tGjnt = null,
                    productgtUIkIB = Product(
                        nameverpNre = "Investment Product",
                        iconjmFxqYT = "icon_url",
                        termW1yCzAm = "60 days",
                        amountAs1NQoA = "2000"
                    )
                )
                // 添加更多模拟订单...
            )

            // 将模拟数据设置到 _orders
            _orders.value = mockOrders
        }
    }



}