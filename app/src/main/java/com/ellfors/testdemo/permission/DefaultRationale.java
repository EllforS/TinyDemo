package com.ellfors.testdemo.permission;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import com.ellfors.testdemo.R;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;

import java.util.List;

/**
 * 当用户拒绝权限时的友善弹窗提醒
 * 2018/3/27 09:57
 */
public final class DefaultRationale implements Rationale
{
    @Override
    public void showRationale(Context context, List<String> permissions, final RequestExecutor executor)
    {
        List<String> permissionNames = Permission.transformText(context, permissions);
        String message = context.getString(R.string.permission_dialog_rationale, TextUtils.join("\n", permissionNames));

        PermissionDialog.newBuilder(context)
                .setCancelable(false)
                .setTitle(R.string.permission_dialog_title)
                .setMessage(message)
                .setPositiveButton(R.string.permission_dialog_resume, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        executor.execute();
                    }
                })
                .setNegativeButton(R.string.permission_dialog_cancel, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        executor.cancel();
                    }
                })
                .show();
    }
}
