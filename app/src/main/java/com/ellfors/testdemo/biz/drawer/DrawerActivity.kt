package com.ellfors.testdemo.biz.drawer

import android.content.Intent
import android.graphics.Color
import android.view.Gravity
import com.ellfors.testdemo.R
import com.ellfors.testdemo.base.BaseActivity
import kotlinx.android.synthetic.main.activity_drawer.*

/**
 * DrawerActivity
 * 2019/4/2 11:22
 */
class DrawerActivity : BaseActivity()
{
    companion object
    {
        fun start(activity: BaseActivity)
        {
            activity.startActivity(Intent(activity, DrawerActivity::class.java))
            inOverridePendingTransition(activity)
        }
    }

    override fun getLayout(): Int
    {
        return R.layout.activity_drawer
    }

    override fun initEventAndData()
    {
        //设置阴影颜色
        mDrawerLayout.setScrimColor(Color.TRANSPARENT)
    }

    public fun closeDrawer()
    {
        mDrawerLayout.closeDrawer(Gravity.END)
    }
}