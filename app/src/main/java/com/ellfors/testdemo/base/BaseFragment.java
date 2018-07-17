package com.ellfors.testdemo.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ellfors.testdemo.ext.ImageExt;
import com.ellfors.testdemo.ext.ValueExt;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment implements ImageExt, ValueExt
{
    public Context mContext;
    private View mView;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(getLayout(), null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        mContext = getActivity();
        mView = view;
        initInject();
        initEventAndData();
    }

    /**
     * ************************************* 需要复写的方法 ******************************************
     */
    /* 绑定布局 */
    public abstract int getLayout();

    /* 初始化事件和数据 */
    public abstract void initEventAndData();

    /* 初始化注入 */
    public void initInject()
    {

    }

    public boolean goBack()
    {
        return false;
    }

    /**
     * ************************************** 生命周期 **********************************************
     */
    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        if (unbinder != null)
            unbinder.unbind();
    }
}
