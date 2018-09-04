package com.ellfors.testdemo.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;

import com.ellfors.testdemo.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshInitializer;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import org.greenrobot.eventbus.EventBus;

import java.util.Iterator;
import java.util.Stack;

public class MyApp extends Application
{
    private static MyApp mApp;
    private static Stack<Activity> stack;
    public static Float density = 1.5F;
    public static int width = 0;
    public static int height = 0;

    public static MyApp getInstance()
    {
        return mApp;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();

        init();
    }

    private void init()
    {
        mApp = this;
        stack = new Stack<>();
        //初始化分包
        MultiDex.install(this);
        initRefresh();
    }

    /**
     * 初始化刷新加载控件
     */
    private void initRefresh()
    {
        //设置刷新文字
        ClassicsHeader.REFRESH_HEADER_PULLING = getString(R.string.refresh_header_pulling);
        ClassicsHeader.REFRESH_HEADER_REFRESHING = getString(R.string.refresh_header_refreshing);
        ClassicsHeader.REFRESH_HEADER_LOADING = getString(R.string.refresh_header_loading);
        ClassicsHeader.REFRESH_HEADER_RELEASE = getString(R.string.refresh_header_release);
        ClassicsHeader.REFRESH_HEADER_FINISH = getString(R.string.refresh_header_finish);
        ClassicsHeader.REFRESH_HEADER_FAILED = getString(R.string.refresh_header_failed);
        //设置加载文字
        ClassicsFooter.REFRESH_FOOTER_PULLING = getString(R.string.refresh_footer_pulling);
        ClassicsFooter.REFRESH_FOOTER_RELEASE = getString(R.string.refresh_footer_release);
        ClassicsFooter.REFRESH_FOOTER_LOADING = getString(R.string.refresh_footer_loading);
        ClassicsFooter.REFRESH_FOOTER_REFRESHING = getString(R.string.refresh_footer_refreshing);
        ClassicsFooter.REFRESH_FOOTER_FINISH = getString(R.string.refresh_footer_finish);
        ClassicsFooter.REFRESH_FOOTER_FAILED = getString(R.string.refresh_footer_failed);
        ClassicsFooter.REFRESH_FOOTER_NOTHING = getString(R.string.refresh_footer_nothing);
        //全局属性设置
        SmartRefreshLayout.setDefaultRefreshInitializer(new DefaultRefreshInitializer()
        {
            @Override
            public void initialize(@NonNull Context context, @NonNull RefreshLayout layout)
            {
                //是否在刷新/加载的时候禁止列表的操作
                layout.setDisableContentWhenRefresh(true);
                layout.setDisableContentWhenLoading(true);
                //Header、Footer高度
                layout.setHeaderHeight(57);
                layout.setFooterHeight(57);
            }
        });
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator()
        {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout)
            {
                return new ClassicsHeader(context)
                        .setEnableLastTime(false)
                        .setTextSizeTitle(12)
                        .setDrawableMarginRight(7)
                        .setFinishDuration(0)
                        .setAccentColor(getResources().getColor(R.color.tra_black_20))
                        .setArrowDrawable(getResources().getDrawable(R.drawable.ptr_head_pull_ic))
                        .setProgressDrawable(getResources().getDrawable(R.drawable.load_img_loading));
            }
        });
        //设置全局Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator()
        {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout)
            {
                return new ClassicsFooter(context)
                        .setTextSizeTitle(12)
                        .setDrawableMarginRight(7)
                        .setFinishDuration(0)
                        .setAccentColor(getResources().getColor(R.color.tra_black_20))
                        .setArrowDrawable(getResources().getDrawable(R.drawable.ptr_head_pull_ic))
                        .setProgressDrawable(getResources().getDrawable(R.drawable.load_img_loading));
            }
        });
    }

    /*
     ****************************************** Application相关方法 *********************************
     */

    /**
     * 将Activity加入栈
     */
    public void addTask(Activity activity)
    {
        if (stack == null)
            stack = new Stack<>();
        stack.add(activity);
    }

    /**
     * 将Activity移出栈
     */
    public void removeTask(Activity activity)
    {
        if (stack != null && activity != null && stack.contains(activity))
            stack.remove(activity);
    }

    /**
     * 获取栈顶的Activity
     */
    public Context currentActivity()
    {
        try
        {
            return stack.lastElement() == null ? getApplicationContext() : stack.lastElement();
        }
        catch (Exception e)
        {
            return getApplicationContext();
        }
    }

    /**
     * 销毁Acitivity
     */
    public void finishLastActivity()
    {
        Activity activity = stack.lastElement();
        if (activity != null)
            this.finishActivity(activity);
    }

    /**
     * 销毁Acitivity
     */
    public void finishActivity(Activity activity)
    {
        if (activity != null && stack != null)
        {
            stack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 销毁Acitivity
     */
    public void finishActivity(Class<?> cls)
    {
        if (cls == null || stack == null)
            return;
        for (int i = 0; i < stack.size(); i++)
        {
            if (stack.get(i) != null && stack.get(i).getClass().equals(cls))
                this.finishActivity(stack.get(i));
        }
    }

    /**
     * 判断是否含有Activity
     */
    public boolean hasActivity(Class<?> cls)
    {
        Iterator var2 = stack.iterator();
        Activity activity;
        do
        {
            if (!var2.hasNext())
            {
                return false;
            }

            activity = (Activity) var2.next();
        } while (!activity.getClass().equals(cls));
        return true;
    }

    /**
     * 销毁全部Activity
     */
    public void finishAllActivity()
    {
        int i = 0;
        for (int size = stack.size(); i < size; ++i)
        {
            if (null != stack.get(i))
            {
                stack.get(i).finish();
            }
        }
        stack.clear();
    }

    /**
     * 判断是否大于当前版本
     */
    public static boolean isMethodsCompat(int VersionCode)
    {
        int currentVersion = Build.VERSION.SDK_INT;
        return currentVersion >= VersionCode;
    }

    public Context getContext()
    {
        return this;
    }

    /*
     ******************************************** Event方法 *****************************************
     */

    public static void eventRegister(Object subscriber)
    {
        EventBus.getDefault().register(subscriber);
    }

    public static void eventUnRegister(Object subscriber)
    {
        EventBus.getDefault().unregister(subscriber);
    }

    public static void eventPost(Object event)
    {
        EventBus.getDefault().post(event);
    }

}
