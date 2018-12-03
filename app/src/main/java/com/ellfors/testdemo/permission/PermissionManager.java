package com.ellfors.testdemo.permission;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.Request;

import java.util.List;

/**
 * 权限管理
 * 2018/3/27 09:39
 */
public class PermissionManager
{
    private DefaultPermissionSetting mSetting;
    private Context mContext;
    private static Request mRequest;

    public PermissionManager(Context context)
    {
        mContext = context;
        mSetting = new DefaultPermissionSetting(mContext);
        mRequest = AndPermission.with(context);
    }

    public PermissionManager(Activity context)
    {
        mContext = context;
        mSetting = new DefaultPermissionSetting(mContext);
        mRequest = AndPermission.with(context);
    }

    public PermissionManager(Fragment context)
    {
        mContext = context.getActivity();
        mSetting = new DefaultPermissionSetting(mContext);
        mRequest = AndPermission.with(context);
    }

    public PermissionManager(android.app.Fragment context)
    {
        mContext = context.getActivity();
        mSetting = new DefaultPermissionSetting(mContext);
        mRequest = AndPermission.with(context);
    }

    /**
     * 添加权限
     */
    public PermissionManager addPermission(String... permissions)
    {
        if (mRequest != null)
            mRequest.permission(permissions);
        return this;
    }

    /**
     * 添加权限组
     */
    public PermissionManager addPermission(String[]... permissions)
    {
        if (mRequest != null)
            mRequest.permission(permissions);
        return this;
    }

    /**
     * 添加引导提示
     */
    public PermissionManager addRationale()
    {
        if (mRequest != null)
            mRequest.rationale(new DefaultRationale());
        return this;
    }

    /**
     * 添加引导提示
     */
    public PermissionManager addRationale(Rationale rationale)
    {
        if (mRequest != null)
            mRequest.rationale(rationale);
        return this;
    }

    /**
     * 添加监听(默认开启友好提示)
     */
    public void addListener(final OnPermissionListener listener)
    {
        if (mRequest != null)
            mRequest.onGranted(new Action()
            {
                @Override
                public void onAction(List<String> permissions)
                {
                    if (listener != null)
                        listener.onSuccess(permissions);
                }
            }).onDenied(new Action()
            {
                @Override
                public void onAction(List<String> permissions)
                {
                    if (listener != null)
                        listener.onFailed(permissions);
                    if (AndPermission.hasAlwaysDeniedPermission(mContext, permissions) && mSetting != null)
                        mSetting.showSetting(permissions);
                }
            }).start();
    }

    /**
     * 添加监听
     */
    public void addListener(final OnPermissionListener listener, final boolean showSetting)
    {
        if (mRequest != null)
            mRequest.onGranted(new Action()
            {
                @Override
                public void onAction(List<String> permissions)
                {
                    if (listener != null)
                        listener.onSuccess(permissions);
                }
            }).onDenied(new Action()
            {
                @Override
                public void onAction(List<String> permissions)
                {
                    if (listener != null)
                        listener.onFailed(permissions);
                    if (showSetting && AndPermission.hasAlwaysDeniedPermission(mContext, permissions) && mSetting != null)
                        mSetting.showSetting(permissions);
                }
            }).start();
    }

    public void setSettingListener(OnSettingListener listener)
    {
        if (mSetting == null)
            return;
        mSetting.setListener(listener);
    }

    public boolean isSettingShowing()
    {
        return mSetting != null && mSetting.isShowing();
    }

    public interface OnSettingListener
    {
        void onNegative();
    }

    public interface OnPermissionListener
    {
        void onSuccess(List<String> permissions);

        void onFailed(List<String> permissions);
    }

}
