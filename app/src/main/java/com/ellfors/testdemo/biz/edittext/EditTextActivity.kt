package com.ellfors.testdemo.biz.edittext

import android.content.Intent
import com.ellfors.testdemo.R
import com.ellfors.testdemo.base.BaseActivity
import com.ellfors.testdemo.widget.SeparatedEditText
import kotlinx.android.synthetic.main.activity_edittext.*

/**
 * 输入框
 * 2018/11/20 20:06
 *
 * github:
 * https://github.com/WGwangguan/SeparatedEditText
 */
class EditTextActivity : BaseActivity()
{
    companion object
    {
        fun start(activity: BaseActivity)
        {
            activity.startActivity(Intent(activity, EditTextActivity::class.java))
            inOverridePendingTransition(activity)
        }
    }

    override fun getLayout(): Int
    {
        return R.layout.activity_edittext
    }

    override fun initEventAndData()
    {
        et_solid.setBorderWidth(10)

        et_solid.setTextChangedListener(
                object : SeparatedEditText.TextChangedListener
                {
                    override fun textCompleted(text: CharSequence?)
                    {
                        showToast(text.toString())
                    }

                    override fun textChanged(changeText: CharSequence?)
                    {
                    }
                })

        btn_edit.setOnClickListener {
            showToast(et_solid.text.toString().trim())
        }
    }

}