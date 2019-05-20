package com.ellfors.testdemo.biz.dragphoto

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

/**
 * 图片拖拽返回适配器
 * 2019/5/9 11:38
 */
class DragPhotoVpAdapter(fm: FragmentManager, private val fileList: ArrayList<String>?) : FragmentStatePagerAdapter(fm)
{
    override fun getItem(position: Int): Fragment
    {
        val url = fileList?.get(position)
        return DragPhotoFragment.newInstance(url)
    }

    override fun getCount(): Int
    {
        return fileList?.size ?: 0
    }
}