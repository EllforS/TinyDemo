package com.ellfors.testdemo.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaMetadataRetriever;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.support.annotation.IntRange;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ellfors.testdemo.app.MyApp;
import com.ellfors.testdemo.widget.FixedSpeedScroller;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * UI工具类
 * 2018/3/26 15:31
 */
public class ViewUtil
{
    //最后点击按钮的时间
    private static long lastClickTime;
    //过滤Emoji表情
    private static Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
    //过滤特殊字符
    private static Pattern speChat = Pattern.compile("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]");
    //只能输入中英文•
    private static Pattern englishChinese = Pattern.compile("^[\\u4e00-\\u9fa5·a-zA-Z]+$");

    /**
     * 防止按钮连续点击的方法（Button,ImageButton等）
     *
     * @return 点击按钮的时间间隔够不够0.5秒
     */
    public static boolean isFastDoubleClick()
    {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 500)
        {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 防止按钮连续点击的方法（Button,ImageButton等）
     */
    public static boolean isFastDoubleClick(@IntRange(from = 0, to = 2000) int delay)
    {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < delay)
        {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 给一个View设置透明度
     *
     * @param view  需要设置透明度的view
     * @param alpha 透明度值
     */
    public static void setAlpha(View view, float alpha)
    {
        AlphaAnimation alphaAnimation = new AlphaAnimation(alpha, alpha);
        alphaAnimation.setDuration(0);
        alphaAnimation.setFillAfter(true);
        // 设置透明度
        view.startAnimation(alphaAnimation);
    }

    /**
     * 设置屏幕相关数据
     */
    public static void setDensityWH(Activity mContext)
    {
        // 得到屏幕密度
        DisplayMetrics dm = new DisplayMetrics();
        mContext.getWindowManager().getDefaultDisplay().getMetrics(dm);
        MyApp.density = dm.density;// 像素密度
        MyApp.width = dm.widthPixels; // 像素：宽
        MyApp.height = dm.heightPixels; // 像素：高
    }

    /**
     * 显示软键盘
     */
    public static void showKeybord(final EditText editText)
    {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        Timer timer = new Timer();
        timer.schedule(new TimerTask()
        {
            public void run()
            {
                InputMethodManager inputManager = (InputMethodManager) MyApp.getInstance().getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputManager != null)
                    inputManager.showSoftInput(editText, 0);
            }
        }, 500);
    }

    /**
     * 隐藏软键盘
     */
    public static void hideInputWindow(Activity activity)
    {
        try
        {
            View view = activity.getCurrentFocus();
            if (view != null)
            {
                InputMethodManager inputManager = ((InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE));
                if (inputManager != null)
                    inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 禁止EditText输入Emoji表情、空格、特殊字符
     */
    public static void filterEmojiAndSpecial(EditText editText, @IntRange(from = 0, to = Integer.MAX_VALUE) int maxLength)
    {
        InputFilter emojiFilter = new InputFilter()
        {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend)
            {
                Matcher emojiMatcher = emoji.matcher(source);
                Matcher speMatcher = speChat.matcher(source.toString());
                if (emojiMatcher.find())
                {
                    return "";
                }
                else if (source.equals(" "))
                {
                    return "";
                }
                else if (speMatcher.find())
                {
                    return "";
                }
                return null;
            }
        };
        editText.setFilters(new InputFilter[]{emojiFilter, new InputFilter.LengthFilter(maxLength)});
    }

    /**
     * 禁止EditText输入Emoji表情
     */
    public static void filterEmoji(EditText editText, @IntRange(from = 0, to = Integer.MAX_VALUE) int maxLength)
    {
        InputFilter emojiFilter = new InputFilter()
        {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend)
            {
                Matcher emojiMatcher = emoji.matcher(source);
                if (emojiMatcher.find())
                {
                    return "";
                }
                return null;
            }
        };
        editText.setFilters(new InputFilter[]{emojiFilter, new InputFilter.LengthFilter(maxLength)});
    }

    /**
     * 名字过滤,只能输入中文和英文和·
     */
    public static void filterForName(EditText editText, @IntRange(from = 0, to = Integer.MAX_VALUE) int maxLength)
    {
        InputFilter filter = new InputFilter()
        {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend)
            {
                Matcher englishMatcher = englishChinese.matcher(source.toString());

                if (!englishMatcher.matches())
                {
                    return "";
                }
                return null;
            }
        };
        editText.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(maxLength)});
    }

    /**
     * 设置EditText只能输入俩位小数
     * (需要将此方法放到addTextChangedListener的onTextChanged方法中)
     */
    public static String setInputNumberDecimal(EditText editText, CharSequence s)
    {
        if (editText == null || TextUtils.isEmpty(s))
            return "";
        if (s.toString().contains("."))
        {
            if (s.length() - 1 - s.toString().indexOf(".") > 2)
            {
                s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
                editText.setText(s);
                editText.setSelection(s.length());
            }
        }
        if (s.toString().trim().equals("."))
        {
            s = "0" + s;
            editText.setText(s);
            editText.setSelection(2);
        }
        if (s.toString().startsWith("0") && s.toString().trim().length() > 1)
        {
            if (!s.toString().substring(1, 2).equals("."))
            {
                editText.setText(s.subSequence(0, 1));
                editText.setSelection(1);
            }
        }
        return editText.getText().toString().trim();
    }

    /**
     * 获取状态栏高度 + Title高度
     */
    public static int getStatusBarHeight(Context context, int actionBarHeight)
    {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
        {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        result += MyApp.density * actionBarHeight;
        return result;
    }

    /**
     * 设置标题栏右边图标
     */
    public static void setRightIcon(Activity context, TextView textView, int icon)
    {
        if (icon == 0)
        {
            textView.setCompoundDrawables(null, null, null, null);
        }
        else
        {
            Drawable drawable = context.getResources().getDrawable(icon);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            textView.setCompoundDrawables(null, null, drawable, null);
        }
    }

    /**
     * 设置textView 左图标
     */
    public static void setLeftIcon(Activity context, TextView textView, int icon, int drawablePadding)
    {
        if (icon == 0)
        {
            textView.setCompoundDrawables(null, null, null, null);
        }
        else
        {
            Drawable drawable = context.getResources().getDrawable(icon);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            textView.setCompoundDrawables(drawable, null, null, null);
            textView.setCompoundDrawablePadding(DensityUtil.dp2px(context, drawablePadding));
        }
    }

    /**
     * 设置图片背景为视频的第一帧
     */
    public static void setImageBg4VideoBitmap(ImageView iv, String videoUrl)
    {
        Bitmap bitmap = getNetVideoBitmap(videoUrl);
        if (bitmap != null)
        {
            iv.setImageBitmap(bitmap);
        }
        else
        {
            iv.setBackgroundResource(0);
        }
    }

    /**
     * 服务器返回url，通过url去获取视频的第一帧
     * Android 原生给我们提供了一个MediaMetadataRetriever类
     * 提供了获取url视频第一帧的方法,返回Bitmap对象
     */
    public static Bitmap getNetVideoBitmap(String videoUrl)
    {
        Bitmap bitmap = null;

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try
        {
            //根据url获取缩略图
            retriever.setDataSource(videoUrl, new HashMap());
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST);
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
        finally
        {
            retriever.release();
        }
        return bitmap;
    }

    /**
     * 获取本地视频的第一帧
     */
    public static Bitmap getLocalVideoBitmap(String localPath)
    {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try
        {
            //根据文件路径获取缩略图
            retriever.setDataSource(localPath);
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime();
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
        finally
        {
            retriever.release();
        }
        return bitmap;
    }

    /**
     * 去掉RecyclerView Item刷新动画
     */
    public static void clearRecyclerViewItemAnimator(RecyclerView mRecyclerView)
    {
        ((DefaultItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    /**
     * 判断是否为中文版
     */
    public static boolean isZh(Context context)
    {
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();

        if (language.endsWith("zh"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * 检查是否存在SDCard
     */
    public static boolean hasSdcard()
    {
        String state = Environment.getExternalStorageState();

        if (state.equals(Environment.MEDIA_MOUNTED))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * 设置View的Shape颜色
     */
    public static void setViewShapeColor(View view, int color)
    {
        GradientDrawable myGrad = (GradientDrawable) view.getBackground();
        myGrad.setColor(color);
    }

    /**
     * 设置View的Shape颜色
     */
    public static void setViewShapeColor(View view, String colorString)
    {
        GradientDrawable myGrad = (GradientDrawable) view.getBackground();
        myGrad.setColor(Color.parseColor(colorString));
    }

    /**
     * 验证是否存在可用网络
     */
    public static boolean CheckNetworkState(Context context)
    {
        int state = currentNetwork(context);
        return state < 2;
    }

    /**
     * 验证网络状态 0 存在wifi网络， 1 存在2/3G网络，2无网络连接
     */
    @SuppressLint("MissingPermission")
    private static int currentNetwork(Context context)
    {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo.State mobile = null, wifi = null;

        try
        {
            mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        try
        {
            wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        // 判断当前连接的网络 返回相对应的状态
        if (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING)
            return 0;
        else if (mobile == NetworkInfo.State.CONNECTED || mobile == NetworkInfo.State.CONNECTING)
            return 1;
        else
            return 2;
    }

    /**
     * 设置ViewPager的滑动时间
     *
     * @param context
     * @param viewpager      ViewPager控件
     * @param DurationSwitch 滑动延时
     */
    public static void controlViewPagerSpeed(Context context, ViewPager viewpager, int DurationSwitch)
    {
        try
        {
            Field mField;
            mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            FixedSpeedScroller mScroller = new FixedSpeedScroller(context, new AccelerateInterpolator());
            mScroller.setmDuration(DurationSwitch);
            mField.set(viewpager, mScroller);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
