package com.ellfors.testdemo.biz.img_progress.options;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.ellfors.testdemo.biz.img_progress.progress.ProgressInterceptor;

import java.io.InputStream;

import okhttp3.OkHttpClient;

/**
 * MyGlideModule
 * 2019-07-04 17:54
 */
@GlideModule
public class MyGlideModule extends AppGlideModule
{
    public MyGlideModule()
    {
        super();
    }

    @Override
    public boolean isManifestParsingEnabled()
    {
//        return super.isManifestParsingEnabled();
        return false;
    }

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder)
    {
        super.applyOptions(context, builder);
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry)
    {
//        super.registerComponents(context,glide,registry);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new ProgressInterceptor())
                .build();

        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(okHttpClient));
    }
}