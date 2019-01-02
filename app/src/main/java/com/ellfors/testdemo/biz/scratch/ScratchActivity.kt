package com.ellfors.testdemo.biz.scratch

import android.content.Intent
import android.view.View
import com.clock.scratch.ScratchView
import com.ellfors.testdemo.R
import com.ellfors.testdemo.base.BaseActivity
import kotlinx.android.synthetic.main.activity_scratch.*

/**
 * 刮奖
 * https://github.com/D-clock/ScratchView
 * 2018/12/26 14:42
 */
class ScratchActivity : BaseActivity()
{
    companion object
    {
        fun start(activity: BaseActivity)
        {
            activity.startActivity(Intent(activity, ScratchActivity::class.java))
            inOverridePendingTransition(activity)
        }
    }

    override fun getLayout(): Int
    {
        return R.layout.activity_scratch
    }

    override fun initEventAndData()
    {
        sv_scratch.setEraserSize(100F)
        sv_scratch.setMaskColor(resources.getColor(R.color.color_ff6633))
        sv_scratch.setMaxPercent(40)
        sv_scratch.setWatermark(R.drawable.logo_small)

        sv_scratch.setEraseStatusListener(
                object : ScratchView.EraseStatusListener
                {
                    override fun onCompleted(view: View?)
                    {
                        sv_scratch.clear()
                    }

                    override fun onProgress(percent: Int)
                    {

                    }
                })
    }
}