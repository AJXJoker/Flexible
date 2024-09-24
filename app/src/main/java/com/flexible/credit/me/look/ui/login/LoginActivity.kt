package com.flexible.credit.me.look.ui.login

import com.flexible.credit.me.lib_base.base.BaseDataBindingActivity
import com.flexible.credit.me.lib_base.utils.route.Route
import com.flexible.credit.me.lib_base.utils.route.RouteTable
import com.flexible.credit.me.look.R
import com.flexible.credit.me.look.databinding.ActivityLoginBinding
import com.flexible.credit.me.look.viewmodel.login.LoginViewModel

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.CountDownTimer
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.flexible.credit.me.lib_base.utils.ToastUtils
import com.flexible.credit.me.lib_base.utils.route.Router


@Route(path = RouteTable.LOGIN)
class LoginActivity : BaseDataBindingActivity<LoginViewModel, ActivityLoginBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_login

    private var countDownTimer: CountDownTimer? = null
    private val countDownDuration: Long = 60000 // 60秒
    private val countDownInterval: Long = 1000 // 1秒

    override fun initView() {
        setClickableTextWithColor(
            mDataBinding.privacyPolicy,
            "《Privacy Policy》",
            "https://example.com/privacy-policy"
        )
    }

    override fun initData() {

        viewModel.loading.observe(this) {
            // Handle loading indicator
        }

        viewModel.isLoginStatus.observe(this) {
            // Handle loading indicator
            if (it){
                Router.navigate(this@LoginActivity, RouteTable.MAIN)
            }
        }

        viewModel.sendCode.observe(this) {
            if (it == 0) {
                mDataBinding.clGetPhoneCode.visibility = View.VISIBLE
                mDataBinding.clInputPhone.visibility = View.INVISIBLE
                startCountDown()
            } else {
                ToastUtils.showToast(baseContext, "获取短信失败")
            }
        }

    }

    override fun initEvent() {
        mDataBinding.loginButton.setOnClickListener {
            val mobile = mDataBinding.edtPhoneNumberInput.text.toString()
            val phoneCode = "+86"
            val verificationCode = mDataBinding.edtPhoneNumberCodeInput.text.toString()

            if (mDataBinding.clInputPhone.visibility == View.VISIBLE) {
                val phone = mDataBinding.edtPhoneNumberInput.text.toString()
                if (phone.isNotEmpty()) {

                    viewModel.sendCode(mobile, phoneCode)

                } else {
                    Toast.makeText(this@LoginActivity, "请输入手机号", Toast.LENGTH_SHORT).show()
                }
            } else if (mDataBinding.clGetPhoneCode.visibility == View.VISIBLE) {
                val code = mDataBinding.edtPhoneNumberCodeInput.text.toString()
                if (code.isNullOrEmpty()) {
                    ToastUtils.showToast(baseContext, "请输入验证码")
                    return@setOnClickListener
                }
                viewModel.startLogin(mobile, phoneCode, verificationCode)
                //Router.navigate(this@LoginActivity, RouteTable.MAIN)
            }
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
            ForegroundColorSpan(Color.parseColor("#014AB1")),
            start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        textView.text = spannableString
        textView.movementMethod = LinkMovementMethod.getInstance() // 使点击事件能够触发
    }


    private fun startCountDown() {
        countDownTimer = object : CountDownTimer(countDownDuration, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = (millisUntilFinished / 1000).toInt()
                mDataBinding.tvPhoneCode.text = "$secondsRemaining"
            }

            override fun onFinish() {
                mDataBinding.tvPhoneCode.text = "重新获取"
            }
        }.start()
    }


    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel() // 释放计时器
    }

}