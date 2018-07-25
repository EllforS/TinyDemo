package com.ellfors.testdemo.widget;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;

/**
 * 从左至右GridLayoutManager
 * 2018/7/25 19:42
 */
public class RtlGridLayoutManager extends GridLayoutManager
{

    public RtlGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public RtlGridLayoutManager(Context context, int spanCount)
    {
        super(context, spanCount);
    }

    public RtlGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout)
    {
        super(context, spanCount, orientation, reverseLayout);
    }

    @Override
    protected boolean isLayoutRTL()
    {
        return true;
    }
}