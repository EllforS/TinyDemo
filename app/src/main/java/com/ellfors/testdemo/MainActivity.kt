package com.ellfors.testdemo

import android.support.v7.widget.LinearLayoutManager
import com.ellfors.testdemo.base.BaseActivity
import com.ellfors.testdemo.base.recyclerview.BaseRecyclerData
import com.ellfors.testdemo.biz.blur.BlurActivity
import com.ellfors.testdemo.biz.build.BuildActivity
import com.ellfors.testdemo.biz.edittext.EditTextActivity
import com.ellfors.testdemo.biz.refresh.RefreshActivity
import com.ellfors.testdemo.biz.right_grid.RightGridActivity
import com.ellfors.testdemo.biz.scratch.ScratchActivity
import com.ellfors.testdemo.biz.statusbar.StatusBarActivity
import com.ellfors.testdemo.biz.swipeback.TestSwipeActivity
import com.ellfors.testdemo.biz.tinker.TinkerActivity
import com.ellfors.testdemo.biz.yspay.YSPayActivity
import com.ellfors.testdemo.model.MainItemBean
import com.ellfors.testdemo.permission.PermissionManager
import com.ellfors.testdemo.util.ViewUtil
import com.yanzhenjie.permission.Permission
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity()
{
    private val permissions = arrayOf(
            Permission.WRITE_EXTERNAL_STORAGE,
            Permission.READ_EXTERNAL_STORAGE,
            Permission.READ_PHONE_STATE,
            Permission.CALL_PHONE,
            Permission.CAMERA,
            Permission.ACCESS_COARSE_LOCATION,
            Permission.ACCESS_FINE_LOCATION)

    private val datas: ArrayList<MainItemBean> = arrayListOf(
            MainItemBean(MainItemBean.ID.RIGHT_GRID, "从右至左GridLayoutManager"),
            MainItemBean(MainItemBean.ID.REFRESH, "刷新"),
            MainItemBean(MainItemBean.ID.STATUS_BAR, "状态栏"),
            MainItemBean(MainItemBean.ID.TINKER, "Tinker热修复"),
            MainItemBean(MainItemBean.ID.EDIT_TEXT, "特殊输入框"),
            MainItemBean(MainItemBean.ID.BLUR, "高斯模糊"),
            MainItemBean(MainItemBean.ID.YSPAY, "测试公司支付"),
            MainItemBean(MainItemBean.ID.BUILD, "测试建造者模式"),
            MainItemBean(MainItemBean.ID.SCRATCH, "刮奖控件"),
            MainItemBean(MainItemBean.ID.SWIPE_BACK, "侧滑返回")
    )

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
                MainItemBean.ID.RIGHT_GRID -> RightGridActivity.start(this@MainActivity)
                MainItemBean.ID.REFRESH -> RefreshActivity.start(this@MainActivity)
                MainItemBean.ID.STATUS_BAR -> StatusBarActivity.start(this@MainActivity)
                MainItemBean.ID.TINKER -> TinkerActivity.start(this@MainActivity)
                MainItemBean.ID.EDIT_TEXT -> EditTextActivity.start(this@MainActivity)
                MainItemBean.ID.BLUR -> BlurActivity.start(this@MainActivity)
                MainItemBean.ID.YSPAY -> YSPayActivity.start(this@MainActivity)
                MainItemBean.ID.BUILD -> BuildActivity.start(this@MainActivity)
                MainItemBean.ID.SCRATCH -> ScratchActivity.start(this@MainActivity)
                MainItemBean.ID.SWIPE_BACK -> TestSwipeActivity.start(this@MainActivity)
            }
        }
    }

    private fun getData(): MutableList<BaseRecyclerData>
    {
        val list: MutableList<BaseRecyclerData> = mutableListOf()
        for (bean in datas)
        {
            list.add(BaseRecyclerData(bean))
        }
        return list
    }

}
