package com.ellfors.testdemo.biz.statusbar

import android.content.Intent
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
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

        val img = findViewById<ImageView>(R.id.iv_test)

        Glide.with(this)
                .load("https://res.kxdzc.com/ads/2018121801.jpg")
                .apply(RequestOptions.bitmapTransform(RoundedCorners(100)))
                .into(img)

    }

}