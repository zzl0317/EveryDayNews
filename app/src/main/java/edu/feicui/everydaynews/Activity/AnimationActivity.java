package edu.feicui.everydaynews.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

import edu.feicui.everydaynews.R;

/**
 * 加载页面
 * Created by Administrator on 2016/9/29.
 */
public class  AnimationActivity extends BaseActivity{
    ImageView mIvShow;
    LinearLayout mLayout;//标题的布局
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
    }

    @Override
    protected void initView() {
        mLayout= (LinearLayout) findViewById(R.id.ll_base);
        mLayout.setVisibility(View.GONE);
        mIvShow= (ImageView) findViewById(R.id.iv_animation);
        /**
         * 补间动画的使用
         *两种方式
         * 1.在代码中设置渐变时间 重复次数   动画重复模式   间隔时间    开始动画
         * 2.在xml文件中使用   res下建---->anim文件中---->建alpha_pic----->alpha
         *  在alpha中设置  渐变时间  重复次数   动画重复模式   间隔时间
         *  然后在代码中   开始动画startAnimation(AnimationUtils.loadAnimation(this,R.anim.alpha_pic)
         */
        mIvShow.startAnimation(AnimationUtils.loadAnimation(this,R.anim.alpha_pic));
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent=new Intent(AnimationActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);


    }
}
