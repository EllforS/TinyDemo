package com.ellfors.testdemo.biz.medal.adapter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.ellfors.testdemo.biz.medal.DSContentFragment
import com.ellfors.testdemo.biz.medal.DSTitleFragment

class DSVpAdapter(fm: FragmentManager, private val isTitle: Boolean) : FragmentPagerAdapter(fm)
{
    override fun getItem(position: Int): Fragment
    {
        val bundle = Bundle()
        bundle.putInt("pos", position)
        val fragment = if (isTitle) DSTitleFragment() else DSContentFragment()
        fragment.arguments = bundle
        return fragment
    }

    override fun getCount(): Int
    {
        return 9
    }
}