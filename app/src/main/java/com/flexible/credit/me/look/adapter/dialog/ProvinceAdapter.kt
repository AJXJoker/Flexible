package com.flexible.credit.me.look.adapter.dialog

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.flexible.credit.me.lib_base.http.AddressResponseData
import com.flexible.credit.me.look.R

class ProvinceAdapter(
    private val jobTypes: List<AddressResponseData>,
    private val onItemSelected: (AddressResponseData) -> Unit
) : RecyclerView.Adapter<ProvinceAdapter.JobTypeViewHolder>() {

    private var selectedPosition: Int = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobTypeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_job_type, parent, false)
        return JobTypeViewHolder(view)
    }

    override fun onBindViewHolder(holder: JobTypeViewHolder, position: Int) {
        val addressResponseData = jobTypes[position]
        holder.bind(addressResponseData, position)
    }

    override fun getItemCount(): Int = jobTypes.size

    inner class JobTypeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvJobType: TextView = itemView.findViewById(R.id.tvJobType)

        fun bind(addressResponseData: AddressResponseData, position: Int) {
            val provinceMsg = addressResponseData.nameverpNre
            tvJobType.text = provinceMsg

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
                onItemSelected(addressResponseData)
            }
        }
    }
}

// JobPositionAdapter
class CityAdapter(
    private var jobPositions: List<AddressResponseData>,
    private val onItemSelected: (AddressResponseData) -> Unit
) : RecyclerView.Adapter<CityAdapter.JobPositionViewHolder>() {

    private var selectedPosition: Int = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobPositionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_job_type, parent, false) // 使用相同的布局文件
        return JobPositionViewHolder(view)
    }

    override fun onBindViewHolder(holder: JobPositionViewHolder, position: Int) {
        val addressResponseData = jobPositions[position]
        holder.bind(addressResponseData, position)
    }

    override fun getItemCount(): Int = jobPositions.size

    inner class JobPositionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvJobType: TextView = itemView.findViewById(R.id.tvJobType)

        fun bind(addressResponseData: AddressResponseData, position: Int) {
            val provinceMsg = addressResponseData.nameverpNre
            tvJobType.text = provinceMsg

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
                onItemSelected(addressResponseData)
            }
        }
    }

    fun updateData(newJobPositions: List<AddressResponseData>) {
        this.jobPositions = newJobPositions
        selectedPosition = RecyclerView.NO_POSITION // 重置选中状态
        notifyDataSetChanged()
    }
}
