package com.ellfors.testdemo.biz.refresh

import android.content.Intent
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import butterknife.BindView
import com.ellfors.testdemo.R
import com.ellfors.testdemo.base.BaseActivity
import com.ellfors.testdemo.base.recyclerview.BaseRecyclerData
import com.ellfors.testdemo.util.ListUtil
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener

/**
 * RefreshActivity
 * 2018/9/3 12:13
 */
class RefreshActivity : BaseActivity()
{
    @BindView(R.id.refresh_layout)
    lateinit var mRefreshLayout: SmartRefreshLayout
    @BindView(R.id.rcv_refresh)
    lateinit var mRecyclerView: RecyclerView

    lateinit var mAdapter: RefreshAdapter
    private var list = getResetData(10)

    companion object
    {
        fun start(activity: BaseActivity)
        {
            activity.startActivity(Intent(activity, RefreshActivity::class.java))
            inOverridePendingTransition(activity)
        }
    }

    override fun getLayout(): Int
    {
        return R.layout.activity_refresh
    }

    override fun initEventAndData()
    {
        initRecyclerView()
        initRefresh()

        mRefreshLayout.autoRefresh()
    }

    private fun initRecyclerView()
    {
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = RefreshAdapter(this, ListUtil.getData(list))
        mAdapter.setOnItemClickListener { view: View, _: Int, bean: BaseRecyclerData ->
            when (view.id)
            {
                R.id.tv_refresh ->
                {
                    if (bean.data is String)
                        Toast.makeText(this@RefreshActivity, bean.data as String, Toast.LENGTH_SHORT).show()
                }
            }
        }
        mRecyclerView.adapter = mAdapter
    }

    private fun initRefresh()
    {
        //数据不满一页时取消上拉加载功能
        mRefreshLayout.setEnableLoadMoreWhenContentNotFull(false)
        //禁止自动上拉加载（需手动）
        mRefreshLayout.setEnableAutoLoadMore(false)
        //控制是否可以刷新/加载
//        mRefreshLayout.setEnableRefresh(false)
//        mRefreshLayout.setEnableLoadMore(false)
        mRefreshLayout.setOnRefreshLoadMoreListener(
                object : OnRefreshLoadMoreListener
                {
                    override fun onRefresh(refreshLayout: RefreshLayout)
                    {
                        //刷新
                        Handler().postDelayed(
                                {
                                    list = getResetData(20)
                                    mAdapter.setData(ListUtil.getData(list))
                                    mRefreshLayout.finishRefresh()
                                }, 1000)
                    }

                    override fun onLoadMore(refreshLayout: RefreshLayout)
                    {
                        //加载
                        Handler().postDelayed(
                                {
                                    mAdapter.addData(ListUtil.getData(getLoadData()))
//                                    mRefreshLayout.finishLoadMore()
                                    mRefreshLayout.finishLoadMoreWithNoMoreData()
                                }, 1000)
                    }
                })
    }

    private fun getResetData(size: Int): MutableList<String>
    {
        val data: MutableList<String> = arrayListOf()
        for (i in 1..size)
        {
            data.add("第${i}条数据")
        }
        return data
    }

    private fun getLoadData(): MutableList<String>
    {
        val data: MutableList<String> = arrayListOf()
        data.add("新增数据")
        return data
    }

}