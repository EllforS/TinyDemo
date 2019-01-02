package com.ellfors.testdemo.biz.yspay;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import com.ellfors.testdemo.base.BaseActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 支付工具类
 * 2018/12/25 10:14
 */
public class YSPayUtil
{
    private static final String FILE_PATH = Environment.getExternalStorageDirectory() + "/TestDemo" + "/download/";
    private static final String FILE_NAME = "YinShengPlugin.apk";

    /**
     * 校验支付Apk是否安装
     * 未安装则安装Apk
     */
    public static boolean checkApkExist(BaseActivity context)
    {
        boolean flag = isInstallPayPackage();
        if (flag)
            return true;
        new MyAsync().execute(context);
        return false;
    }

    private static class MyAsync extends AsyncTask<BaseActivity, Void, BaseActivity>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            //开启LoadingDialog
        }

        @Override
        protected BaseActivity doInBackground(BaseActivity... contexts)
        {
            if (contexts != null && contexts.length > 0)
            {
                copyAssetsFile(contexts[0]);
                return contexts[0];
            }
            else
            {
                return null;
            }
        }

        @Override
        protected void onPostExecute(BaseActivity context)
        {
            super.onPostExecute(context);
            if (context == null)
                return;
            //关闭LoadingDialog
            openFile(context, new File(FILE_PATH + FILE_NAME));
        }
    }

    /**
     * 判断手机是否有支付APP
     */
    private static boolean isInstallPayPackage()
    {
        return new File("/data/data/com.yinsheng.android.app").exists();
    }

    /**
     * 复制文件到SD卡
     */
    private static Uri copyAssetsFile(Context context)
    {
        try
        {
            InputStream mInputStream = context.getAssets().open(FILE_NAME);
            File file = new File(FILE_PATH);
            if (!file.exists())
            {
                file.mkdirs();
            }
            File mFile = new File(FILE_PATH + File.separator + FILE_NAME);
            if (!mFile.exists())
                mFile.createNewFile();
            FileOutputStream mFileOutputStream = new FileOutputStream(mFile);
            byte[] mbyte = new byte[1024];
            int i = 0;
            while ((i = mInputStream.read(mbyte)) > 0)
            {
                mFileOutputStream.write(mbyte, 0, i);
            }
            mInputStream.close();
            mFileOutputStream.close();
            Uri uri = null;
            try
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                {
                    //包名.fileprovider
                    uri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileProvider", mFile);
                }
                else
                {
                    uri = Uri.fromFile(mFile);
                }
            }
            catch (ActivityNotFoundException e)
            {
                e.printStackTrace();
            }
            MediaScannerConnection.scanFile(context, new String[]{mFile.getAbsolutePath()}, null, null);
            return uri;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    /**
     * 适配android 7.0 打开文件
     */
    private static void openFile(Context mContext, File file)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".fileProvider", file);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        }
        else
        {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        mContext.startActivity(intent);
    }

}
