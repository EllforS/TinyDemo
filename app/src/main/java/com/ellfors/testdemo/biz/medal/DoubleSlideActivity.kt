package com.ellfors.testdemo.biz.medal

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v4.view.ViewPager
import android.widget.RelativeLayout
import butterknife.BindView
import com.ellfors.testdemo.R
import com.ellfors.testdemo.app.MyApp
import com.ellfors.testdemo.base.BaseActivity
import com.ellfors.testdemo.biz.medal.adapter.DSVpAdapter
import com.ellfors.testdemo.event.DSTitleSelected
import com.ellfors.testdemo.util.ViewUtil

/**
 * 我的证书Demo
 * 2018/7/16 14:26
 */
class DoubleSlideActivity : BaseActivity()
{
    @BindView(R.id.rl_title)
    lateinit var rl_title: RelativeLayout
    @BindView(R.id.vp_title)
    lateinit var vp_title: ViewPager
    @BindView(R.id.vp_content)
    lateinit var vp_content: ViewPager

    var titleIsTouching: Boolean = false

    companion object
    {
        fun start(activity: BaseActivity)
        {
            activity.startActivity(Intent(activity, DoubleSlideActivity::class.java))
        }
    }

    override fun getLayout(): Int
    {
        return R.layout.activity_doubleslide
    }

    override fun initEventAndData()
    {
        initTitle()
        initContent()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initTitle()
    {
        //装载数据
        val titleAdapter = DSVpAdapter(supportFragmentManager, true)
        vp_title.adapter = titleAdapter
        vp_title.isHorizontalScrollBarEnabled = false
        vp_title.offscreenPageLimit = 5
        //将外层的点击事件传给TitleViewPager
        rl_title.setOnTouchListener { _, event -> vp_title.dispatchTouchEvent(event) }
        //监听滑动
        vp_title.addOnPageChangeListener(
                object : ViewPager.OnPageChangeListener
                {
                    override fun onPageScrollStateChanged(state: Int)
                    {

                    }

                    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int)
                    {
                        if (vp_content.currentItem != position && titleIsTouching)
                            vp_content.currentItem = position
                    }

                    override fun onPageSelected(position: Int)
                    {
                        MyApp.eventPost(DSTitleSelected(position))
                    }
                })
        //触摸监听
        vp_title.setOnTouchListener { _, _ ->
            titleIsTouching = true
            return@setOnTouchListener false
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initContent()
    {
        //装载数据
        val contentAdapter = DSVpAdapter(supportFragmentManager, false)
        vp_content.adapter = contentAdapter
        //监听滑动
        vp_content.addOnPageChangeListener(
                object : ViewPager.OnPageChangeListener
                {
                    override fun onPageScrollStateChanged(state: Int)
                    {
                    }

                    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int)
                    {
                        if (vp_title.currentItem != position && !titleIsTouching)
                            vp_title.currentItem = position
                    }

                    override fun onPageSelected(position: Int)
                    {
                    }
                })
        //触摸监听
        vp_content.setOnTouchListener { _, _ ->
            titleIsTouching = false
            return@setOnTouchListener false
        }
        //设置滑动速度
        ViewUtil.controlViewPagerSpeed(this, vp_content, 150)
    }
}