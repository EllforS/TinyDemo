package com.ellfors.testdemo.biz.touchlist

import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.widget.TextView
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
    @BindView(R.id.tv_touch_value)
    lateinit var mTextView: TextView

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

        val mListener = MoveItemTouchHelper()
        val itemTouchHelper = ItemTouchHelper(mListener)
        itemTouchHelper.attachToRecyclerView(mRecyclerView)

        mListener.setIdleColor(Color.RED)
        mListener.setDefaultColor(Color.WHITE)
        mListener.setItemTouchMoveListener(object : MoveItemTouchHelper.ItemTouchMoveListener
        {
            override fun onItemMove(fromPosition: Int, toPosition: Int)
            {
                //移动模块
                mAdapter?.notifyItemMoved(fromPosition, toPosition)
            }

            override fun onItemMoveComplete(fromPosition: Int, toPosition: Int)
            {
                //重排数据
                list.add(toPosition, list.removeAt(fromPosition))
            }
        })
    }

    @OnClick(R.id.btn_toast)
    fun onClick()
    {
        var value = ""
        for (i in 0 until list.size)
        {
            value += "${list[i].data as String}_"
        }
        mTextView.text = value
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