package com.flexible.credit.me.look.viewmodel.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.flexible.credit.me.lib_base.base.BaseViewModel
import com.flexible.credit.me.lib_base.http.IncompleteFormRequest
import com.flexible.credit.me.lib_base.http.IncompleteFormRequestData
import com.flexible.credit.me.lib_base.http.NetworkResponseHandler
import com.flexible.credit.me.lib_base.http.Order
import com.flexible.credit.me.lib_base.http.OrderListRequest
import com.flexible.credit.me.lib_base.http.OrderListRequestData
import com.flexible.credit.me.lib_base.http.OrderResponse
import com.flexible.credit.me.lib_base.http.Product
import com.flexible.credit.me.lib_base.http.RetrofitClient
import com.flexible.credit.me.lib_base.http.SpecificFormResponse
import com.flexible.credit.me.lib_base.utils.AESEncryptionUtil
import com.flexible.credit.me.lib_base.utils.GsonUtil
import com.flexible.credit.me.lib_base.utils.JsonUtils
import com.flexible.credit.me.lib_base.utils.LoggerUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OrderViewModel : BaseViewModel() {


    var page = 1
    var pageSize = 20

    //private val _orders = MutableLiveData<List<Order>>()

    private val _orders = MutableLiveData<List<Order>>()


    val orders: LiveData<List<Order>> get() = _orders

    //订单列表
    fun getOrderList() {
        launchUI {
            try {
                val formattedJson = buildOrderListRequestBody()
                val reqBody = AESEncryptionUtil.encrypt(formattedJson, AESEncryptionUtil.AESKEY)

                val response = RetrofitClient.apiService.getOrderList(reqBody)
                NetworkResponseHandler.handleResponse(
                    response,
                    onSuccess = { encryptedText ->
                        // 成功时处理加密文本
                        val orderResponse: OrderResponse? =
                            GsonUtil.fromJson(encryptedText, OrderResponse::class.java)
                        if (orderResponse != null) {
                            if (orderResponse.statusHbpsDDn == 0) {
                                var orderList = orderResponse.pageL9DQhgx.contentcQBAh1g
                                _orders.value = orderList
                            }
                        }
                        LoggerUtils.logLongMessage("订单列表 Response: $encryptedText")
                        // 继续处理其他逻辑
                    },
                    onFailure = { code, message ->
                        // 失败时处理错误信息
                        LoggerUtils.d("订单列表  Failed with code: $code, message: $message")
                    }
                )

            } catch (e: Exception) {
            }
        }
    }


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


    /**
     * 构建获取未完成表单的请求体
     */
    fun buildOrderListRequestBody(nodeType: String = "NODE1"): String {
        val requestBody = OrderListRequest(
            queryCoJgzSW = OrderListRequestData(
                pageNoXCuUUqT = page,
                pageSizea23cerF = pageSize,
                // statusListYztS2i7 = ""
            )
        )
        return JsonUtils.removeWhitespaceFromJson(requestBody)
    }

}