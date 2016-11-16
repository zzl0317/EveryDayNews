package edu.feicui.everydaynews.net;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import edu.feicui.everydaynews.View.SeverletUrl;
import okhttp3.*;

/**
 * 网络请求类
 * Created by Administrator on 2016/9/22.
 */
public class MyHttp {
    public static void get(Context context,String url, Map<String , String> prams,
                           OnResuilFinishListener mListener){

        //进行网络请求
        Request request=new Request();
        request.params=prams;
        request.type=Constants.GET;
        request.url=url+Utils.getUrl(prams,Constants.GET);

        //url 拼接好的    type   get   params   ------------Map参数
        //请求
        NetAsync async=new NetAsync(context,mListener);
        async.execute(request);
    }

   public static void post(Context context,String url, Map<String , String> prams,
                           OnResuilFinishListener mListener){
       Request request=new Request();
       request.params=prams;
       request.type=Constants.POST;
       request.url=url;
       NetAsync async=new NetAsync(context,mListener);
       async.execute(request);
   }
    /**
     * OkHttp请求框架
     */
    public void getHttp(){
        //实例化OkHttpClient的对象
        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                .connectTimeout(3000, TimeUnit.MILLISECONDS)
                .build();
        //新建一个请求
        okhttp3.Request request=new okhttp3.Request.Builder()
                .url(SeverletUrl.TOKEN_REGISTER)
                .get()
                .build();
        //加入一个请求
        Call call=okHttpClient.newCall(request);
        //执行请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("aac", "onFailure: 失败" );
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                ResponseBody boby=response.body();
                String str=boby.string();

            }
        });

    }
    public void post(){
        //1.实例化
        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                .connectTimeout(3000, TimeUnit.MILLISECONDS)
                .build();
        //2.设置请求方式
        FormBody boby=new FormBody.Builder()
                .add("size","10")
                .build();
        okhttp3.Request req=new okhttp3.Request.Builder()
                .url("http://www.wycode.cn/qpi/movie/getMovies")
                .post(boby)
                .build();
        Call call=okHttpClient.newCall(req);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                ResponseBody boby=response.body();
                String str=boby.string();
                Log.e("aac", "onResponse: "+str );
            }
        });

    }

}
