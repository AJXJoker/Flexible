package com.flexible.credit.me.look.adapter.bank

import android.widget.TextView
import com.flexible.credit.me.lib_base.base.BaseRecyclerViewAdapter
import com.flexible.credit.me.lib_base.http.Order
import com.flexible.credit.me.look.R

class BankManagementAdapter(private val orders: List<Order>) :
    BaseRecyclerViewAdapter<Order>(orders.toMutableList(), R.layout.item_bank_management) {


    override fun bind(holder: BaseViewHolder, item: Order, position: Int) {
        val tv_bank_name = holder.itemView.findViewById<TextView>(R.id.tv_bank_name)
        val tv_bank_card_number = holder.itemView.findViewById<TextView>(R.id.tv_bank_card_number)
        val tv_bank_car_number = holder.itemView.findViewById<TextView>(R.id.tv_bank_car_number)

    }

}