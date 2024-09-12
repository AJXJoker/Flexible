package com.flexible.credit.me.lib_base.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter<T>(
    private var items: MutableList<T>, // 改为 MutableList 以支持数据更新
    private val layoutId: Int
) : RecyclerView.Adapter<BaseRecyclerViewAdapter.BaseViewHolder>() {

    var onItemClick: ((item: T, position: Int) -> Unit)? = null

    class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return BaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(items[position], position)
        }
        bind(holder, items[position], position)
    }

    abstract fun bind(holder: BaseViewHolder, item: T, position: Int)

    override fun getItemCount(): Int = items.size

    // 添加更新数据方法
    fun updateData(newItems: List<T>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged() // 刷新整个列表
    }

    // 添加更新单个项方法
    fun updateItem(position: Int, newItem: T) {
        if (position in items.indices) {
            items[position] = newItem
            notifyItemChanged(position) // 刷新单个列表项
        }
    }
}
