package com.ellfors.testdemo.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ellfors.testdemo.R;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

/**
 * CustomRefreshLayout
 * 2019/5/30 11:16
 */
public class CustomRefreshLayout extends LinearLayout implements RefreshHeader
{
    private ImageView mProgressView;
    private AnimationDrawable mAnimationDrawable;

    public CustomRefreshLayout(Context context)
    {
        this(context, null);
    }

    public CustomRefreshLayout(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);

        setGravity(Gravity.CENTER);

        addView(getProgressView(context));
    }

    private ImageView getProgressView(Context context)
    {
        mProgressView = new ImageView(context);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mProgressView.setLayoutParams(params);

        mProgressView.setBackgroundResource(R.drawable.progress_loading);
        mAnimationDrawable = (AnimationDrawable) mProgressView.getBackground();

        return mProgressView;
    }

    /*
     ******************************************* 实现的属性 ***************************************
     */
    @NonNull
    @Override
    public View getView()
    {
        return this;
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle()
    {
        return SpinnerStyle.Translate;
    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight)
    {

    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success)
    {
        if (mAnimationDrawable != null)
            mAnimationDrawable.stop();
        return 0;
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState)
    {
        if (newState == RefreshState.PullDownToRefresh)
        {
            if (mAnimationDrawable != null)
                mAnimationDrawable.start();
        }
    }

    @Override
    public void setPrimaryColors(int... colors)
    {

    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight)
    {

    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight)
    {

    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight)
    {

    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax)
    {

    }

    @Override
    public boolean isSupportHorizontalDrag()
    {
        return false;
    }
}
