package edu.feicui.everydaynews.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/28.
 */
public class HomeAdapter extends FragmentPagerAdapter{
    ArrayList<Fragment> mList;
    public HomeAdapter(FragmentManager fm,ArrayList<Fragment> mList) {
        super(fm);
        this.mList=mList;
    }

    @Override
    public Fragment getItem(int position) {

        return mList.get(position);
    }

    @Override
    public int getCount() {

        return mList==null?0:mList.size();
    }
}
