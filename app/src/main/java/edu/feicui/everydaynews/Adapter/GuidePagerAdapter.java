package edu.feicui.everydaynews.Adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/27.
 */
public class GuidePagerAdapter extends PagerAdapter {
    ArrayList<ImageView> mListView;
    public GuidePagerAdapter(ArrayList<ImageView> mListView){
            this.mListView=mListView;
    }


    @Override
    public int getCount() {

        return mListView==null?0:mListView.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView img=mListView.get(position%mListView.size());
        container.addView(img);
        return img;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
