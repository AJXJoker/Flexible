package com.flexible.credit.me.look.ui.home

import BankSelectionDialogFragment
import android.view.View
import com.flexible.credit.me.lib_base.base.BaseDataBindingFragment
import com.flexible.credit.me.lib_base.utils.LoggerUtils
import com.flexible.credit.me.lib_base.utils.StatusBarUtil
import com.flexible.credit.me.lib_base.utils.route.RouteTable
import com.flexible.credit.me.lib_base.utils.route.Router
import com.flexible.credit.me.look.R
import com.flexible.credit.me.look.databinding.FragmentHomeBinding
import com.flexible.credit.me.look.viewmodel.home.HomeViewModel

class HomeFragment : BaseDataBindingFragment<HomeViewModel, FragmentHomeBinding>() {


    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun initView(view: View) {
        // 初始化视图
        StatusBarUtil.addStatusBarMargin(mDataBinding.clHeader)
        LoggerUtils.d("首頁初始化视图")
    }

    override fun loadData() {
        // 加载数据，仅在懒加载条件满足时调用
        LoggerUtils.d("首頁加载数据，仅在懒加载条件满足时调用")
    }

    override fun initEvent() {
        // 初始化事件

        mDataBinding.tvApply.setOnClickListener {
            /*val bankSelectionDialog = BankSelectionDialogFragment().apply {
                setBankOptions(
                    listOf(
                        "contables",
                        "personal directivo",
                        "ventas",
                        "directores",
                        "administración"
                    ), "personal directivo"
                )
                setOnBankSelectedListener { selectedBank ->
                    // 处理选择结果
                    LoggerUtils.d("Selected Bank: $selectedBank")
                }
            }

            bankSelectionDialog.show(requireActivity().supportFragmentManager, "bankSelectionDialog")*/
            Router.navigate(requireActivity(), RouteTable.APPLYINFO)
        }
    }

}