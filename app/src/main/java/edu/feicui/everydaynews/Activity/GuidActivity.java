package edu.feicui.everydaynews.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import edu.feicui.everydaynews.Adapter.GuidePagerAdapter;
import edu.feicui.everydaynews.R;

/**
 * 引导页面
 */
public class GuidActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    //图片资源
    public int[] mPicture = {R.mipmap.welcome, R.mipmap.wy, R.mipmap.small};
    ArrayList<ImageView> mList;//图片集合
    ImageView[] mArray = new ImageView[3];

    LinearLayout mLayout;//标题的布局

    Button mBtnPass;//跳过按钮

    /**
     * 引导页面
     * SharedPrefrence文件名
     */
    String PREFRENCE_NAME = "prefrence_settings";
    SharedPreferences preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guid);
        if(!getData()){//没有引导页
            Intent intent=new Intent(this, AnimationActivity.class);
            startActivity(intent);
            finish();
        }

    }

    //操作SharedPreferences数据
    private boolean getData() {
        /*
         *PREFRENCE_NAME  文件名
         * MODE_PRIVATE   权限
         */
        preference = getSharedPreferences(PREFRENCE_NAME, MODE_PRIVATE);
        //所要读的数据对应的key    默认值(数据不存在)
        boolean isFirst = preference.getBoolean("is_first", true);
        return isFirst;
    }

    /**
     *初始化
     */
    @Override
    protected void initView() {
        mLayout= (LinearLayout) findViewById(R.id.ll_base);
        mLayout.setVisibility(View.GONE);

        ViewPager viewPager = (ViewPager) findViewById(R.id.vp_guid);
        mBtnPass = (Button) findViewById(R.id.btn_guid_pass);

        ArrayList<ImageView> listImg = new ArrayList<>();
        for (int i = 0; i < mPicture.length; i++) {
            ImageView img = new ImageView(this);
            img.setImageResource(mPicture[i]);
            listImg.add(img);
        }


        GuidePagerAdapter pagerAdapter = new GuidePagerAdapter(listImg);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(this);
        mBtnPass.setOnClickListener(this);
    }
    /*
     *当页面滑动状态改变
     *  1 ： 滑动
     *  2 ： 弹性运动
     *  0 ： 停止
     */
    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * @param position                所滑动页面的下标
     * @param positionOffset         所滑动页面的偏移百分比【0，1）
     * @param positionOffsetPixels  所滑动页面的像素
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (ImageView iv : mArray) {
            mBtnPass.setVisibility(View.GONE);

        }
        if (position == 2) {
            mBtnPass.setVisibility(View.VISIBLE);

            mBtnPass.setOnClickListener(this);
        }


    }


    @Override
    public void onClick(View v) {
        //编辑器

        SharedPreferences.Editor edt = preference.edit();
        edt.putBoolean("is_first", false);
        edt.commit();
        Intent intent=new Intent(this,AnimationActivity.class);
        startActivity(intent);
        finish();
        getPreferences(0);
        //拿到SharedPreference的对象
        getSharedPreferences(getLocalClassName(), 0);
    }
}
