package com.flexible.credit.me.look.adapter.history

import android.widget.CheckBox
import android.widget.TextView
import com.flexible.credit.me.lib_base.base.BaseRecyclerViewAdapter
import com.flexible.credit.me.lib_base.http.Order
import com.flexible.credit.me.look.R

class BorrowingHistoryAdapter (private val orders: List<Order>) :
    BaseRecyclerViewAdapter<Order>(orders.toMutableList(), R.layout.item_borrowing_history) {


    override fun bind(holder: BaseViewHolder, item: Order, position: Int) {
        val tv_order_name = holder.itemView.findViewById<TextView>(R.id.tv_order_name)
        val tv_state = holder.itemView.findViewById<TextView>(R.id.tv_state)
        val tv_jk_money = holder.itemView.findViewById<TextView>(R.id.tv_jk_money)
        val tv_apply_time = holder.itemView.findViewById<TextView>(R.id.tv_apply_time)

    }

}