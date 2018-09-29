package com.ellfors.testdemo

import android.support.v7.widget.LinearLayoutManager
import com.ellfors.testdemo.base.BaseActivity
import com.ellfors.testdemo.base.recyclerview.BaseRecyclerData
import com.ellfors.testdemo.biz.medal.DoubleSlideActivity
import com.ellfors.testdemo.biz.refresh.RefreshActivity
import com.ellfors.testdemo.biz.right_grid.RightGridActivity
import com.ellfors.testdemo.biz.statusbar.StatusBarActivity
import com.ellfors.testdemo.biz.tinker.TinkerActivity
import com.ellfors.testdemo.model.MainItemBean
import com.ellfors.testdemo.permission.PermissionManager
import com.ellfors.testdemo.util.ViewUtil
import com.yanzhenjie.permission.Permission
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity()
{
    private val permissions = arrayOf(Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE, Permission.READ_PHONE_STATE, Permission.CALL_PHONE, Permission.CAMERA, Permission.ACCESS_COARSE_LOCATION, Permission.ACCESS_FINE_LOCATION)

    override fun getLayout(): Int
    {
        return R.layout.activity_main
    }

    override fun initEventAndData()
    {
        ViewUtil.setDensityWH(this)
        initRecyclerView()

        PermissionManager(this@MainActivity)
                .addRationale()
                .addPermission(permissions)
                .addListener(null)
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
                MainItemBean.ID.RIGHT_GRID -> RightGridActivity.start(this@MainActivity)
                MainItemBean.ID.REFRESH -> RefreshActivity.start(this@MainActivity)
                MainItemBean.ID.STATUS_BAR -> StatusBarActivity.start(this@MainActivity)
                MainItemBean.ID.TINKER -> TinkerActivity.start(this@MainActivity)
            }
        }
    }

    private fun getData(): MutableList<BaseRecyclerData>
    {
        val list: MutableList<BaseRecyclerData> = mutableListOf()
        list.add(BaseRecyclerData(MainItemBean(MainItemBean.ID.DOUBLE_SLIDE, "我的证书Demo")))
        list.add(BaseRecyclerData(MainItemBean(MainItemBean.ID.RIGHT_GRID, "从右至左GridLayoutManager")))
        list.add(BaseRecyclerData(MainItemBean(MainItemBean.ID.REFRESH, "刷新")))
        list.add(BaseRecyclerData(MainItemBean(MainItemBean.ID.STATUS_BAR, "状态栏")))
        list.add(BaseRecyclerData(MainItemBean(MainItemBean.ID.TINKER, "Tinker热修复")))
        return list
    }

}
