package com.flexible.credit.me.look.adapter.order

import android.widget.CheckBox
import android.widget.TextView
import com.flexible.credit.me.lib_base.base.BaseRecyclerViewAdapter
import com.flexible.credit.me.lib_base.http.Order
import com.flexible.credit.me.look.R

class OrderAdapter(private val orders: List<Order>) :
    BaseRecyclerViewAdapter<Order>(orders.toMutableList(), R.layout.item_home_order) {

    private val checkedStates = mutableMapOf<Int, Boolean>()

    override fun bind(holder: BaseViewHolder, item: Order, position: Int) {
        val tvTotalMoney = holder.itemView.findViewById<TextView>(R.id.tv_total_money)
        val tvAmountRepaid = holder.itemView.findViewById<TextView>(R.id.tv_amount_repaid)
        val chbOrder = holder.itemView.findViewById<CheckBox>(R.id.chb_order)

        // 设置文本信息
        /*tvTotalMoney.text = item.totalMoney.toString()
        tvAmountRepaid.text = item.amountRepaid.toString()*/

        // 根据 checkedStates 恢复复选框的选中状态
        chbOrder.isChecked = checkedStates[position] ?: false

        // 设置复选框选中状态监听器
        chbOrder.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // 复选框选中时保存状态
                checkedStates[position] = true
            } else {
                // 复选框取消选中时从 checkedStates 中移除状态
                checkedStates.remove(position)
            }
        }
    }

    fun getSelectedOrders(): List<Order> {
        return orders.filterIndexed { index, _ -> checkedStates[index] == true }
    }
}