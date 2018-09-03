package com.ellfors.testdemo.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.support.multidex.MultiDex;

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
