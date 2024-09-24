package com.flexible.credit.me.look.adapter.dialog

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.flexible.credit.me.look.R

// JobTypeAdapter
class JobTypeAdapter(
    private val jobTypes: List<String>,
    private val onItemSelected: (String) -> Unit
) : RecyclerView.Adapter<JobTypeAdapter.JobTypeViewHolder>() {

    private var selectedPosition: Int = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobTypeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_job_type, parent, false)
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

// JobPositionAdapter
class JobPositionAdapter(
    private var jobPositions: List<String>,
    private val onItemSelected: (String) -> Unit
) : RecyclerView.Adapter<JobPositionAdapter.JobPositionViewHolder>() {

    private var selectedPosition: Int = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobPositionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_job_type, parent, false) // 使用相同的布局文件
        return JobPositionViewHolder(view)
    }

    override fun onBindViewHolder(holder: JobPositionViewHolder, position: Int) {
        val jobPosition = jobPositions[position]
        holder.bind(jobPosition, position)
    }

    override fun getItemCount(): Int = jobPositions.size

    inner class JobPositionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvJobType: TextView = itemView.findViewById(R.id.tvJobType)

        fun bind(jobPosition: String, position: Int) {
            tvJobType.text = jobPosition

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

                // 回调选择的岗位
                onItemSelected(jobPosition)
            }
        }
    }

    fun updateData(newJobPositions: List<String>) {
        this.jobPositions = newJobPositions
        selectedPosition = RecyclerView.NO_POSITION // 重置选中状态
        notifyDataSetChanged()
    }
}
