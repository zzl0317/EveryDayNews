package edu.feicui.everydaynews.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import edu.feicui.everydaynews.Activity.Fragment.NewsFragment;
import edu.feicui.everydaynews.R;

/**
 * Created by Administrator on 2016/10/28.
 */
public class TextActivity extends FragmentActivity{
    NewsFragment fragment;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_main);
        init();
        fragment=new NewsFragment();
    }
    void init(){
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction transaction=fm.beginTransaction();
        transaction.add(R.id.ll_text_fragment,fragment);
        transaction.show(fragment);
        transaction.commit();
    }
}
