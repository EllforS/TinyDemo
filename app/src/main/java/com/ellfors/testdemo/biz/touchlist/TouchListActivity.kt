package com.ellfors.testdemo.biz.touchlist

import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import butterknife.BindView
import butterknife.OnClick
import com.ellfors.testdemo.R
import com.ellfors.testdemo.base.BaseActivity
import com.ellfors.testdemo.base.recyclerview.BaseRecyclerData

/**
 * RecyclerView拖拽移动
 * 2019/4/22 12:14
 */
class TouchListActivity : BaseActivity()
{
    @BindView(R.id.rcv_touch)
    lateinit var mRecyclerView: RecyclerView

    private var mAdapter: TouchListAdapter? = null
    val list = mutableListOf<BaseRecyclerData>()

    companion object
    {
        fun start(activity: BaseActivity)
        {
            activity.startActivity(Intent(activity, TouchListActivity::class.java))
            inOverridePendingTransition(activity)
        }
    }

    override fun getLayout(): Int
    {
        return R.layout.activity_touch_list
    }

    override fun initEventAndData()
    {
        mRecyclerView.layoutManager = GridLayoutManager(this, 3)
//        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = TouchListAdapter(this, getData())
        mRecyclerView.adapter = mAdapter

        val mListener = MyItemTouchHelper(object : MyItemTouchHelper.ItemTouchMoveListener
        {
            override fun onItemMove(fromPosition: Int, toPosition: Int)
            {
                //移动模块
                mAdapter?.notifyItemMoved(fromPosition, toPosition)
            }

            override fun onItemMoveComplete(fromPosition: Int, toPosition: Int)
            {
                Log.d("AAA", "from = $fromPosition，to = $toPosition")
                //重排数据
                list.add(toPosition, list.removeAt(fromPosition))
            }
        })
        val itemTouchHelper = ItemTouchHelper(mListener)
        itemTouchHelper.attachToRecyclerView(mRecyclerView)
    }

    @OnClick(R.id.btn_toast)
    fun onClick()
    {
        for (i in 0 until list.size)
        {
            Log.d("AAA", "value = ${list[i].data as String}")
        }
    }

    private fun getData(): MutableList<BaseRecyclerData>
    {
        for (i in 1..10)
        {
            list.add(BaseRecyclerData("$i"))
        }
        return list
    }

}