package com.flexible.credit.me.look

import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.flexible.credit.me.lib_base.base.BaseDataBindingActivity
import com.flexible.credit.me.lib_base.utils.LoggerUtils
import com.flexible.credit.me.lib_base.utils.route.Route
import com.flexible.credit.me.lib_base.utils.route.RouteTable
import com.flexible.credit.me.look.adapter.FragmentAdapter
import com.flexible.credit.me.look.databinding.ActivityMainBinding
import com.flexible.credit.me.look.ui.home.HomeFragment
import com.flexible.credit.me.look.ui.home.OrderFragment
import com.flexible.credit.me.look.ui.home.UserFragment
import com.flexible.credit.me.look.viewmodel.home.MainViewModel

@Route(path = RouteTable.MAIN)
class MainActivity : BaseDataBindingActivity<MainViewModel, ActivityMainBinding>() {

    private lateinit var fragments: List<Fragment>

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView() {
        fragments = listOf(
            HomeFragment(),
            OrderFragment(),
            UserFragment()
        )

        // 使用 FragmentAdapter 设置到 ViewPager2
        mDataBinding.viewPager.adapter = FragmentAdapter(this, fragments)

        // 设置 offscreenPageLimit
        mDataBinding.viewPager.offscreenPageLimit = fragments.size
        mDataBinding.viewPager.isUserInputEnabled = false
        // 处理 RadioGroup 和 ViewPager2 的联动
        mDataBinding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio_home -> mDataBinding.viewPager.setCurrentItem(0, false)
                R.id.radio_order -> mDataBinding.viewPager.setCurrentItem(1, false)
                R.id.radio_user -> mDataBinding.viewPager.setCurrentItem(2, false)
            }
        }

        // 处理 ViewPager2 滑动时 RadioGroup 的联动
        mDataBinding.viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> mDataBinding.radioGroup.check(R.id.radio_home)
                    1 -> {
                        LoggerUtils.d("获取数据...")
                        val fragment: OrderFragment = fragments[1] as OrderFragment
                        fragment.loadData()
                        mDataBinding.radioGroup.check(R.id.radio_order)
                    }

                    2 -> mDataBinding.radioGroup.check(R.id.radio_user)
                }
            }
        })
    }

    override fun initData() {
        // 初始化数据
    }

    override fun initEvent() {
        // 初始化事件
    }
}
