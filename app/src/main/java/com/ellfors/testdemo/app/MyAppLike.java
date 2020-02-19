package com.ellfors.testdemo.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;

import com.ellfors.testdemo.R;
import com.ellfors.testdemo.widget.CustomRefreshLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshInitializer;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;

import org.greenrobot.eventbus.EventBus;

import java.util.Iterator;
import java.util.Stack;

/**
 * MyAppLike
 * 2018/9/25 17:12
 */
@SuppressWarnings("unused")
@DefaultLifeCycle(
        application = ".MyApplication",     //application类名
        loaderClass = "com.tencent.tinker.loader.TinkerLoader",   //loaderClassName, 我们这里使用默认即可!
        flags = ShareConstants.TINKER_ENABLE_ALL,
        loadVerifyFlag = false)
public class MyAppLike extends DefaultApplicationLike
{
    public MyAppLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent)
    {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    private static Application mApp;
    private static MyAppLike mAppLike;
    private static Stack<Activity> stack;
    public static Float density = 1.5F;
    public static int width = 0;
    public static int height = 0;

    public static MyAppLike getInstance()
    {
        return mAppLike;
    }

    public static Application getApp()
    {
        return mApp;
    }

    @Override
    public void onBaseContextAttached(Context base)
    {
        super.onBaseContextAttached(base);

        init();
    }

    private void init()
    {
        mAppLike = this;
        mApp = getApplication();
        stack = new Stack<>();
        //初始化Tinker
        TinkerInstaller.install(this);
        //初始化分包
        MultiDex.install(getApplication());
        initRefresh();
    }

    /**
     * 初始化刷新加载控件
     */
    private void initRefresh()
    {
        //设置刷新文字
        ClassicsHeader.REFRESH_HEADER_PULLING = mApp.getString(R.string.refresh_header_pulling);
        ClassicsHeader.REFRESH_HEADER_REFRESHING = mApp.getString(R.string.refresh_header_refreshing);
        ClassicsHeader.REFRESH_HEADER_LOADING = mApp.getString(R.string.refresh_header_loading);
        ClassicsHeader.REFRESH_HEADER_RELEASE = mApp.getString(R.string.refresh_header_release);
        ClassicsHeader.REFRESH_HEADER_FINISH = mApp.getString(R.string.refresh_header_finish);
        ClassicsHeader.REFRESH_HEADER_FAILED = mApp.getString(R.string.refresh_header_failed);
        //设置加载文字
        ClassicsFooter.REFRESH_FOOTER_PULLING = mApp.getString(R.string.refresh_footer_pulling);
        ClassicsFooter.REFRESH_FOOTER_RELEASE = mApp.getString(R.string.refresh_footer_release);
        ClassicsFooter.REFRESH_FOOTER_LOADING = mApp.getString(R.string.refresh_footer_loading);
        ClassicsFooter.REFRESH_FOOTER_REFRESHING = mApp.getString(R.string.refresh_footer_refreshing);
        ClassicsFooter.REFRESH_FOOTER_FINISH = mApp.getString(R.string.refresh_footer_finish);
        ClassicsFooter.REFRESH_FOOTER_FAILED = mApp.getString(R.string.refresh_footer_failed);
        ClassicsFooter.REFRESH_FOOTER_NOTHING = mApp.getString(R.string.refresh_footer_nothing);
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
                layout.setHeaderHeight(/*57*/100);
                layout.setFooterHeight(/*57*/100);
            }
        });
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator()
        {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout)
            {
                return new CustomRefreshLayout(context);

//                return new ClassicsHeader(context)
//                        .setEnableLastTime(false)
//                        .setTextSizeTitle(12)
//                        .setDrawableMarginRight(7)
//                        .setFinishDuration(0)
//                        .setAccentColor(mApp.getResources().getColor(R.color.tra_black_20))
//                        .setArrowDrawable(mApp.getResources().getDrawable(R.drawable.ptr_head_pull_ic))
//                        .setProgressDrawable(mApp.getResources().getDrawable(R.drawable.load_img_loading));
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
                        .setAccentColor(mApp.getResources().getColor(R.color.tra_black_20))
                        .setArrowDrawable(mApp.getResources().getDrawable(R.drawable.ptr_head_pull_ic))
                        .setProgressDrawable(mApp.getResources().getDrawable(R.drawable.load_img_loading));
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
            return stack.lastElement() == null ? mApp.getApplicationContext() : stack.lastElement();
        }
        catch (Exception e)
        {
            return mApp.getApplicationContext();
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
