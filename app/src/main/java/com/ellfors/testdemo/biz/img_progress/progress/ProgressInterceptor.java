package com.ellfors.testdemo.biz.img_progress.progress;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * ProgressInterceptor
 * 2019-07-04 17:28
 */
public class ProgressInterceptor implements Interceptor
{
    public static final Map<String, ProgressListener> LISTENER_MAP = new HashMap<>();

    public static void addListener(String url, ProgressListener listener)
    {
        LISTENER_MAP.put(url, listener);
    }

    public static void removeListener(String url)
    {
        LISTENER_MAP.remove(url);
    }

    @Override
    public Response intercept(Chain chain) throws IOException
    {
        Request request = chain.request();
        Response response = chain.proceed(request);
        String url = request.url().toString();
        ResponseBody body = response.body();
        return response.newBuilder().body(new ProgressResponseBody(url, body)).build();
    }
}