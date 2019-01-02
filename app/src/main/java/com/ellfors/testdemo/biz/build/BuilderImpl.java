package com.ellfors.testdemo.biz.build;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * BuilderImplImpl
 * 2018/12/25 18:10
 */
public class BuilderImpl implements BuilderInterface
{
    private Context mContext;
    private String value_1;
    private Integer value_2;
    private Boolean value_3;

    BuilderImpl(Context mContext)
    {
        this.mContext = mContext;
    }

    @Override
    public BuilderImpl setValue_1(String value)
    {
        this.value_1 = value;
        return this;
    }

    @Override
    public BuilderImpl setValue_2(int value)
    {
        this.value_2 = value;
        return this;
    }

    @Override
    public BuilderImpl setValue_3(boolean value)
    {
        this.value_3 = value;
        return this;
    }

    @Override
    public void toast()
    {
        String str = (TextUtils.isEmpty(value_1) ? "" : value_1) + "___" +
                (value_2 == null ? "00" : value_2.toString()) + "___" +
                (value_3 == null ? "not_found" : value_3.toString());
        if (mContext != null && !TextUtils.isEmpty(str))
            Toast.makeText(mContext, str, Toast.LENGTH_LONG).show();
    }
}