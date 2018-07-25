package com.ellfors.testdemo.biz.medal

import android.annotation.SuppressLint
import com.ellfors.testdemo.R
import com.ellfors.testdemo.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_ds_content.*

class DSContentFragment : BaseFragment()
{
    override fun getLayout(): Int
    {
        return R.layout.fragment_ds_content
    }

    @SuppressLint("SetTextI18n")
    override fun initEventAndData()
    {
        val pos = arguments!!.getInt("pos")
        tv_content.text = "这里是内容_${pos + 1}"
    }
}