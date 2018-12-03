package com.ellfors.testdemo.biz.statusbar

import android.content.Intent
import com.ellfors.testdemo.R
import com.ellfors.testdemo.base.BaseActivity

/**
 * StatusBarActivity
 * 2018/9/19 11:42
 */
class StatusBarActivity : BaseActivity()
{
    companion object
    {
        fun start(activity: BaseActivity)
        {
            activity.startActivity(Intent(activity, StatusBarActivity::class.java))
            inOverridePendingTransition(activity)
        }
    }

    override fun getLayout(): Int
    {
        return R.layout.activity_statusbar
    }

    override fun initEventAndData()
    {
        showStatusBar(false)
        setStatusBarDarkFont(false)
    }

}