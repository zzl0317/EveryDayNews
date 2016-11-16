package edu.feicui.everydaynews.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import edu.feicui.everydaynews.Entity.News;
import edu.feicui.everydaynews.R;

/**
 * Created by Administrator on 2016/10/8.
 */
public class NewsListAdapter extends MyBaesAdapter<News>{

    public NewsListAdapter(Context mContext, ArrayList mList, int mLayoutId) {
        super(mContext, mList, mLayoutId);
    }


    @Override
    public void putView(Holder holder, View convertView, int position, News news) {
        TextView title= (TextView) convertView.findViewById(R.id.tv_item_home_news_title);
        title.setText(news.title);
        ImageView img= (ImageView) convertView.findViewById(R.id.iv_item_home_news_pic);
        Glide.with(mContext).load(news.icon).into(img);
    }
}
