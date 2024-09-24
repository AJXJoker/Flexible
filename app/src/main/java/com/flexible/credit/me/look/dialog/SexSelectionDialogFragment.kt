package com.flexible.credit.me.look.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flexible.credit.me.look.R
import com.flexible.credit.me.look.adapter.dialog.JobPositionAdapter
import com.flexible.credit.me.look.adapter.dialog.JobTypeAdapter

class SexSelectionDialogFragment : DialogFragment() {

    private lateinit var sexTypes: List<String>
    private var selectedSexType: String? = null
    private var onJobSelectedListener: ((String) -> Unit)? = null

    private lateinit var jobTypeAdapter: JobTypeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_job_selection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView for job types
        val recyclerViewJobTypes: RecyclerView = view.findViewById(R.id.recyclerViewJobTypes)
        recyclerViewJobTypes.layoutManager = LinearLayoutManager(context)
        jobTypeAdapter = JobTypeAdapter(sexTypes) { selectedType ->
            selectedSexType = selectedType
            onJobSelectedListener?.invoke(selectedSexType!!)
            dismiss()
        }
        recyclerViewJobTypes.adapter = jobTypeAdapter
    }

    fun setJobData(
        sexTypes: List<String>,
        selectedJobType: String? = null,
    ) {
        this.sexTypes = sexTypes
        this.selectedSexType = selectedJobType
    }

    fun setOnJobSelectedListener(listener: (String) -> Unit) {
        this.onJobSelectedListener = listener
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        // 设置背景透明
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        // 设置从底部弹出
        dialog.window?.setWindowAnimations(com.flexible.credit.me.lib_base.R.style.DialogAnimation)

        return dialog
    }

    override fun onStart() {
        super.onStart()

        val windowManager =
            requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val screenHeight = display.height

        // 设置弹窗的最大高度为屏幕高度的一半
        val halfScreenHeight = screenHeight / 2

        // 设置弹窗的宽度为铺满屏幕，高度为屏幕的一半
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            halfScreenHeight
        )
        // 设置弹窗从底部弹出
        dialog?.window?.setGravity(Gravity.BOTTOM)
    }
}

