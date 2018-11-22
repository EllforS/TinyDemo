package com.ellfors.testdemo.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.ellfors.testdemo.R;
import com.ellfors.testdemo.app.MyAppLike;
import com.ellfors.testdemo.ext.ImageExt;
import com.ellfors.testdemo.ext.ValueExt;
import com.ellfors.testdemo.util.ViewUtil;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity implements ImageExt, ValueExt
{
    public static final int LAYOUT_ID_NOT_FOUND = 0;
    private static final int DEFAULT_STATUSBAR_COLOR = R.color.colorPrimary;
    private static final float DEFALUT_STATUS_BAR_ALPHA = 1f;
    private static final int FLYME_STATUSBAR_FONT_COLOR = R.color.color_333333;

    private FragmentFactory mFragmentFactory;
    private ArrayList<Fragment> mList = new ArrayList<>();
    public Context mContext;
    private Unbinder unbinder;
    public Bundle mSavedInstanceState;

    private ImmersionBar immersionBar;
    private boolean showStatusBar = true;
    private int mStatusBarColor = DEFAULT_STATUSBAR_COLOR;
    private float mStatusBarAlpha = DEFALUT_STATUS_BAR_ALPHA;
    private boolean isDarkFont = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        unbinder = ButterKnife.bind(this);

        mContext = this;
        MyAppLike.getInstance().addTask(this);
        mSavedInstanceState = savedInstanceState;
        initInject();
        initEventAndData();
        initStatusBar();
    }

    /**
     * 初始化状态栏
     */
    private void initStatusBar()
    {
        immersionBar = ImmersionBar.with(this);
        if (showStatusBar)
            immersionBar.statusBarColor(mStatusBarColor)
                    .statusBarAlpha(mStatusBarAlpha)
                    .statusBarDarkFont(isDarkFont)
                    .flymeOSStatusBarFontColor(isDarkFont ? FLYME_STATUSBAR_FONT_COLOR : R.color.white)
                    .fitsSystemWindows(true)
                    .init();
        else
            immersionBar
                    .statusBarDarkFont(isDarkFont)
                    .flymeOSStatusBarFontColor(isDarkFont ? FLYME_STATUSBAR_FONT_COLOR : R.color.white)
                    .init();
    }

    /**
     * 设置是否显示沉浸式状态栏
     */
    public void showStatusBar(boolean flag)
    {
        this.showStatusBar = flag;
    }

    /**
     * 设置状态栏颜色
     */
    public void setStatusBarColor(int color)
    {
        this.mStatusBarColor = color;
    }

    /**
     * 设置状态栏文字颜色
     *
     * @param flag true 黑色 false 白色
     */
    public void setStatusBarDarkFont(boolean flag)
    {
        this.isDarkFont = flag;
    }

    /**
     * 设置状态栏透明度
     */
    public void setStatusBarAlpha(float alpha)
    {
        this.mStatusBarAlpha = alpha;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            finish();
            outOverridePendingTransition(this);
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * Toast提示
     */
    public void showToast(String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * ************************************ 需要复写的方法 *******************************************
     */
    /* 绑定布局 */
    public abstract int getLayout();

    /* 初始化事件和数据 */
    public abstract void initEventAndData();

    /* 初始化注入 */
    public void initInject()
    {
    }

    /* 装载Fragment的布局 */
    public int getFragmentGroupId()
    {
        return LAYOUT_ID_NOT_FOUND;
    }

    /**
     * ************************************* 页面切换的方法 ******************************************
     */
    /* 界面返回效果 */
    public static void outOverridePendingTransition(Activity mContext)
    {
        mContext.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);// 往右滑
    }

    /* 界面跳转效果 */
    public static void inOverridePendingTransition(Activity mContext)
    {
        mContext.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);// 往左滑
    }

    /* 向上跳入 */
    public static void upOverridePendingTransition(Activity mContext)
    {
        mContext.overridePendingTransition(R.anim.activity_open_up, R.anim.activity_close_up);  // 往上滑
    }

    /* 向下跳出 */
    public static void downOverridePendingTransition(Activity mContext)
    {
        mContext.overridePendingTransition(R.anim.activity_open_down, R.anim.activity_close_down);// 往下滑
    }

    /* 渐隐进入 */
    public static void fadeInOverridePendingTransition(Activity mContext)
    {
        mContext.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    /**
     * ************************************** 操控Fragment的方法 *************************************
     */
    public FragmentFactory getFragmentFactory()
    {
        if (mFragmentFactory == null)
        {
            mFragmentFactory = new FragmentFactory();
        }
        return mFragmentFactory;
    }

    public boolean isFragmentInCache(BaseFragment f)
    {
        if (mFragmentFactory != null)
        {
            return mFragmentFactory.getFragmentFromCache(f.getClass()) != null;
        }
        return false;
    }

    public <T extends BaseFragment> Fragment replaceFragment(Class<T> clazz)
    {
        return replaceFragment(clazz, null);
    }

    public <T extends BaseFragment> Fragment replaceFragment(Class<T> clazz, Bundle args)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fT = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentByTag(clazz.getName());
        if (getFragmentGroupId() == LAYOUT_ID_NOT_FOUND)
            throw new RuntimeException("Fragment parent id is not found...");
        if (fragment == null)
        {
            fragment = getFragmentFactory().getFragment(clazz, false);
            fragment.setArguments(args);
            fT.add(getFragmentGroupId(), fragment, clazz.getName());
        }
        else
        {
            fT.show(fragment);
        }
        if (!mList.contains(fragment))
        {
            mList.add(fragment);
        }

        for (Fragment other : mList)
        {
            if (other != fragment)
            {
                fT.hide(other);
            }
        }
        fT.commit();
        return fragment;
    }

    protected <T extends BaseFragment> BaseFragment findFragment(Class<T> clazz)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        return (BaseFragment) fragmentManager.findFragmentByTag(clazz.getName());
    }

    public BaseFragment getCurrentFragment()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        return (BaseFragment) fragmentManager.findFragmentById(getFragmentGroupId());
    }

    /**
     * **************************************** 生命周期 ********************************************
     */

    @Override
    protected void onResume()
    {
        super.onResume();
        if (MyAppLike.width == 0)
            ViewUtil.setDensityWH(this);
    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (unbinder != null)
            unbinder.unbind();
        if (immersionBar != null)
            immersionBar.destroy();
        MyAppLike.getInstance().removeTask(this);
    }
}
