package com.flexible.credit.me.look.viewmodel.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.flexible.credit.me.lib_base.base.BaseViewModel
import com.flexible.credit.me.lib_base.http.ApiResponse
import com.flexible.credit.me.lib_base.http.Order
import com.flexible.credit.me.lib_base.http.Product
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OrderViewModel : BaseViewModel() {


    /*private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>> get() = _orders

    fun getOrders(apiService: suspend () -> ApiResponse<List<Order>>) {
        apiCall(
            call = apiService,
            onSuccess = { orders ->
                _orders.value = orders
            }
        )
    }*/


    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>> get() = _orders

    // 模拟获取订单数据
    fun getMockOrders() {
        viewModelScope.launch {
            // 创建模拟数据
            val mockOrders = listOf(
                Order(
                    "Order001",
                    1500.0,
                    "NEW",
                    "New order",
                    "2024-01-01",
                    30,
                    "DAY",
                    null,
                    Product("Loan Product", "icon_url", "30 days", "1500"),
                    "John Doe",
                    "Male",
                    "Software Engineer",
                    "A",
                    "Good credit",
                    true,
                    true,
                    "A",
                    "Good contacts",
                    true,
                    "A",
                    "Good SMS",
                    true,
                    "A",
                    "Good banking",
                    850,
                    "A",
                    "High credit score"
                ),
                Order(
                    "Order002",
                    2000.0,
                    "PASS",
                    "Order approved",
                    "2024-01-02",
                    60,
                    "DAY",
                    null,
                    Product("Investment Product", "icon_url", "60 days", "2000"),
                    "Jane Smith",
                    "Female",
                    "Doctor",
                    "A",
                    "Good credit",
                    true,
                    true,
                    "A",
                    "Good contacts",
                    true,
                    "A",
                    "Good SMS",
                    true,
                    "A",
                    "Good banking",
                    900,
                    "A",
                    "High credit score"
                ),
                // 添加更多模拟订单...
            )

            // 将模拟数据设置到 _orders
            _orders.value = mockOrders
        }
    }


}