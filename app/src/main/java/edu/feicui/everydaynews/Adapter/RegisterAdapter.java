package edu.feicui.everydaynews.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.feicui.everydaynews.Entity.ItemInfo;
import edu.feicui.everydaynews.Entity.Loginlog;
import edu.feicui.everydaynews.R;

/**
 * 用户中心的适配器
 * Created by Administrator on 2016/10/17.
 */
public class RegisterAdapter extends MyBaesAdapter<Loginlog>  {


    public RegisterAdapter(Context mContext, ArrayList<Loginlog> mList, int mLayoutId) {
        super(mContext, mList, mLayoutId);
    }

    /**
     * @param holder      对应条目的holder
     * @param convertView 对应条目的view
     * @param position    对应条目的位置
     * @param loginlog
     */
    @Override
    public void putView(Holder holder, View convertView, int position, Loginlog loginlog) {
        TextView mTvItemAddress= (TextView) convertView.findViewById(R.id.tv_user_recycler_address);
        mTvItemAddress.setText(loginlog.address);
        TextView mTvItemDevice= (TextView) convertView.findViewById(R.id.tv_user_recycler_device);
        mTvItemDevice.setText(loginlog.device==0?mContext.getResources().getString(R.string.phone)
                :mContext.getResources().getString(R.string.pc));
        TextView mTvItemTime= (TextView) convertView.findViewById(R.id.tv_user_recycler_time);
        mTvItemTime.setText(loginlog.time);

    }


}


