package com.ellfors.testdemo.biz.dragphoto

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v4.view.ViewPager
import android.view.KeyEvent
import android.widget.FrameLayout
import android.widget.TextView
import butterknife.BindView
import com.ellfors.testdemo.R
import com.ellfors.testdemo.base.BaseActivity
import com.ellfors.testdemo.widget.HackyViewPager

/**
 * 图片拖拽返回
 * 2019/5/9 11:28
 */
@SuppressLint("SetTextI18n")
class DragPhotoActivity : BaseActivity()
{
    @BindView(R.id.vp_drag)
    lateinit var mViewPager: HackyViewPager
    @BindView(R.id.tv_image_pager_indicator)
    lateinit var mIndicator: TextView
    @BindView(R.id.fl_drag)
    lateinit var fl_drag: FrameLayout

    private var mAdapter: DragPhotoVpAdapter? = null
    private val imgs = arrayListOf(
            "http://k.zol-img.com.cn/sjbbs/7692/a7691515_s.jpg",
            "http://img2.100bt.com/upload/ttq/20131103/1383468354041_middle.jpg",
            "http://bbsfiles.vivo.com.cn/vivobbs/attachment/forum/201803/25/133431xr80ra2bfy0by8ao.jpg"
    )

    companion object
    {
        fun start(activity: BaseActivity)
        {
            activity.startActivity(Intent(activity, DragPhotoActivity::class.java))
            fadeInOverridePendingTransition(activity)
        }
    }

    override fun getLayout(): Int
    {
        return R.layout.activity_drag_photo
    }

    override fun initEventAndData()
    {
        mIndicator.text = "1 / ${imgs.size}"

        mAdapter = DragPhotoVpAdapter(supportFragmentManager, imgs)
        mViewPager.adapter = mAdapter
        mViewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener()
        {
            override fun onPageSelected(position: Int)
            {
                super.onPageSelected(position)
                mIndicator.text = "${position + 1} / ${imgs.size}"
            }
        })
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            finish()
            fadeInOverridePendingTransition(this)
        }
        return false
    }

}