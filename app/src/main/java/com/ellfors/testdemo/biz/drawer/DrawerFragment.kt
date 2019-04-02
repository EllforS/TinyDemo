package com.ellfors.testdemo.biz.drawer

import com.ellfors.testdemo.R
import com.ellfors.testdemo.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_drawer.*

/**
 * DrawerFragment
 * 2019/4/2 11:22
 */
class DrawerFragment : BaseFragment()
{
    override fun getLayout(): Int
    {
        return R.layout.fragment_drawer
    }

    override fun initEventAndData()
    {
        tv_close.setOnClickListener {
            if (activity is DrawerActivity)
                (activity as DrawerActivity).closeDrawer()
        }
    }
}