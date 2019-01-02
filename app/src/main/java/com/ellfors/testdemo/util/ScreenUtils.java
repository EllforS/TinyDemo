package com.ellfors.testdemo.util;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;

/**
 * 屏幕适配工具类
 * 2019/1/2 11:33
 */
public class ScreenUtils
{
    private static float sNonCompatDensity;
    private static float sNonScaledDensity;

    public static void setCustomDensity(@NonNull Activity activity, final Application application)
    {
        final DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();

        if (sNonCompatDensity == 0)
        {
            sNonCompatDensity = appDisplayMetrics.density;
            sNonScaledDensity = appDisplayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks()
            {
                @Override
                public void onConfigurationChanged(Configuration newConfig)
                {
                    if (newConfig != null && newConfig.fontScale > 0)
                    {
                        sNonScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory()
                {

                }
            });
        }

        final float targetDensity = (float) appDisplayMetrics.widthPixels / 360;
        final float targetScaleDensity = targetDensity * (sNonScaledDensity / sNonCompatDensity);
        final int targetDpi = (int) (160 * targetDensity);

        appDisplayMetrics.density = targetDensity;
        appDisplayMetrics.densityDpi = targetDpi;
        appDisplayMetrics.scaledDensity = targetScaleDensity;

        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.densityDpi = targetDpi;
        activityDisplayMetrics.scaledDensity = targetScaleDensity;
    }
}
