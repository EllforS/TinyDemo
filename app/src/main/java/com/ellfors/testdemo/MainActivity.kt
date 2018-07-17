package com.ellfors.testdemo

import android.support.v7.widget.LinearLayoutManager
import com.ellfors.testdemo.base.BaseActivity
import com.ellfors.testdemo.base.recyclerview.BaseRecyclerData
import com.ellfors.testdemo.biz.certificate.DoubleSlideActivity
import com.ellfors.testdemo.model.MainItemBean
import com.ellfors.testdemo.util.ViewUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity()
{
    override fun getLayout(): Int
    {
        return R.layout.activity_main
    }

    override fun initEventAndData()
    {
        ViewUtil.setDensityWH(this)
        initRecyclerView()
    }

    private fun initRecyclerView()
    {
        rcv_main.layoutManager = LinearLayoutManager(this)
        val mAdapter = MainAdapter(this, getData())
        rcv_main.adapter = mAdapter
        mAdapter.setOnItemClickListener { _, _, bean ->
            if (bean.data !is MainItemBean)
                return@setOnItemClickListener
            when ((bean.data as MainItemBean).id)
            {
                MainItemBean.ID.DOUBLE_SLIDE -> DoubleSlideActivity.start(this@MainActivity)
            }
        }
    }

    private fun getData(): MutableList<BaseRecyclerData>
    {
        val list: MutableList<BaseRecyclerData> = mutableListOf()
        list.add(BaseRecyclerData(MainItemBean(MainItemBean.ID.DOUBLE_SLIDE, "我的证书Demo")))
        return list
    }

}
