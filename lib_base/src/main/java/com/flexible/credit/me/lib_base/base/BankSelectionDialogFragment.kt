import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flexible.credit.me.lib_base.R
import com.flexible.credit.me.lib_base.adapter.BankSelectionAdapter
import com.flexible.credit.me.lib_base.utils.StatusBarUtil

class BankSelectionDialogFragment : DialogFragment() {

    private var selectedBank: String? = null
    private lateinit var bankOptions: List<String>
    private var onBankSelectedListener: ((String) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_bank_selection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        StatusBarUtil.setFullScreen(requireActivity().window)
        // 设置RecyclerView
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewBanks)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // 创建并设置适配器
        val adapter =
            BankSelectionAdapter(requireContext(), bankOptions, selectedBank) { selected ->
                selectedBank = selected
                selectedBank?.let { bank ->
                    onBankSelectedListener?.invoke(bank)
                    dismiss()
                }
            }
        recyclerView.adapter = adapter

        /* // 设置确认按钮点击事件
         view.findViewById<View>(R.id.btnConfirm).setOnClickListener {
             selectedBank?.let { bank ->
                 onBankSelectedListener?.invoke(bank)
                 dismiss()
             }
         }

         // 设置取消按钮点击事件
         view.findViewById<View>(R.id.btnCancel).setOnClickListener {
             dismiss()
         }*/
    }

    fun setBankOptions(options: List<String>, selected: String? = null) {
        this.bankOptions = options
        this.selectedBank = selected
    }

    fun setOnBankSelectedListener(listener: (String) -> Unit) {
        this.onBankSelectedListener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        // 设置背景透明
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        // 设置从底部弹出
        dialog.window?.setWindowAnimations(R.style.DialogAnimation)

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
