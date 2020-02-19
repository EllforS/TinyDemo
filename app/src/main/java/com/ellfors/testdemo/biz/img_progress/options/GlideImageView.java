package com.ellfors.testdemo.biz.img_progress.options;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ellfors.testdemo.biz.img_progress.progress.CircleProgressView;
import com.ellfors.testdemo.biz.img_progress.progress.ProgressInterceptor;
import com.ellfors.testdemo.biz.img_progress.progress.ProgressListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import static android.view.Gravity.CENTER;

/**
 * GlideImageView
 * 2019-07-04 17:59
 */
public class GlideImageView extends FrameLayout
{
    private ImageView mImageView;
    private CircleProgressView mProgressBar;

    public GlideImageView(Context context)
    {
        super(context);
        init(context);
    }

    public GlideImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    public GlideImageView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int mParentHeight = MeasureSpec.getSize(heightMeasureSpec);
        int mParentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width;
        int height;

        if (widthMode == MeasureSpec.EXACTLY)
            width = mParentWidth;
        else
            width = DensityUtil.dp2px(100);
        if (heightMode == MeasureSpec.EXACTLY)
            height = mParentHeight;
        else
            height = DensityUtil.dp2px(100);
        setMeasuredDimension(width, height);
        setBackgroundColor(Color.TRANSPARENT);
    }

    private void init(Context context)
    {
        addView(getImage(context));
        addView(getProgressBar(context));
    }

    private ImageView getImage(Context mContext)
    {
        mImageView = new ImageView(mContext);
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        mImageView.setLayoutParams(params);
        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return mImageView;
    }

    private CircleProgressView getProgressBar(Context mContext)
    {
        mProgressBar = new CircleProgressView(mContext);
        LayoutParams params = new LayoutParams(
                DensityUtil.dp2px(50),
                DensityUtil.dp2px(50)
        );
        params.gravity = CENTER;
        mProgressBar.setLayoutParams(params);
        mProgressBar.setProgress(0);
        mProgressBar.setInnerPadding(2);
        mProgressBar.setOuterColor(0xE5ffffff);
        mProgressBar.setOuterSize(1);
        mProgressBar.setNormalBarColor(Color.TRANSPARENT);
        mProgressBar.setReachBarColor(0xE5ffffff);
        mProgressBar.setProgressStyle(CircleProgressView.ProgressStyle.FILL_IN_ARC);
        mProgressBar.setVisibility(GONE);
        return mProgressBar;
    }

    /*
     ******************************************* 外部方法 ****************************************
     */

    public void setUrl(final String url)
    {
        ProgressInterceptor.addListener(url, new ProgressListener()
        {
            @Override
            public void onProgress(int progress)
            {
                Log.d("AAA", "onProgress: " + progress);
                mProgressBar.setProgress(progress);
            }
        });

        SimpleTarget<Drawable> simpleTarge = new SimpleTarget<Drawable>()
        {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition)
            {
                Log.d("AAA", "onResourceReady: ");

                mProgressBar.setVisibility(GONE);
                mImageView.setImageDrawable(resource);
                ProgressInterceptor.removeListener(url);
            }

            @Override
            public void onStart()
            {
                super.onStart();
                Log.d("AAA", "onStart: ");

                mProgressBar.setVisibility(VISIBLE);
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable)
            {
                super.onLoadFailed(errorDrawable);

                Log.d("AAA", "onFailed");

                mProgressBar.setVisibility(GONE);
            }
        };
        GlideApp.with(getContext())
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.NONE)  //图片存储策略（不存储，无论内存还是磁盘）
                .skipMemoryCache(true)                      //跳过内存缓存
                .into(simpleTarge);
    }

    public void setUrl(int resource)
    {
        mProgressBar.setVisibility(GONE);
        mImageView.setImageResource(resource);
    }
}
