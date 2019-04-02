package com.ellfors.testdemo.biz.navigation

import android.content.Intent
import butterknife.OnClick
import com.ellfors.testdemo.R
import com.ellfors.testdemo.base.BaseActivity
import com.ellfors.testdemo.util.NavigationUtil

/**
 * 调起导航
 * 2019/3/28 10:21
 */
class NavigationAcitvity : BaseActivity()
{
    companion object
    {
        fun start(activity: BaseActivity)
        {
            activity.startActivity(Intent(activity, NavigationAcitvity::class.java))
            inOverridePendingTransition(activity)
        }
    }

    override fun getLayout(): Int
    {
        return R.layout.activity_navigation
    }

    override fun initEventAndData()
    {
    }

    @OnClick(R.id.btn_navigation)
    fun onClick()
    {
        NavigationUtil.showMapsList(this, 114.107394, 22.540911, "大剧院E出口")
    }
}