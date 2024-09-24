package com.flexible.credit.me.look.adapter.dialog

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.flexible.credit.me.look.R

class ChildrenAdapter(
    private val jobTypes: List<String>,
    private val onItemSelected: (String) -> Unit
) : RecyclerView.Adapter<ChildrenAdapter.JobTypeViewHolder>() {

    private var selectedPosition: Int = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobTypeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_children, parent, false)
        return JobTypeViewHolder(view)
    }

    override fun onBindViewHolder(holder: JobTypeViewHolder, position: Int) {
        val jobType = jobTypes[position]
        holder.bind(jobType, position)
    }

    override fun getItemCount(): Int = jobTypes.size

    inner class JobTypeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvJobType: TextView = itemView.findViewById(R.id.tvJobType)

        fun bind(jobType: String, position: Int) {
            tvJobType.text = jobType

            // 更改文本颜色和字体大小
            if (position == selectedPosition) {
                tvJobType.setTextColor(Color.parseColor("#317CEB")) // 选中时文本颜色
            } else {
                tvJobType.setTextColor(Color.parseColor("#333333")) // 默认文本颜色
            }

            itemView.setOnClickListener {
                // 更新选中位置
                val previousPosition = selectedPosition
                selectedPosition = position

                // 通知适配器更新UI
                notifyItemChanged(previousPosition)
                notifyItemChanged(position)

                // 回调选择的类型
                onItemSelected(jobType)
            }
        }
    }

}
