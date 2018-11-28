package com.ellfors.testdemo.biz.blur

import android.content.Intent
import android.graphics.BitmapFactory
import android.widget.ImageView
import butterknife.BindView
import com.ellfors.testdemo.R
import com.ellfors.testdemo.base.BaseActivity

/**
 * 高斯模糊
 * 2018/11/26 16:57
 */
class BlurActivity : BaseActivity()
{
    @BindView(R.id.iv_blur_1)
    lateinit var img_1: ImageView

    companion object
    {
        fun start(activity: BaseActivity)
        {
            activity.startActivity(Intent(activity, BlurActivity::class.java))
            inOverridePendingTransition(activity)
        }
    }

    override fun getLayout(): Int
    {
        return R.layout.activity_blur
    }

    override fun initEventAndData()
    {
        showStatusBar(false)
        BitmapBlurUtil.getInstance().setImageBlurBitmap(this@BlurActivity, img_1, BitmapFactory.decodeResource(resources, R.drawable.test_img_1))
    }

}