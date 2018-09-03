package com.ellfors.testdemo.biz.refresh

import android.content.Intent
import com.ellfors.testdemo.base.BaseActivity

/**
 * RefreshActivity
 * 2018/9/3 12:13
 */
class RefreshActivity : BaseActivity()
{

    companion object
    {
        fun start(activity: BaseActivity)
        {
            activity.startActivity(Intent(activity, RefreshActivity::class.java))
            inOverridePendingTransition(activity)
        }
    }

    override fun getLayout(): Int
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initEventAndData()
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}