package edu.feicui.everydaynews.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/26.
 * 封装的BaseAdapter
 * 简化BaseAdapter的使用步骤，节省时间
 */
public abstract class MyBaesAdapter<T> extends BaseAdapter{
    Context mContext;
   public ArrayList<T> mList;
    LayoutInflater mInflate;
    int mLayoutId;
    public MyBaesAdapter( Context mContext,ArrayList<T> mList,  int mLayoutId){
        this.mContext=mContext;
        this.mList=mList;
        this.mLayoutId=mLayoutId;
        mInflate= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (this.mList == null) {
            this.mList=new ArrayList<>();
        }
    }
    @Override
    public int getCount() {
        return mList==null?0:mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if(convertView==null){
            convertView=mInflate.inflate(mLayoutId,null);
            holder=new Holder();
            convertView.setTag(holder);
        }else {
            holder= (Holder) convertView.getTag();
        }
        putView(holder,convertView,position,mList.get(position));
        return convertView;
    }

    /**
     *
     * @param holder    对应条目的holder
     * @param convertView   对应条目的view
     * @param position      对应条目的位置
     * @param t
     */
    public abstract void putView(Holder holder, View convertView, int position,T t);

    class Holder{

    }
}
