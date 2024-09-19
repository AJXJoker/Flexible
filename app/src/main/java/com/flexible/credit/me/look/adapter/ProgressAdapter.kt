package com.flexible.credit.me.look.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.flexible.credit.me.lib_base.model.Step
import com.flexible.credit.me.look.R

class ProgressAdapter(private val steps: List<Step>) :
    RecyclerView.Adapter<ProgressAdapter.StepViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_progress_step, parent, false)
        return StepViewHolder(view)
    }

    override fun onBindViewHolder(holder: StepViewHolder, position: Int) {
        val step = steps[position]

        // 更新标题
        holder.stepTitle.text = step.title

        // 根据是否选中来改变图标
        if (step.isSelected) {
            holder.stepIcon.setImageResource(R.mipmap.ic_add_user_info_state_1) // 选中状态图标
        } else {
            holder.stepIcon.setImageResource(R.mipmap.ic_add_user_info_state_def_1) // 未选中状态图标
        }

        if (position == steps.size - 1) {
            holder.rightLine.visibility = View.INVISIBLE
        } else {
            holder.rightLine.visibility = View.VISIBLE
        }
        if (position == 0) {
            holder.leftLine.visibility = View.GONE
        } else {
            holder.leftLine.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int = steps.size

    // ViewHolder类
    class StepViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val stepIcon: ImageView = view.findViewById(R.id.stepIcon)
        val stepTitle: TextView = view.findViewById(R.id.stepTitle)

        val leftLine: View = view.findViewById(R.id.leftLine)
        val rightLine: View = view.findViewById(R.id.rightLine)
    }
}
