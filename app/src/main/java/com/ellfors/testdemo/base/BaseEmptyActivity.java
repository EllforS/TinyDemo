package com.ellfors.testdemo.base;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ellfors.testdemo.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

/**
 * BaseEmptyActivity
 * 2019/1/2 11:22
 */
public abstract class BaseEmptyActivity extends AppCompatActivity
{
    View view_empty;
    View view_net_error;

    private boolean isShowingReplaceView = false;
    private View targetView;
    private ViewGroup viewParent;
    private int viewIndex = -1;

    /**
     * 显示无数据页
     */
    @SuppressLint("InflateParams")
    public void showEmptyError(String tips, int drawableRes)
    {
        if (isShowingReplaceView || targetView == null || viewIndex == -1)
            return;
        if (view_empty == null)
        {
            view_empty = LayoutInflater.from(this).inflate(R.layout.item_default_empty, null);
            view_empty.setOnClickListener(null);
            ViewGroup.LayoutParams layoutParams = targetView.getLayoutParams();
            view_empty.setLayoutParams(layoutParams);
        }
        TextView tvTips = view_empty.findViewById(R.id.tv_tips);
        ImageView ivImg = view_empty.findViewById(R.id.iv_img);
        if (!TextUtils.isEmpty(tips))
            tvTips.setText(tips);
        if (drawableRes != 0)
            ivImg.setImageResource(drawableRes);
        if (viewParent instanceof SmartRefreshLayout)
        {
            ((SmartRefreshLayout) viewParent).setRefreshContent(view_empty);
        }
        else
        {
            viewParent.removeViewAt(viewIndex);
            viewParent.addView(view_empty, viewIndex);
        }
        isShowingReplaceView = true;
    }

    /**
     * 显示无数据页
     */
    public void showEmptyError()
    {
        this.showEmptyError("", 0);
    }

    /**
     * 显示无网络页
     */
    public void showNetError()
    {
        if (isShowingReplaceView || targetView == null || viewIndex == -1)
            return;
        if (view_net_error == null)
        {
            view_net_error = LayoutInflater.from(this).inflate(R.layout.item_default_net_error, null);
            view_net_error.setOnClickListener(null);
            ViewGroup.LayoutParams layoutParams = targetView.getLayoutParams();
            view_net_error.setLayoutParams(layoutParams);
        }

        if (viewParent instanceof SmartRefreshLayout)
        {
            ((SmartRefreshLayout) viewParent).setRefreshContent(view_net_error);
        }
        else
        {
            viewParent.removeViewAt(viewIndex);
            viewParent.addView(view_net_error, viewIndex);
        }

        isShowingReplaceView = true;
    }

    /**
     * 显示数据页
     */
    public void showContent()
    {
        if (targetView != null && isShowingReplaceView)
        {
            if (viewParent instanceof SmartRefreshLayout)
            {
                ((SmartRefreshLayout) viewParent).setRefreshContent(targetView);
            }
            else
            {
                viewParent.removeViewAt(viewIndex);
                viewParent.addView(targetView, viewIndex);
            }
            isShowingReplaceView = false;
        }
    }

    /**
     * 设置需要替换的View
     */
    public final void setTargetView(View view)
    {
        this.targetView = view;
        viewIndex = getTargetViewIndex();
        viewParent = (ViewGroup) targetView.getParent();
    }

    private int getTargetViewIndex()
    {
        if (targetView == null)
            return -1;
        ViewGroup viewGroup = (ViewGroup) targetView.getParent();
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++)
        {
            if (viewGroup.getChildAt(i) == targetView)
                return i;
        }
        return -1;
    }
}
