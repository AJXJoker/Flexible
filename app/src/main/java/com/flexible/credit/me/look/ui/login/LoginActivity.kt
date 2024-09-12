package com.flexible.credit.me.look.ui.login

import com.flexible.credit.me.lib_base.base.BaseDataBindingActivity
import com.flexible.credit.me.lib_base.utils.route.Route
import com.flexible.credit.me.lib_base.utils.route.RouteTable
import com.flexible.credit.me.look.R
import com.flexible.credit.me.look.databinding.ActivityLoginBinding
import com.flexible.credit.me.look.viewmodel.login.LoginViewModel

import android.content.Intent
import android.net.Uri
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import com.flexible.credit.me.lib_base.utils.route.Router


@Route(path = RouteTable.LOGIN)
class LoginActivity : BaseDataBindingActivity<LoginViewModel, ActivityLoginBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_login

    override fun initView() {
        setClickableTextWithColor(
            mDataBinding.privacyPolicy,
            "《Privacy Policy》",
            "https://example.com/privacy-policy"
        )
    }

    override fun initData() {
        viewModel.sendCodeResponse.observe(this) { response ->
            // Handle send code response
        }

        viewModel.loginResponse.observe(this) { response ->
            // Handle login response
        }

        viewModel.loading.observe(this) { isLoading ->
            // Handle loading indicator
        }

        viewModel.error.observe(this) { errorMessage ->
            // Handle error message
        }
    }

    override fun initEvent() {
        mDataBinding.loginButton.setOnClickListener {
            Router.navigate(this@LoginActivity, RouteTable.MAIN)
        }
    }


    /**
     * 设置TextView中的指定文字为红色并添加点击事件
     *
     * @param textView 需要设置的TextView
     * @param targetText 需要设置为红色并可点击的目标文字
     * @param targetUrl 点击目标文字后打开的链接地址
     */
    fun setClickableTextWithColor(textView: TextView, targetText: String, targetUrl: String) {
        val text = textView.text.toString()
        val spannableString = SpannableString(text)

        // 定位目标文字的开始和结束位置
        val start = text.indexOf(targetText)
        val end = start + targetText.length

        // 设置目标文字的点击事件
        spannableString.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                // 处理点击事件，例如打开链接
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(targetUrl))
                widget.context.startActivity(intent)
            }
        }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        // 设置目标文字的颜色为红色
        spannableString.setSpan(
            ForegroundColorSpan(resources.getColor(android.R.color.holo_red_dark)),
            start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        textView.text = spannableString
        textView.movementMethod = LinkMovementMethod.getInstance() // 使点击事件能够触发
    }
}