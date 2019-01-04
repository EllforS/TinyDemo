package com.ellfors.testdemo.biz.swipeback

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.ellfors.testdemo.R
import com.ellfors.testdemo.base.BaseActivity
import com.ellfors.testdemo.base.BaseActivity.inOverridePendingTransition
import com.ellfors.testdemo.util.DensityUtil
import me.imid.swipebacklayout.lib.SwipeBackLayout
import me.imid.swipebacklayout.lib.app.SwipeBackActivity

/**
 * 测试侧滑返回
 * 2019/1/4 11:49
 */
class TestSwipeActivity : SwipeBackActivity()
{
    companion object
    {
        fun start(activity: BaseActivity)
        {
            activity.startActivity(Intent(activity, TestSwipeActivity::class.java))
            inOverridePendingTransition(activity)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_swipe)

        init()
    }

    private fun init()
    {
        //侧滑返回模式
        //SwipeBackLayout.EDGE_LEFT   右滑返回(默认)
        //SwipeBackLayout.EDGE_RIGHT  左滑返回
        //SwipeBackLayout.EDGE_BOTTOM 上滑返回
        //SwipeBackLayout.EDGE_ALL    全部
        swipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT)
        //设置触发边缘的大小 dp
        swipeBackLayout.setEdgeSize(DensityUtil.dp2px(this@TestSwipeActivity, 100F))
        //设置滚动阀值   0.0~1.0之间
        swipeBackLayout.setScrollThresHold(0.5f)
        //设置是否可以侧滑返回  默认true
        swipeBackLayout.setEnableGesture(true)
        //滑动监听
        swipeBackLayout.addSwipeListener(
                object : SwipeBackLayout.SwipeListener
                {
                    override fun onScrollStateChange(state: Int, scrollPercent: Float)
                    {
                        //state  1：拖动   2：回弹
                        Log.d("AAA", "状态改变___状态${state}___滚动百分比${String.format("%.2f", scrollPercent * 100)}")
                    }

                    override fun onEdgeTouch(edgeFlag: Int)
                    {
                        Log.i("AAA", "触摸___$edgeFlag")
                    }

                    override fun onScrollOverThreshold()
                    {
                        //当达到最大滑动距离可以侧滑返回时，触发这个方法
                        Log.e("AAA", "滚动阙值")
                    }
                })
        //测试bean对象数值监听
        val bean = TestBean()
        bean.userName = "111"
        bean.userName = "222"
        bean.userName = "666"
        Log.d("AAA", "userName === ${bean.userName}")
    }

}