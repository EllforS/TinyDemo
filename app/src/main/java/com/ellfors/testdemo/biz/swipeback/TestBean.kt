package com.ellfors.testdemo.biz.swipeback

import android.text.TextUtils
import android.util.Log
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

/**
 * TestBean
 * 2019/1/4 14:59
 */
class TestBean
{
    /**
     * Delegates.vetoable("默认值"){}
     * 监听数值改变，并决定是否赋值
     * 如果只监听则使用Delegates.observable()
     */
    var userName: String by Delegates.vetoable("not found") { _: KProperty<*>, old: String, new: String ->
        Log.d("AAA", "value === $old ____ $new")
        return@vetoable !TextUtils.equals(new, "666")
    }
}