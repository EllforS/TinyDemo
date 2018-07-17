package com.ellfors.testdemo.biz.certificate

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import com.ellfors.testdemo.R
import com.ellfors.testdemo.app.MyApp
import com.ellfors.testdemo.base.BaseFragment
import com.ellfors.testdemo.event.DSTitleSelected
import kotlinx.android.synthetic.main.fragment_ds_title.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class DSTitleFragment : BaseFragment()
{
    private var pos = 0

    override fun getLayout(): Int
    {
        return R.layout.fragment_ds_title
    }

    @SuppressLint("SetTextI18n")
    override fun initEventAndData()
    {
        MyApp.eventRegister(this@DSTitleFragment)

        pos = arguments!!.getInt("pos")
        tv_title.text = "标题${pos + 1}"

        selectPos(0)
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        MyApp.eventUnRegister(this@DSTitleFragment)
    }

    private fun selectPos(position: Int)
    {
        val param = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        if (pos == position)
        {
            tv_title.textSize = 15f
            tv_title.typeface = Typeface.DEFAULT_BOLD
            param.setMargins(0, 45, 0, 0)
        }
        else
        {
            tv_title.textSize = 11f
            tv_title.typeface = Typeface.DEFAULT
            param.setMargins(0, 10, 0, 0)
        }
        tv_title.layoutParams = param
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onTitleSelected(bean: DSTitleSelected?)
    {
        if (bean == null)
            return
        selectPos(bean.position)
    }

}