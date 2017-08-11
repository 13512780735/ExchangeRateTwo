package com.likeit.currenciesapp.utils;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2016/5/21 0021.
 *
 * @Author CaiWF
 * @Email 401885064@qq.com
 * @TODO OkHttp请求工具类
 */
public class OkHttpUtils {
    private static OkHttpClient okHttpClient = new OkHttpClient();

    public static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType type = MediaType.parse("text/html;charset=utf-8");

    /**
     * get请求，不带参数
     *
     * @param url      请求地址
     * @param callback 回调监听
     * @param params   参数
     */
    public static void get(String url, Callback callback, Map<String, String> params) {
        url = url + "?" + toGetParams(params);
        Logger.e("get请求 url :" + url);
        Request request = new Request.Builder().url(url).get().build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    /**
     * post请求，带参数
     *
     * @param url      请求地址
     * @param callback 回调监听
     * @param params   参数
     */
    public static void post(String url, Callback callback, Map<String, String> params) {

        Request request = new Request.Builder().url(url).post(requestBody(params)).build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    /**
     * post请求，不带参数
     *
     * @param url         请求地址
     * @param callback    回调监听
     * @param requestBody 参数
     */
    public static void post(String url, Callback callback, RequestBody requestBody) {
        Request request = new Request.Builder().url(url).post(requestBody).build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    /**
     * post请求，带参数
     *
     * @param url         请求地址
     * @param callback    回调监听
     * @param params 参数
     */
    public static void post(String url, Callback callback,File file, Map<String, String> params) {
        Request request=getFileRequest(url,file,params);
        okHttpClient.newCall(request).enqueue(callback);
    }


    public static Request getFileRequest(String url,File file, Map<String, String> maps) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (maps == null) {
            builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"file\";filename=\"file.jpg\""), RequestBody.create(MediaType.parse("image/png"), file)
            ).build();

        } else {
            for (String key : maps.keySet()) {
                builder.addFormDataPart(key, maps.get(key));
            }
            builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"file\";filename=\"file.jpg\""), RequestBody.create(MediaType.parse("image/png"), file)
            );

        }
        RequestBody body = builder.build();
        return new Request.Builder().url(url).post(body).build();

    }

    /**
     * map 转 RequestBody
     *
     * @param params
     * @return
     */
    public static RequestBody requestBody(Map<String, String> params) {
        StringBuffer stringBuffer = new StringBuffer();
        Iterator<Map.Entry<String, String>> it = params.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            stringBuffer.append(entry.getKey() + "=" + entry.getValue());

            if (it.hasNext()) {
                stringBuffer.append("&");
            }
        }
        Logger.e(stringBuffer.toString());
        return RequestBody.create(type, stringBuffer.toString());
    }

    /**
     * map 转 RequestParams
     *
     * @param params
     * @return
     */
    public static String toGetParams(Map<String, String> params) {
        StringBuffer stringBuffer = new StringBuffer();
        Iterator<Map.Entry<String, String>> it = params.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            stringBuffer.append(entry.getKey() + "=" + entry.getValue());
            if (it.hasNext()) {
                stringBuffer.append("&");
            }
        }
        return stringBuffer.toString();
    }

}
