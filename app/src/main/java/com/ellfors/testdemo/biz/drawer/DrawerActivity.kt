package com.ellfors.testdemo.biz.drawer

import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.Gravity
import com.ellfors.testdemo.R
import com.ellfors.testdemo.base.BaseActivity
import kotlinx.android.synthetic.main.activity_drawer.*

/**
 * DrawerActivity
 * 2019/4/2 11:22
 */
class DrawerActivity : BaseActivity()
{
    companion object
    {
        fun start(activity: BaseActivity)
        {
            activity.startActivity(Intent(activity, DrawerActivity::class.java))
            inOverridePendingTransition(activity)
        }
    }

    override fun getLayout(): Int
    {
        return R.layout.activity_drawer
    }

    override fun initEventAndData()
    {
        //设置阴影颜色
        mDrawerLayout.setScrimColor(Color.TRANSPARENT)


        //测试Kotlin函数
        val testBean: TestBean? = TestBean()
        val testA: TestBean.TestA? = TestBean.TestA()
        val testB: TestBean.TestB? = TestBean.TestB()
        val testC: TestBean.TestC? = TestBean.TestC()
        //apply一般用于  赋值、初始化、多层级判空
        testBean?.apply {
            valueA = testA
        }?.valueA?.apply {
            valueB = testB
        }?.valueB?.apply {
            valueC = testC
        }?.valueC?.apply {
            name = "测试"
        }
        //run是let、with的结合体，一般用于操作  节省类名重复调用  比如RecyclerView Adapter中的OnBindViewHolder
        testBean?.run {
            Log.d("AAA", "名字$name")
            Log.d("AAA", "年龄$age")
        }
    }

    public fun closeDrawer()
    {
        mDrawerLayout.closeDrawer(Gravity.END)
    }
}