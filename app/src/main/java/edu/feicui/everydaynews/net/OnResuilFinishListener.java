package edu.feicui.everydaynews.net;

/**
 * Created by Administrator on 2016/9/22.
 */
public interface OnResuilFinishListener {
    void success(Response response);//网络请求成功
    void failed(Response response);//网络请求失败
}
