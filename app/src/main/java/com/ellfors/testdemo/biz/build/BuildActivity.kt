package com.ellfors.testdemo.biz.build

import android.content.Intent
import com.ellfors.testdemo.R
import com.ellfors.testdemo.base.BaseActivity
import kotlinx.android.synthetic.main.activity_background.*

/**
 * 测试建造者模式
 * 2018/12/25 16:59
 */
class BuildActivity : BaseActivity()
{
    companion object
    {
        fun start(activity: BaseActivity)
        {
            activity.startActivity(Intent(activity, BuildActivity::class.java))
            inOverridePendingTransition(activity)
        }
    }

    override fun getLayout(): Int
    {
        return R.layout.activity_background
    }

    override fun initEventAndData()
    {
        /**
         * BackgroundLibrary可以在XML标签中直接生产shape文件效果
         * （不好用………………）
         */
        tv_bg_test_1.setOnClickListener {
            TestBuild.with(this@BuildActivity)
                    .setValue_1("aaaaa")
                    .setValue_2(333)
                    .setValue_3(true)
                    .toast()
        }
    }
}