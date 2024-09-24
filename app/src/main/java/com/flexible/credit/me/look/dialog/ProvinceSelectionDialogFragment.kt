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
import com.flexible.credit.me.lib_base.http.AddressResponseData
import com.flexible.credit.me.lib_base.utils.LoggerUtils
import com.flexible.credit.me.look.R
import com.flexible.credit.me.look.adapter.dialog.CityAdapter
import com.flexible.credit.me.look.adapter.dialog.JobPositionAdapter
import com.flexible.credit.me.look.adapter.dialog.JobTypeAdapter
import com.flexible.credit.me.look.adapter.dialog.ProvinceAdapter

class ProvinceSelectionDialogFragment : DialogFragment() {

    private lateinit var jobTypes: List<AddressResponseData>
    private lateinit var jobPositionsMap: List<AddressResponseData>

    private var selectedJobType: AddressResponseData? = null
    private var selectedJobPosition: AddressResponseData? = null

    private lateinit var jobTypeAdapter: ProvinceAdapter
    private lateinit var jobPositionAdapter: CityAdapter
    private lateinit var recyclerViewJobPositions: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_province_city_selection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView for job types
        val recyclerViewJobTypes: RecyclerView = view.findViewById(R.id.recyclerViewJobTypes)
        recyclerViewJobTypes.layoutManager = LinearLayoutManager(context)

        jobTypeAdapter = ProvinceAdapter(jobTypes) { selectedType ->
            selectedJobType = selectedType
            LoggerUtils.d("省份选择:${selectedJobType!!.nameverpNre}")
            onAddressSelectedListener?.onAddressSelected(selectedType)
        }
        recyclerViewJobTypes.adapter = jobTypeAdapter

        recyclerViewJobPositions = view.findViewById(R.id.recyclerViewJobPositions)
        recyclerViewJobPositions.layoutManager = LinearLayoutManager(context)
        jobPositionAdapter = CityAdapter(emptyList()) { selectedPosition ->
            selectedJobPosition = selectedPosition
            selectedJobType?.let { jobType ->
               // LoggerUtils.d(":${selectedJobType!!.nameverpNre} 城市:${jobType.nameverpNre}")
                onAddressSelectedListener?.onCitySelected(jobType)
                dismiss()
            }
        }
        recyclerViewJobPositions.adapter = jobPositionAdapter
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        // 设置背景透明
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        // 设置从底部弹出
        dialog.window?.setWindowAnimations(com.flexible.credit.me.lib_base.R.style.DialogAnimation)

        return dialog
    }

    fun setJobData(
        jobTypes: List<AddressResponseData>,
        jobPositionsMap: List<AddressResponseData>,
        selectedJobType: AddressResponseData? = null,
        selectedJobPosition: AddressResponseData? = null
    ) {
        this.jobTypes = jobTypes
        this.jobPositionsMap = jobPositionsMap
        this.selectedJobType = selectedJobType
        this.selectedJobPosition = selectedJobPosition
    }

    fun updateCityData(cityPositionsMap: List<AddressResponseData>) {
        this.jobPositionsMap = cityPositionsMap

        if (cityPositionsMap.isNotEmpty()) {
            recyclerViewJobPositions.visibility = View.VISIBLE
            jobPositionAdapter.updateData(cityPositionsMap)
        } else {
            recyclerViewJobPositions.visibility = View.GONE
        }

        jobPositionAdapter.updateData(jobPositionsMap)
    }

    interface OnProvinceSelectedListener {
        fun onAddressSelected(province: AddressResponseData)
        fun onCitySelected(province: AddressResponseData)
    }

    private var onAddressSelectedListener: OnProvinceSelectedListener? = null


    fun setOnProvinceSelectedListener(listener: OnProvinceSelectedListener) {
        this.onAddressSelectedListener = listener
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

