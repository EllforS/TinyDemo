package com.ellfors.testdemo.biz.right_grid

import android.content.Intent
import android.support.v7.widget.RecyclerView
import butterknife.BindView
import com.ellfors.testdemo.R
import com.ellfors.testdemo.base.BaseActivity
import com.ellfors.testdemo.util.ListUtil
import com.ellfors.testdemo.widget.RtlGridLayoutManager

/**
 * 从右至左GridLayoutManager
 * 2018/7/25 19:26
 */
class RightGridActivity : BaseActivity()
{
    @BindView(R.id.rcv_right_grid)
    lateinit var mRecyclerView: RecyclerView

    lateinit var mAdapter: RightGridAdapter
    private val list = listOf("1", "2", "3", "4", "5")

    companion object
    {
        fun start(activity: BaseActivity)
        {
            activity.startActivity(Intent(activity, RightGridActivity::class.java))
        }
    }

    override fun getLayout(): Int
    {
        return R.layout.activity_right_grid
    }

    override fun initEventAndData()
    {
        mRecyclerView.layoutManager = RtlGridLayoutManager(this, 3)
        mAdapter = RightGridAdapter(this, ListUtil.getData(list))
        mRecyclerView.adapter = mAdapter
    }
}