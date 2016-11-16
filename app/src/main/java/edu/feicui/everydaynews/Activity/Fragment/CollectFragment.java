package edu.feicui.everydaynews.Activity.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.feicui.everydaynews.R;

/**
 * 收藏
 * Created by Administrator on 2016/10/12.
 */
public class CollectFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.collect_fragment,container,false);
    }
}
