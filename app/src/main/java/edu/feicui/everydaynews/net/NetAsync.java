package edu.feicui.everydaynews.net;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2016/9/22.
 */
public class NetAsync extends AsyncTask<Request,Object,Response>{
    ProgressDialog mDialog;

    OnResuilFinishListener mListener;
    public NetAsync(Context context , OnResuilFinishListener mListener){
        mDialog=ProgressDialog.show(context,"","加载中...");
        this.mListener=mListener;
    }

    @Override
    protected Response doInBackground(Request... params) {
       Request request=params[0];
        Response response=new Response();
        HttpURLConnection httpconnection=null;
        try {
            //创建一个URL的对象
            URL url=new URL(request.url);
            //调用URL的openConnection()方法,获取HttpURLConnection对象
            httpconnection= (HttpURLConnection) url.openConnection();
            //设置读取超时为5秒
            httpconnection.setConnectTimeout(Constants.CONNECT_TIMEOUT);
            //设置响应时间为5秒
            httpconnection.setConnectTimeout(Constants.READ_TIMEOUT);
            if(request.type==Constants.GET){//请求成功是get
                httpconnection.setRequestMethod("GET");
            }else {//请求失败是POST
                httpconnection.setRequestMethod("POST");
                //设置此方法，允许向服务器输出内容
                httpconnection.setDoOutput(true);
                //获得一个输出流 向服务器写数据   默认情况下系统不允许向服务器输出内容
                OutputStream ou=httpconnection.getOutputStream();
                //获得一个输出流  ，向服务器写数据
                ou.write(Utils.getUrl(request.params,Constants.POST).getBytes());
            }
            //拿到返回的结果
            int code=httpconnection.getResponseCode();
            response.code=code;
            if(code==HttpURLConnection.HTTP_OK){//请求成功
                InputStream in=httpconnection.getInputStream();
                byte[] bytes=new byte[1024];
                int len;
                StringBuffer buff=new StringBuffer();
                while ((len=in.read(bytes))!=-1){
                    buff.append(new String(bytes,0,len));
                }
                //拿到了结果
                response.resuit=buff.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (httpconnection!=null){//关闭链接
                httpconnection.disconnect();//
            }
        }


        return response;
    }

    @Override
    protected void onPostExecute(Response o){
        super.onPostExecute(o);
        //拿到结果
        mDialog.dismiss();
        Response response=o;
        if(o.code!=HttpURLConnection.HTTP_OK){//失败
            mListener.failed(response);
        }else {//成功
            mListener.success(response);
        }
    }
}
