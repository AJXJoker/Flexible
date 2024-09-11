package com.flexible.credit.me.lib_base.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.flexible.credit.me.lib_base.utils.LanguageUtil
import com.flexible.credit.me.lib_base.utils.LoggerUtils
import com.flexible.credit.me.lib_base.utils.StatusBarUtil
import com.flexible.credit.me.lib_base.utils.getClass
import java.util.Locale

abstract class BaseDataBindingActivity<VM : BaseViewModel, DB : ViewDataBinding> :
    AppCompatActivity() {

    lateinit var viewModel: VM
    lateinit var mDataBinding: DB

    abstract fun getLayoutId(): Int


    abstract fun initView()

    abstract fun initData()

    abstract fun initEvent()


    override fun onCreate(savedInstanceState: Bundle?) {
        // 在super.onCreate()之前调用语言设置
        LanguageUtil.applySavedLanguage(this) // 应用保存的语言设置
        StatusBarUtil.setFullScreen(window)
        super.onCreate(savedInstanceState)
        mDataBinding = DataBindingUtil.setContentView(this, getLayoutId())

        mDataBinding.lifecycleOwner = this
        // viewModel = ViewModelProviders.of(this).get(getClass(this))
        viewModel = ViewModelProvider(this)[getClass(this)]

        lifecycle.addObserver(viewModel)
        initData()
        initView()
        initEvent()
    }


    override fun onDestroy() {
        super.onDestroy()
        if (this::viewModel.isInitialized)
            lifecycle.removeObserver(viewModel)
    }





}