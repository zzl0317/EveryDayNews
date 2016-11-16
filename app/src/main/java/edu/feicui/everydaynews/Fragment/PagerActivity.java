package edu.feicui.everydaynews.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

import edu.feicui.everydaynews.Adapter.HomeAdapter;
import edu.feicui.everydaynews.R;

/**
 * Created by Administrator on 2016/9/28.
 */
public class PagerActivity extends FragmentActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {
    ViewPager mViewPager;
    ArrayList<Fragment> mList=new ArrayList<>();
    RadioGroup mRadioGroup;
    RadioButton mRadioButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

    }

    /**
     * 功能实现  左右滑动或者点下边的按钮 图片跟着按钮变动(例子   微信左右滑动)
     * 用到的知识点:
     * FragmentActivity
     * ViewPager  FragmentPagerAdapter
     * RadioGroup 单组选框
     *RadioButton 单选按钮
     * mRadioButton.setChecked(true);默认
     * 1.新建Activity   继承Fragment
     *         在xml文件中 用到控件RadioGroup  在它里面写单选按钮(RadioButton)
     *         图片选择器
     *         取消选框的圆圈  android:button="@null"
     *
     *
     *
     */
    @Override
    public void onContentChanged() {
        //初始化
        super.onContentChanged();
        mViewPager= (ViewPager) findViewById(R.id.vp_pager);
        mRadioGroup= (RadioGroup) findViewById(R.id.rg_group);
        mRadioButton= (RadioButton) findViewById(R.id.rb_one_button);
        mRadioButton.setChecked(true);
        //数据源
        mList.add(new OneFragment());
        mList.add(new TwoFragment());
        mList.add(new ThreeFragment());
        //适配器
        HomeAdapter adapter=new HomeAdapter(getSupportFragmentManager(),mList);
        mViewPager.setAdapter(adapter);
        //绑定点击事件
        mViewPager.addOnPageChangeListener(this);
        mRadioGroup.setOnCheckedChangeListener(this);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        RadioButton btn= (RadioButton) mRadioGroup.getChildAt(position);
        btn.setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        for (int i = 0; i <group.getChildCount() ; i++) {
                RadioButton btn= (RadioButton) group.getChildAt(i);
            if(btn.getId()==checkedId){
                mViewPager.setCurrentItem(i);
            }
        }
    }


}
