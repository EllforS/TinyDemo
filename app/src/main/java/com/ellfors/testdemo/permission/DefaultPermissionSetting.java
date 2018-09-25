package com.ellfors.testdemo.permission;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import com.ellfors.testdemo.R;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.SettingService;

import java.util.List;

/**
 * DefaultPermissionSetting
 * 2018/3/27 15:51
 */
public class DefaultPermissionSetting
{
    private final Context mContext;
    private PermissionDialog mDialog;
    private PermissionManager.OnSettingListener mListener;

    public DefaultPermissionSetting(Context context)
    {
        this.mContext = context;
    }

    public void showSetting(final List<String> permissions)
    {
        List<String> permissionNames = Permission.transformText(mContext, permissions);
        String message = mContext.getString(R.string.permission_always_failed_message, TextUtils.join("\n", permissionNames));

        final SettingService settingService = AndPermission.permissionSetting(mContext);
        mDialog = PermissionDialog.newBuilder(mContext)
                .setCancelable(false)
                .setTitle(R.string.permission_dialog_title)
                .setMessage(message)
                .setPositiveButton(R.string.setting, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        settingService.execute();
                    }
                })
                .setNegativeButton(R.string.permission_dialog_no, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        settingService.cancel();
                        if (mListener != null)
                            mListener.onNegative();
                    }
                })
                .show();
    }

    public void setListener(PermissionManager.OnSettingListener listener)
    {
        this.mListener = listener;
    }

    public boolean isShowing()
    {
        return mDialog != null && mDialog.isShowing();
    }
}
