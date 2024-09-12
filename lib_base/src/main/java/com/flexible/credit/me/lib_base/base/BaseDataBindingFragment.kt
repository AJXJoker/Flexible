package com.flexible.credit.me.lib_base.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import androidx.fragment.app.Fragment
import com.flexible.credit.me.lib_base.utils.StatusBarUtil
import com.flexible.credit.me.lib_base.utils.getClass

abstract class BaseDataBindingFragment<VM : BaseViewModel, DB : ViewDataBinding> : Fragment() {

    lateinit var viewModel: VM
    lateinit var mDataBinding: DB

    private var isViewInitiated = false
    private var isVisibleToUser = false
    private var isDataLoaded = false

    abstract fun getLayoutId(): Int

    abstract fun initView(view: View)

    abstract fun loadData()

    abstract fun initEvent()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        mDataBinding.lifecycleOwner = this

        viewModel = ViewModelProvider(this)[getClass(this)]

        lifecycle.addObserver(viewModel)  // 将ViewModel与Fragment的生命周期关联

        return mDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        StatusBarUtil.setFullScreen(requireActivity().window)
        isViewInitiated = true
        initView(view)
        initEvent()
        lazyLoad()

        // 观察 ViewModel 的一些基础 LiveData，方便扩展（如loading、错误处理等）
        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { errorMsg ->
        })
    }

    private fun lazyLoad() {
        if (isViewInitiated && isVisibleToUser && !isDataLoaded) {
            loadData() // 仅在视图初始化且对用户可见时加载数据
            isDataLoaded = true // 确保数据只加载一次
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        lazyLoad() // 每当可见状态改变时尝试加载数据
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (this::viewModel.isInitialized)
            lifecycle.removeObserver(viewModel)
        isViewInitiated = false // 视图销毁时重置标志
        isDataLoaded = false // 重置数据加载状态
    }
}
