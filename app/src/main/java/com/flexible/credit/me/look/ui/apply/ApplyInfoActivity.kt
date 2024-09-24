package com.flexible.credit.me.look.ui.apply

import BankSelectionDialogFragment
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.flexible.credit.me.lib_base.base.BaseDataBindingActivity
import com.flexible.credit.me.lib_base.http.AddressInfo
import com.flexible.credit.me.lib_base.http.AddressResponseData
import com.flexible.credit.me.lib_base.http.Job
import com.flexible.credit.me.lib_base.http.JobList
import com.flexible.credit.me.lib_base.http.PersonalInfo
import com.flexible.credit.me.lib_base.model.Step
import com.flexible.credit.me.lib_base.utils.LoggerUtils
import com.flexible.credit.me.lib_base.utils.StatusBarUtil
import com.flexible.credit.me.lib_base.utils.route.Route
import com.flexible.credit.me.lib_base.utils.route.RouteTable
import com.flexible.credit.me.look.R
import com.flexible.credit.me.look.adapter.ProgressAdapter
import com.flexible.credit.me.look.databinding.ActivityApplyInfoBinding
import com.flexible.credit.me.look.dialog.ChildrenCountSelectionDialogFragment
import com.flexible.credit.me.look.dialog.JobSelectionDialogFragment
import com.flexible.credit.me.look.dialog.ProvinceSelectionDialogFragment
import com.flexible.credit.me.look.dialog.SexSelectionDialogFragment
import com.flexible.credit.me.look.viewmodel.apply.ApplyInfoViewModel
import com.flexible.credit.me.look.viewmodel.login.LoginViewModel


@Route(path = RouteTable.APPLYINFO)
class ApplyInfoActivity : BaseDataBindingActivity<ApplyInfoViewModel, ActivityApplyInfoBinding>() {

    private lateinit var adapter: ProgressAdapter

    private var state: Int = 1
    private var nodeType = "NODE1"


    private lateinit var jobTypes: MutableList<String>
    private lateinit var jobPositionsMap: MutableMap<String, MutableList<String>>

    private lateinit var genderList: MutableList<String>

    private lateinit var childrenCountList: MutableList<String>

    private lateinit var maritalList: MutableList<String>
    private lateinit var educationList: MutableList<String>

    private lateinit var provinceList: MutableList<AddressResponseData>
    private lateinit var cityMap: MutableList<AddressResponseData>

    private var provinceId: String = ""
    private var provinceDialog: ProvinceSelectionDialogFragment? = null

    private var provinceAddressDialog: ProvinceSelectionDialogFragment? = null

    private var jobAddress: StringBuilder = java.lang.StringBuilder()
    private var provinceCityAddress: StringBuilder = java.lang.StringBuilder()

    private var formIdgsMz3K4 = ""

    override fun getLayoutId(): Int = R.layout.activity_apply_info

    override fun initView() {
        updateTitle("个人信息")
        StatusBarUtil.addStatusBarMargin(mDataBinding.incTop.clHeader)

        val steps = arrayListOf(
            Step("个人信息", isSelected = true),
            Step("联系人", isSelected = false),
            Step("银行卡", isSelected = false),
            Step("OCR", isSelected = false),
            Step("活体认证", isSelected = false)
        )

        val gridLayoutManager =
            GridLayoutManager(this, steps.size, GridLayoutManager.VERTICAL, false)
        mDataBinding.rvStateList.layoutManager = gridLayoutManager

        // 设置 Adapter
        adapter = ProgressAdapter(steps)
        mDataBinding.rvStateList.adapter = adapter

        initApplyInfo()
    }

    override fun initData() {
        jobTypes = mutableListOf()
        jobPositionsMap = mutableMapOf()
        provinceList = mutableListOf()
        cityMap = mutableListOf()
        genderList = mutableListOf()
        childrenCountList = mutableListOf()
        maritalList = mutableListOf()
        educationList = mutableListOf()

        initObserve()
        viewModel.getIncompleteForm(nodeType)
    }

    private fun changeIncView(state: Int) {
        // 判断 state 并更新 steps 列表
        adapter.steps.forEachIndexed { index, step ->
            if (index == (state - 1)) {
                step.isSelected = true
            }
        }

        // 更新布局的显示
        if (state == 1) {
            updateTitle("个人信息")
            mDataBinding.incApply1.root.visibility = View.VISIBLE
            mDataBinding.incApply2.root.visibility = View.GONE
            mDataBinding.incApply3.root.visibility = View.GONE
            mDataBinding.incApply4.root.visibility = View.GONE
        } else if (state == 2) {
            updateTitle("联系人")
            mDataBinding.incApply1.root.visibility = View.GONE
            mDataBinding.incApply2.root.visibility = View.VISIBLE
            mDataBinding.incApply3.root.visibility = View.GONE
            mDataBinding.incApply4.root.visibility = View.GONE
        } else if (state == 3) {
            updateTitle("收款信息")
            mDataBinding.incApply1.root.visibility = View.GONE
            mDataBinding.incApply2.root.visibility = View.GONE
            mDataBinding.incApply3.root.visibility = View.VISIBLE
            mDataBinding.incApply4.root.visibility = View.GONE
        } else if (state == 4) {
            updateTitle("证件信息")
            mDataBinding.incApply1.root.visibility = View.GONE
            mDataBinding.incApply2.root.visibility = View.GONE
            mDataBinding.incApply3.root.visibility = View.GONE
            mDataBinding.incApply4.root.visibility = View.VISIBLE
        }
        this.state = state
        // 通知 adapter 数据变化并刷新 RecyclerView
        adapter.notifyDataSetChanged()
    }

    override fun initEvent() {

        mDataBinding.tvNext.setOnClickListener {
            when (state) {
                1 -> {
                    //changeIncView(2)
                    submitUserInfo()
                }

                2 -> {
                    changeIncView(3)
                }

                3 -> {
                    changeIncView(4)
                }

                4 -> {
                }
            }
        }

        mDataBinding.incTop.ivBack.setOnClickListener { finish() }

        mDataBinding.incApply3.tvBankLabel.setOnClickListener {
            val bankSelectionDialog = BankSelectionDialogFragment().apply {
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

            bankSelectionDialog.show(supportFragmentManager, "bankSelectionDialog")

        }

        initApply1Listener()
    }


    private fun initObserve() {
        viewModel.incompleteFormData.observeForever {
            if (it != null) {
                val formsList = it.modelzzBvcDJ.formsP334IKR
                formIdgsMz3K4 =
                    formsList.firstOrNull { it.columnField == "formPerson" }?.formIdgsMz3K4.toString()
                LoggerUtils.e("获取到表单id：${formIdgsMz3K4}")
                if (!formIdgsMz3K4.isNullOrEmpty()) {
                    viewModel.getSpecifyForm(formIdgsMz3K4, nodeType)
                }
            }
        }


        viewModel.specificFormData.observeForever {
            if (it != null) {
                LoggerUtils.d("开始获取岗位信息...")
                viewModel.getJoBPositions()
                viewModel.getJobAddress(1, "")
            }
        }


        viewModel.jobListLiveData.observeForever {
            try {
                it?.let { list ->
                    list.forEachIndexed { index, job ->
                        val jobType: String = job.nameverpNre
                        val childrenr3xoueW: List<Job> = job.childrenr3xoueW
                        jobTypes.add(jobType)
                        jobPositionsMap[jobType] =
                            childrenr3xoueW.map { it.nameverpNre }.toMutableList()
                    }
                } ?: run {
                    LoggerUtils.e("jobList is null or empty")
                }
            } catch (e: Exception) {
                LoggerUtils.d("异常:${e.message}")
            }
        }

        viewModel.genderListLiveData.observeForever {
            if (it != null) {
                it.let { list ->
                    list.forEachIndexed { index, options ->
                        val gender = options.nameverpNre
                        genderList.add(gender)
                    }
                } ?: run {
                    LoggerUtils.e("jobList is null or empty")
                }
            }
        }

        viewModel.childrenCountListLiveData.observeForever {
            if (it != null) {
                it.let { list ->
                    list.forEachIndexed { _, options ->
                        val childrenCount = options.value
                        childrenCountList.add(childrenCount)
                    }
                } ?: run {
                    LoggerUtils.e("jobList is null or empty")
                }
            }
        }

        viewModel.maritalListLiveData.observeForever {
            if (it != null) {
                it.let { list ->
                    list.forEachIndexed { _, options ->
                        val nameverpNre = options.nameverpNre
                        maritalList.add(nameverpNre)
                    }
                } ?: run {
                    LoggerUtils.e("jobList is null or empty")
                }
            }
        }

        viewModel.educationListLiveData.observeForever {
            if (it != null) {
                it.let { list ->
                    list.forEachIndexed { _, options ->
                        val nameverpNre = options.nameverpNre
                        educationList.add(nameverpNre)
                    }
                } ?: run {
                    LoggerUtils.e("jobList is null or empty")
                }
            }
        }

        viewModel.provinceListLiveData.observeForever {
            try {
                it?.let { list ->
                    list.forEachIndexed { index, job ->
                        provinceList.add(job)
                    }
                } ?: run {
                    LoggerUtils.e("jobList is null or empty")
                }
            } catch (e: Exception) {
                LoggerUtils.d("异常:${e.message}")
            }
        }


        viewModel.cityListLiveData.observeForever {
            try {
                it?.let { list ->
                    list.forEachIndexed { index, job ->
                        cityMap.add(job)
                    }
                    provinceDialog?.updateCityData(cityMap)
                    provinceAddressDialog?.updateCityData(cityMap)
                } ?: run {
                    LoggerUtils.e("citylist  is null or empty")
                }
            } catch (e: Exception) {
                LoggerUtils.d("异常:${e.message}")
            }
        }
    }

    private fun initApply1Listener() {
        mDataBinding.incApply1.job.setOnClickListener {
            val jobDialog = JobSelectionDialogFragment().apply {
                setJobData(jobTypes, jobPositionsMap)
                setOnJobSelectedListener { jobType, jobPosition ->
                    // 处理选中的工作类型和岗位
                    mDataBinding.incApply1.job.text = jobPosition
                    LoggerUtils.d("选择的工作类型:${jobType} jobPosition:${jobPosition}")
                }
            }

            jobDialog.show(supportFragmentManager, "JobSelectionDialog")
        }

        mDataBinding.incApply1.workplace.setOnClickListener {
            if (provinceDialog == null) {
                provinceDialog = ProvinceSelectionDialogFragment().apply {
                    setJobData(provinceList, cityMap)
                    setOnProvinceSelectedListener(object :
                        ProvinceSelectionDialogFragment.OnProvinceSelectedListener {
                        override fun onAddressSelected(province: AddressResponseData) {
                            val haveChildIfQqq4o = province.haveChildIfQqq4o
                            jobAddress.setLength(0)
                            jobAddress.append(province.nameverpNre)
                            if (haveChildIfQqq4o) {
                                provinceId = province.idFIfKrAE
                                viewModel.getJobAddress(2, provinceId)
                            }
                        }

                        override fun onCitySelected(province: AddressResponseData) {
                            jobAddress.append(" ").append(province.nameverpNre)
                            mDataBinding.incApply1.workplace.text = jobAddress.toString()
                        }
                    })
                }
            }
            provinceDialog?.show(supportFragmentManager, "ProvinceSelectionDialog")
        }

        mDataBinding.incApply1.gender.setOnClickListener {
            val jobDialog = SexSelectionDialogFragment().apply {
                setJobData(genderList)
                setOnJobSelectedListener { gender ->
                    // 处理选中的工作类型和岗位
                    mDataBinding.incApply1.gender.text = gender
                    LoggerUtils.d("选择的性别:${gender} ")
                }
            }

            jobDialog.show(supportFragmentManager, "JobSelectionDialog")
        }

        mDataBinding.incApply1.numberChildren.setOnClickListener {
            val jobDialog = ChildrenCountSelectionDialogFragment().apply {
                setJobData(childrenCountList)
                setOnJobSelectedListener { childrenCount ->
                    // 处理选中的工作类型和岗位
                    mDataBinding.incApply1.numberChildren.text = childrenCount
                    LoggerUtils.d("选择的孩子数量:${childrenCount} ")
                }
            }

            jobDialog.show(supportFragmentManager, "JobSelectionDialog")
        }

        mDataBinding.incApply1.maritalStatus.setOnClickListener {
            val jobDialog = ChildrenCountSelectionDialogFragment().apply {
                setJobData(maritalList)
                setOnJobSelectedListener { maritalState ->
                    mDataBinding.incApply1.maritalStatus.text = maritalState
                    LoggerUtils.d("婚姻状态:${maritalState} ")
                }
            }

            jobDialog.show(supportFragmentManager, "JobSelectionDialog")
        }


        mDataBinding.incApply1.education.setOnClickListener {
            val jobDialog = ChildrenCountSelectionDialogFragment().apply {
                setJobData(educationList)
                setOnJobSelectedListener { education ->
                    mDataBinding.incApply1.education.text = education
                    LoggerUtils.d("学历:${education} ")
                }
            }

            jobDialog.show(supportFragmentManager, "JobSelectionDialog")
        }

        mDataBinding.incApply1.provinceCityDistrict.setOnClickListener {
            if (provinceAddressDialog == null) {
                provinceAddressDialog = ProvinceSelectionDialogFragment().apply {
                    setJobData(provinceList, cityMap)
                    setOnProvinceSelectedListener(object :
                        ProvinceSelectionDialogFragment.OnProvinceSelectedListener {
                        override fun onAddressSelected(province: AddressResponseData) {
                            val haveChildIfQqq4o = province.haveChildIfQqq4o
                            provinceCityAddress.setLength(0)
                            provinceCityAddress.append(province.nameverpNre)
                            if (haveChildIfQqq4o) {
                                provinceId = province.idFIfKrAE
                                viewModel.getJobAddress(2, provinceId)
                            }
                        }

                        override fun onCitySelected(province: AddressResponseData) {
                            provinceCityAddress.append(" ").append(province.nameverpNre)
                            mDataBinding.incApply1.provinceCityDistrict.text =
                                provinceCityAddress.toString()
                        }
                    })
                }
            }
            provinceAddressDialog?.show(supportFragmentManager, "ProvinceSelectionDialog")
        }
    }


    private fun submitUserInfo() {
        // 创建个人信息和地址信息实例
        val personalInfo = PersonalInfo(
            educationyG36FwB = "HIGH_SCHOOL",
            sexqMEMZU4 = "FEMALE",
            maritalohPONU0 = "DIVORCED",
            childrenCountvnKYOsG = "0",
            postalCodeZGXuNce = "03455354967",
            residencev3Biuqj = "OTHERS",
            emailnJqLXeM = "Shahzaib.butt46@gmail.com"
        )

        val addressInfo = AddressInfo(
            statecgPKZIc = "PK-3D4A305AC68921930382976E382DC0A9",
            citybqr99jo = "PK-Narowal",
            detailAddressBVKP8jM = "Muhla new abadi baddomalhi"
        )

        // 调用方法构建请求体
        val requestBody = viewModel.buildJobAddressRequestBody(
            formIdgsMz3K4 = formIdgsMz3K4,
            personalInfo = personalInfo,
            addressInfo = addressInfo
        )

        viewModel.submitUserInfoForm(requestBody)

        LoggerUtils.d("构建提交信息类:${requestBody}")
    }

    private fun updateTitle(title: String) {
        if (title.isEmpty())
            return
        mDataBinding.incTop.tvTitle.text = title
    }


    /**
     * 初始化步骤 1
     */
    private fun initApplyInfo() {
        val star = "*"
        val starColor = Color.RED
        val labelColor = ContextCompat.getColor(this, R.color.black)

        // 使用 Pair 和 List 结构化数据，方便批量处理
        val labels = listOf(
            Pair(mDataBinding.incApply1.labelJob, "职业"),
            Pair(mDataBinding.incApply1.labelWorkDuration, "工作时长"),
            Pair(mDataBinding.incApply1.labelIncome, "月收入"),
            Pair(mDataBinding.incApply1.labelCompanyName, "公司名称"),
            Pair(mDataBinding.incApply1.labelWorkplace, "工作地"),
            Pair(mDataBinding.incApply1.labelCompanyAddress, "公司详细地址"),
            Pair(mDataBinding.incApply1.labelCompanyNumber, "公司联系电话"),

            Pair(mDataBinding.incApply1.labelEducation, "最高学历"),
            Pair(mDataBinding.incApply1.labelGender, "性别"),
            Pair(mDataBinding.incApply1.labelNumberChildren, "孩子数量"),
            Pair(mDataBinding.incApply1.labelMaritalStatus, "婚姻情况"),
            Pair(mDataBinding.incApply1.labelProvinceCityDistrict, "省市区"),
            Pair(mDataBinding.incApply1.labelStreetHouseNumber, "街道和门牌号"),
            Pair(mDataBinding.incApply1.labelFacebookAccount, "Facebook账号"),
            Pair(mDataBinding.incApply1.labelLine, "Line"),
            Pair(mDataBinding.incApply1.labelMail, "邮箱"),

            Pair(mDataBinding.incApply2.tvRelationshipLabel1, "联系人关系"),
            Pair(mDataBinding.incApply2.tvPhoneLabel1, "联系人手机号"),
            Pair(mDataBinding.incApply2.tvNameLabel1, "联系人姓名"),
            Pair(mDataBinding.incApply2.tvRelationshipLabel2, "联系人关系"),
            Pair(mDataBinding.incApply2.tvPhoneLabel2, "联系人手机号"),
            Pair(mDataBinding.incApply2.tvNameLabel2, "联系人姓名"),

            Pair(mDataBinding.incApply3.tvBankLabel, "选择银行"),
            Pair(mDataBinding.incApply3.tvBankCardNumberLabel, "银行卡号码"),

            Pair(mDataBinding.incApply4.tvIdNumberNumberLabel, "证件号码"),
            Pair(mDataBinding.incApply4.tvUserNameLabel, "姓名"),
            Pair(mDataBinding.incApply4.tvUserGenderLabel, "性别"),
            Pair(mDataBinding.incApply4.tvDateBirthLabel, "出生日期"),
        )

        // 使用 forEach 循环，对每个 Pair 调用 setColoredText 函数
        labels.forEach { (textView, label) ->
            setColoredText(textView, star, label, starColor, labelColor)
        }
    }

    private fun setColoredText(
        textView: TextView,
        star: String,
        label: String,
        starColor: Int,
        labelColor: Int
    ) {
        // 创建一个 SpannableString 对象，用于设置文本样式
        val spannableString = SpannableString("$star $label")

        // 设置星号的颜色
        spannableString.setSpan(
            ForegroundColorSpan(starColor),
            0,
            star.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        // 设置标签的颜色
        spannableString.setSpan(
            ForegroundColorSpan(labelColor),
            star.length,
            spannableString.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        // 将设置好样式的文本设置给 TextView
        textView.text = spannableString
    }

}