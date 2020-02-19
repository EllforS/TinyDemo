package com.ellfors.testdemo.biz.img_progress

import android.content.Intent
import android.widget.Button
import butterknife.BindView
import com.ellfors.testdemo.R
import com.ellfors.testdemo.base.BaseActivity
import com.ellfors.testdemo.biz.img_progress.options.GlideImageView

/**
 * ImageProgressActivity
 * 2019-07-04 17:01
 */
class ImageProgressActivity : BaseActivity()
{
    @BindView(R.id.iv_progress)
    lateinit var mImageView: GlideImageView
    @BindView(R.id.btn_load_img)
    lateinit var mBtnLoad: Button

    companion object
    {
        fun start(activity: BaseActivity)
        {
            activity.startActivity(Intent(activity, ImageProgressActivity::class.java))
            inOverridePendingTransition(activity)
        }
    }

    override fun getLayout(): Int
    {
        return R.layout.activity_img_progress
    }

    override fun initEventAndData()
    {
        val url = "https://raw.githubusercontent.com/sfsheng0322/GlideImageView/master/resources/girl.jpg"

        mBtnLoad.setOnClickListener {
            mImageView.setUrl(url)
        }

    }
}