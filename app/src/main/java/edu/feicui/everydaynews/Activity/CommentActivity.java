package edu.feicui.everydaynews.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import edu.feicui.everydaynews.Adapter.CommentAdapter;
import edu.feicui.everydaynews.Entity.CommentInfo;
import edu.feicui.everydaynews.Entity.CommentItem;
import edu.feicui.everydaynews.Entity.CommentRelease;
import edu.feicui.everydaynews.Entity.News;
import edu.feicui.everydaynews.R;
import edu.feicui.everydaynews.View.SeverletUrl;
import edu.feicui.everydaynews.View.XXListView.XListView;
import edu.feicui.everydaynews.net.Constants;
import edu.feicui.everydaynews.net.MyHttp;
import edu.feicui.everydaynews.net.OnResuilFinishListener;
import edu.feicui.everydaynews.net.Response;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;

/**
 * 显示评论
 * Created by Administrator on 2016/10/25.
 */
public class CommentActivity extends BaseActivity implements XListView.IXListViewListener, View.OnClickListener {
    CommentInfo info;
    XListView mLvComment;
    CommentAdapter adapter;
    Intent intent;
    News news;
    int dir = 1;
    int nid = 1;
    Button mBtContent;
    ImageView mImgLeft;//左边的图片
    TextView mTvTittle;//标题的文字
    ImageView mImgRight;//右边的图片
    ImageView mIvCommentBack;//返回到新闻详情的按钮
    EditText mEtInput;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:

                    adapter.notifyDataSetChanged();
                    break;
                case 2:
                    adapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_activity);

    }

    /**
     * 用于子类初始化工作
     */
    @Override
    protected void initView() {
        intent = getIntent();
        news = (News) intent.getSerializableExtra("NEWS");
        mBtContent= (Button) findViewById(R.id.btn_send_content);
        mLvComment = (XListView) findViewById(R.id.lv_comment_content);
        mEtInput= (EditText) findViewById(R.id.et_comment_input);

        adapter = new CommentAdapter(this, null, R.layout.item_comment_activity);
        getHttp();
        mIvCommentBack= (ImageView) findViewById(R.id.iv_news_deatil_left_pic);
        mImgLeft=(ImageView) findViewById(R.id.iv_base_left);//左边的图片
        mImgRight=(ImageView) findViewById(R.id.iv_base_right);//右边的图片
        mTvTittle=(TextView) findViewById(R.id.tv_base_tittle);//标题的文字
        mImgLeft.setVisibility(View.GONE);
        mImgRight.setVisibility(View.GONE);
        mTvTittle.setText("评论");
        mIvCommentBack.setVisibility(View.VISIBLE);
        mIvCommentBack.setOnClickListener(this);
        mLvComment.setAdapter(adapter);
        mLvComment.setPullLoadEnable(true);//可以进行上啦加载
        mLvComment.setPullRefreshEnable(true);//可以进行下拉刷新
        mLvComment.setXListViewListener(this);//监听上啦加载下来刷新
        mBtContent.setOnClickListener(this);

    }

    public void getHttp() {
        Map<String, String> params = new HashMap<>();
        params.put("ver", "0000000");
        params.put("nid", news.nid + "");
        params.put("dir", "1");
        params.put("stamp", "20121221121212");
        params.put("cid", "1");
        params.put("cnt", "20");
        MyHttp.get(this, SeverletUrl.COMMENT_SHOW, params, new OnResuilFinishListener() {

            @Override
            public void success(Response response) {
                Gson gson = new Gson();
                info = gson.fromJson(response.resuit.toString(), CommentInfo.class);
                adapter.mList =info.data;
                handler.sendEmptyMessage(1);
            }

            @Override
            public void failed(Response response) {
                Toast.makeText(CommentActivity.this, "显示评论内容失败", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 发布评论
     */
    public void getHttpInput(){
    Map<String ,String> params=new HashMap<>();
        params.put("nid",news.nid+"");
        params.put("ver","0000000");
        params.put("token", Constants.TOKEN);
        params.put("imei","000000000000000");
        params.put("ctx",mEtInput.getText().toString());
        MyHttp.get(CommentActivity.this, SeverletUrl.COMMENT_RELEASE, params, new OnResuilFinishListener() {
            @Override
            public void success(Response response) {
                Gson gson=new Gson();
                CommentRelease release= gson.fromJson(response.resuit.toString(),CommentRelease.class);
                switch (release.data.result){
                    case Constants.ZERO:

                        Toast.makeText(CommentActivity.this,"评论已发送",Toast.LENGTH_SHORT).show();
                        handler.sendEmptyMessage(2);
                        break;
                    case Constants.ONE:
                        Toast.makeText(CommentActivity.this,"非法关键字",Toast.LENGTH_SHORT).show();
                        break;
                    case Constants.TWO:
                        Toast.makeText(CommentActivity.this,"禁止评论(政治敏感新闻)",Toast.LENGTH_SHORT).show();
                        break;
                    case Constants.THREE:
                        Toast.makeText(CommentActivity.this,"禁止评论(用户被禁言)",Toast.LENGTH_SHORT).show();
                        break;

                }
            }

            @Override
            public void failed(Response response) {
                Toast.makeText(CommentActivity.this,"发布评论失败",Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public void onRefresh() {
        //下拉刷新
        Toast.makeText(this, "onRefresh", Toast.LENGTH_SHORT).show();
        dir = 1;
        //清空之前
        adapter.mList.clear();
        getHttp();
    }

    @Override
    public void onLoadMore() {
        //上啦加载
        Toast.makeText(this, "onLoadMore", Toast.LENGTH_SHORT).show();
        dir = 2;
        //拿到最后一条的id
        if (adapter.mList.size() > 0) {
            CommentItem commentItem = adapter.mList.get(adapter.mList.size()-1);
            nid = news.nid;
        }
        getHttp();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_send_content:

                if(mEtInput.getText().toString()==null){
                    Toast.makeText(this, "请输入评论内容", Toast.LENGTH_SHORT).show();
                }else {//不为空发布评论
                    getHttpInput();
                    handler.sendEmptyMessage(2);
                }
            break;
            case R.id.iv_news_deatil_left_pic:
                finish();
        break;
    }
    }


}