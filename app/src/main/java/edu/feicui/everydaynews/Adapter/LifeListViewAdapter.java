package edu.feicui.everydaynews.Adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import edu.feicui.everydaynews.Entity.LeftMenu;
import edu.feicui.everydaynews.R;

/**
 * Created by Administrator on 2016/10/10.
 */
public class LifeListViewAdapter extends MyBaesAdapter<LeftMenu>{
    public LifeListViewAdapter(Context mContext, ArrayList<LeftMenu> mList, int mLayoutId) {
        super(mContext, mList, mLayoutId);
    }

    /**
     * @param holder      对应条目的holder
     * @param convertView 对应条目的view
     * @param position    对应条目的位置
     * @param leftMenu
     */
    @Override
    public void putView(Holder holder, View convertView, int position, LeftMenu leftMenu) {
        ImageView mLeftPic= (ImageView) convertView.findViewById(R.id.iv_left_pic);
        TextView mLeftText= (TextView) convertView.findViewById(R.id.tv_left_text);
        TextView mLeftTexts= (TextView) convertView.findViewById(R.id.tv_left_texts);
        Glide.with(mContext).load(leftMenu.icon).into(mLeftPic);
        mLeftText.setText(leftMenu.data);
        mLeftTexts.setText(leftMenu.datas);
    }
}
