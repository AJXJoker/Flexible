package com.flexible.credit.me.look.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flexible.credit.me.look.R
import com.flexible.credit.me.look.adapter.dialog.JobPositionAdapter
import com.flexible.credit.me.look.adapter.dialog.JobTypeAdapter

class JobSelectionDialogFragment : DialogFragment() {

    private lateinit var jobTypes: List<String>
    private lateinit var jobPositionsMap: Map<String, List<String>>
    private var selectedJobType: String? = null
    private var selectedJobPosition: String? = null
    private var onJobSelectedListener: ((String, String) -> Unit)? = null

    private lateinit var jobTypeAdapter: JobTypeAdapter
    private lateinit var jobPositionAdapter: JobPositionAdapter
    private lateinit var recyclerViewJobPositions: RecyclerView

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
        jobTypeAdapter = JobTypeAdapter(jobTypes) { selectedType ->
            selectedJobType = selectedType
            updateJobPositions(selectedType)
        }
        recyclerViewJobTypes.adapter = jobTypeAdapter

        // RecyclerView for job positions
        recyclerViewJobPositions = view.findViewById(R.id.recyclerViewJobPositions)
        recyclerViewJobPositions.layoutManager = LinearLayoutManager(context)
        jobPositionAdapter = JobPositionAdapter(emptyList()) { selectedPosition ->
            selectedJobPosition = selectedPosition
            selectedJobType?.let { jobType ->
                onJobSelectedListener?.invoke(jobType, selectedPosition)
                dismiss()
            }
        }
        recyclerViewJobPositions.adapter = jobPositionAdapter
    }

    fun setJobData(
        jobTypes: List<String>,
        jobPositionsMap: Map<String, List<String>>,
        selectedJobType: String? = null,
        selectedJobPosition: String? = null
    ) {
        this.jobTypes = jobTypes
        this.jobPositionsMap = jobPositionsMap
        this.selectedJobType = selectedJobType
        this.selectedJobPosition = selectedJobPosition
    }

    fun setOnJobSelectedListener(listener: (String, String) -> Unit) {
        this.onJobSelectedListener = listener
    }

    private fun updateJobPositions(jobType: String) {
        // 更新右侧工作岗位列表数据
        val positions = jobPositionsMap[jobType] ?: emptyList()

        // 如果选中了一个工作类型，显示岗位列表
        if (positions.isNotEmpty()) {
            recyclerViewJobPositions.visibility = View.VISIBLE
            jobPositionAdapter.updateData(positions)
        } else {
            recyclerViewJobPositions.visibility = View.GONE
        }
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

        // 设置弹窗的宽度铺满屏幕
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        // 设置弹窗从底部弹出
        dialog?.window?.setGravity(Gravity.BOTTOM)
    }
}

