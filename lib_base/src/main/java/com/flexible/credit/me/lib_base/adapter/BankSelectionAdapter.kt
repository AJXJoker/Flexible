package com.flexible.credit.me.lib_base.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.flexible.credit.me.lib_base.R


class BankSelectionAdapter(
    private val context: Context,
    private val bankOptions: List<String>,
    private var selectedBank: String? = null,
    private val onBankSelected: (String) -> Unit
) : RecyclerView.Adapter<BankSelectionAdapter.BankViewHolder>() {

    // 记录当前选中的位置
    private var selectedPosition: Int = -1

    init {
        // 初始化时设置选中状态
        selectedBank?.let {
            selectedPosition = bankOptions.indexOf(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_bank_radio_button, parent, false)
        return BankViewHolder(view)
    }

    override fun onBindViewHolder(holder: BankViewHolder, position: Int) {
        val bank = bankOptions[position]
        holder.radioButton.text = bank

        val wh = dpToPx(16, context)

        val drawable = ContextCompat.getDrawable(context, R.drawable.radio_button_selector)
        drawable?.setBounds(0, 0, wh, wh)

        holder.radioButton.setCompoundDrawables(
            null, // left drawable
            null, // top drawable
            drawable, // right drawable (drawableEnd)
            null  // bottom drawable
        )
        // 创建 ColorStateList 动态设置字体颜色
        val colorStateList = ContextCompat.getColorStateList(
            context,
            R.color.radio_button_text_selector // 使用 selector 资源
        )
        holder.radioButton.setTextColor(colorStateList)

        val bankName = bankOptions[position]
        holder.radioButton.text = bankName

        // 绑定选中状态
        holder.radioButton.isChecked = position == selectedPosition

        // 点击事件
        holder.radioButton.setOnClickListener {
            if (selectedPosition != position) {
                // 更新选中的位置
                val previousPosition = selectedPosition
                selectedPosition = position
                notifyItemChanged(previousPosition)  // 更新之前选中的按钮
                notifyItemChanged(selectedPosition)  // 更新当前选中的按钮
                onBankSelected(bankName)             // 回调选中的银行
            }
        }
    }

    override fun getItemCount(): Int = bankOptions.size

    class BankViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val radioButton: RadioButton = view.findViewById(R.id.radioButton)
    }

    fun dpToPx(dp: Int, context: Context): Int {
        return (dp * context.resources.displayMetrics.density).toInt()
    }

}