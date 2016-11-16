package edu.feicui.everydaynews.Activity.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import edu.feicui.everydaynews.Activity.HomeActivity;
import edu.feicui.everydaynews.Activity.NewsDeatilActivity;
import edu.feicui.everydaynews.Adapter.NewsListAdapter;
import edu.feicui.everydaynews.Entity.News;
import edu.feicui.everydaynews.Entity.NewsArray;
import edu.feicui.everydaynews.Entity.NewsCat;
import edu.feicui.everydaynews.R;
import edu.feicui.everydaynews.View.SeverletUrl;
import edu.feicui.everydaynews.View.XXListView.XListView;
import edu.feicui.everydaynews.net.MyHttp;
import edu.feicui.everydaynews.net.OnResuilFinishListener;
import edu.feicui.everydaynews.net.Response;

/**
 * 刷新加载页面
 * Created by Administrator on 2016/10/8.
 */
public class NewsFragment extends Fragment implements XListView.IXListViewListener, AdapterView.OnItemClickListener {
    XListView mListView;
    View mView;
   FragmentActivity activity;
    NewsListAdapter mAdapter;
    int dir=1;
    int nid=1;
    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_news_list, container, false);
    }

    public View findViewById(int id) {
        return mView.findViewById(id);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mView = getView();
        activity = getActivity();
        mListView= (XListView) findViewById(R.id.lv_news_fragment);
        mAdapter = new NewsListAdapter(activity, null, R.layout.item_news);
        mListView.setAdapter(mAdapter);
        mListView.setPullLoadEnable(true);//可以进行上啦加载
        mListView.setPullRefreshEnable(true);//可以进行下拉刷新
        mListView.setXListViewListener(this);//监听上啦加载下来刷新
        getHttp(1);
        mListView.setOnItemClickListener(this);
    }



    /**
     * 获取新闻列表
     */
    public void getHttp(int type) {
        Map<String, String> params = new HashMap<>();
        params.put("ver", "0000000");//版本
        params.put("subid", type+"");//分类号
        params.put("dir", "1");
        params.put("nid", "1");
        params.put("stamp", "20140321000000");
        params.put("cnt", "20");

        MyHttp.get(activity, SeverletUrl.NEW_LIST,params, new OnResuilFinishListener() {

            @Override
             public void success(Response response) {
                Gson gson = new Gson();
                NewsArray array = gson.fromJson(response.resuit.toString(), NewsArray.class);
                if(array.data!=null&&array.data.size()!=0){
                    Log.e("zhangzhonglin", "SeverletUrl.NEW_LIST: "+SeverletUrl.NEW_LIST );
                    mAdapter.mList=array.data;
                    mAdapter.notifyDataSetChanged();
                }
                    if(dir==1){
                        Date date=new Date();
                        mListView.setRefreshTime(format.format(date));
                    }
                //关闭上拉加载以及下拉刷新框
                mListView.stopRefresh();
                mListView.stopLoadMore();

            }

            @Override
            public void failed(Response response) {
                Toast.makeText(activity, "加载失败", Toast.LENGTH_SHORT).show();
                //关闭上拉加载以及下拉刷新框
                mListView.stopRefresh();
                mListView.stopLoadMore();
            }
        });
    }

    @Override
    public void onRefresh() {
        Log.e("aac", "onRefresh: ---------可以上啦加载" );
        Toast.makeText(activity, "onRefresh", Toast.LENGTH_SHORT).show();
        //进行下拉刷新操作
        dir=1;
        //清空之前
        mAdapter.mList.clear();
        getHttp(1);
    }

    @Override
    public void onLoadMore() {
        Toast.makeText(activity, "onLoadMore", Toast.LENGTH_SHORT).show();
        //上拉加载
        dir=2;
        //拿到最后一条的id
        if(mAdapter.mList.size()>0){
            News news=mAdapter.mList.get(mAdapter.mList.size()-1);
            nid=news.nid;
        }
        getHttp(1);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(activity, NewsDeatilActivity.class);
        /**
         * 下一界面新闻详情通过webView显示
         * webView需要连接，链接是News类的一个成员变量（link）
         * 通过一个类的对象可以使用这个类的属性
         * 所以可以通过News的对象使用link
         * 数据源的集合中的每一个元素就是一个News的对象
         * 所以可以从数据源中获得对象
         */
        //把所点击子条目对应的对象传递给下一个界面
        intent.putExtra("NEWS",mAdapter.mList.get(position));

        startActivity(intent);
    }

}
