package edu.feicui.everydaynews.Activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import edu.feicui.everydaynews.Entity.CommentRelease;
import edu.feicui.everydaynews.Entity.CommentTotal;
import edu.feicui.everydaynews.Entity.News;
import edu.feicui.everydaynews.R;
import edu.feicui.everydaynews.View.SeverletUrl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;

/**
 * 新闻列表二级页面
 * Created by Administrator on 2016/10/12.
 */
public class NewsDeatilActivity extends  BaseActivity implements View.OnClickListener {
    WebView mWebView;//新闻详情
    TextView mTextView;//tittle
    TextView mTvNewsDeatil;//跟帖数
    ImageView mIvDeatil;//收藏的点
    ImageView mRightPic;//tittle右边的图片
    ImageView mLeftPic;//tittle左边的图片
    ImageView mIvNewsLeftPic;//左边回退到上一级界面的箭头
    TextView mPwDeatil;
    PopupWindow popupWindow;
    LayoutInflater mInflater;


//    ImageView mImgLeft;//左边的图片
//    TextView mTvTittle;//标题的文字
//    ImageView mImgRight;//右边的图片
//    ImageView mIvCommentBack;//返回到新闻详情的按钮
    News news;

    CommentTotal commentTotal;

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    mTvNewsDeatil.setText("跟帖数:"+commentTotal.data+"");
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_deatil);
    }

    /**
     * 用于子类初始化工作
     */
    @Override
    protected void initView() {
        mInflater=getLayoutInflater();

        //findViewById（）此方法从setsetContentView（）加载的布局中寻找控件
        mWebView= (WebView) findViewById(R.id.web_news_deatil);
        mLeftPic= (ImageView) findViewById(R.id.iv_base_left);
        mTextView= (TextView) findViewById(R.id.tv_base_tittle);
        mIvDeatil= (ImageView) findViewById(R.id.iv__news_deatil_pic);
        mRightPic= (ImageView) findViewById(R.id.iv_base_right);
        mTvNewsDeatil= (TextView) findViewById(R.id.tv_news_deatil_text);//跟帖数
        mIvNewsLeftPic= (ImageView) findViewById(R.id.iv_news_deatil_left_pic);


        View view=mInflater.inflate(R.layout.popwindow_collect,null);
        mPwDeatil= (TextView)view.findViewById(R.id.tv_pw_collect);
        popupWindow=new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.dismiss();
        mTextView.setText("新闻详情");
        mLeftPic.setVisibility(View.GONE);
        mIvNewsLeftPic.setVisibility(View.VISIBLE);
        mTvNewsDeatil.setVisibility(View.VISIBLE);
        mRightPic.setVisibility(View.GONE);
        mTextView.setVisibility(View.VISIBLE );
        mIvDeatil.setVisibility(View.VISIBLE);



        mTvNewsDeatil.setOnClickListener(this);
        mPwDeatil.setOnClickListener(this);
        mIvNewsLeftPic.setOnClickListener(this);
        mIvDeatil.setOnClickListener(this);
        Intent intent=getIntent();
        //拿到上一界面传递过来的对象
         news = (News) intent.getSerializableExtra("NEWS");

        //1.加载连接
        mWebView.loadUrl(news.link);
        //添加设置
        WebSettings settings=mWebView.getSettings();
        //支持javaScript写的网页
        settings.setJavaScriptEnabled(true);

        settings.setUseWideViewPort(true);
        //可以按照任意比例进行缩放
        settings.setLoadWithOverviewMode(true);
        //支持缩放功能
        settings.setSupportZoom(true);
        //显示缩放视图
        settings.setBuiltInZoomControls(true);
        okHttp();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //1.返回键   2.可以返回
        if(keyCode==KeyEvent.KEYCODE_BACK&&mWebView.canGoBack()){
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
                //点击弹出加入收藏的弹框
            case R.id.iv__news_deatil_pic://popWand做弹框
                /**
                 *PopupWindow的使用
                 *
                 * 初始化一个PopupWindow
                 *点击popupWindow的弹框不会消失
                 *给popupWindow的弹框设置背景颜色 setBackgroundDrawable(new BitmapDrawable());
                 * 设置弹框的位置      showAsDropDown(mImageView,0,0);
                 * 第一个参数的意思是 基于展示的View   默认会出现在他的下方
                 * 第二个参数的意思是 弹框横向偏移的尺寸
                 * 第三个参数的意思是 弹框纵向偏移的尺寸
                 * showAtLocation(mImageView, Gravity.BOTTOM,0,0);
                 * 在这个view所在的窗口展示
                 * 第一个参数的意思是窗口上的任意控件都可以
                 *第二个参数的意思是相对于窗口显示的位置
                 *第三个参数的意思是相当于此位置的横向偏移尺寸
                 *第四个参数的意思是相对于此位置的纵向偏移尺寸
                 *这两个方法用任意一个都可以
                 */
                popupWindow.showAsDropDown(mIvDeatil,0,0);
//                popupWindow.showAtLocation(mIvNewsLeftPic, Gravity.BOTTOM,0,0);
            break;
            case R.id.iv_news_deatil_left_pic:
                Intent intent=new Intent(this,HomeActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_news_deatil_text://跟帖数
                Intent intent1=new Intent(this, CommentActivity.class);
                intent1.putExtra("NEWS",news);
                startActivity(intent1);
                break;
//            case R.id.iv_news_deatil_left_pic;
//                break;
        }
    }
    /**
     * WebView网络视图
     *      1.加载链接
     *      2.加载文件
     *      3.加载htm文本
     */
    /*
     *评论总数
     */
    public void okHttp(){
        SeverletUrl.NID = news.nid;
        Log.e(TAG, "NID: "+SeverletUrl.NID );
        //实例化一个okHttpClient的对象
        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                .connectTimeout(3000, TimeUnit.MILLISECONDS)
                .build();
        //创建一个请求
        Log.e(TAG, "SeverletUrl.COMMENT_TOTAL: "+SeverletUrl.COMMENT_TOTAL+SeverletUrl.NID );
        okhttp3.Request request=new okhttp3.Request.Builder()
                .url(SeverletUrl.COMMENT_TOTAL+SeverletUrl.NID)
                .get()
                .build();
        //加入请求
        Call call=okHttpClient.newCall(request);
        //执行请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(NewsDeatilActivity.this,"获取评论总数失败",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                ResponseBody boby=response.body();
                String str=boby.string();
                Gson gson=new Gson();
                commentTotal = gson.fromJson(str,CommentTotal.class);
                handler.sendEmptyMessage(0);

            }
        });
    }
}
