package com.ellfors.testdemo.biz.build;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;

/**
 * 测试Build模式
 * 2018/12/25 17:52
 */
public class TestBuild
{
    public static BuilderInterface with(Context context)
    {
        return BuilderFactory.createBuilder(context);
    }

    public static BuilderInterface with(Activity context)
    {
        return BuilderFactory.createBuilder(context);
    }

    public static BuilderInterface with(Fragment context)
    {
        return BuilderFactory.createBuilder(context.getActivity());
    }

    public static class BuilderFactory
    {
        static BuilderInterface createBuilder(Context context)
        {
            //这里可以分版本或者其他的来创建对应的实现类
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
                return new BuilderImpl(context);
            else
                return new BuilderImpl(context);
        }
    }
}
