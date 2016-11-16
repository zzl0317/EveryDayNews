package edu.feicui.everydaynews.Activity;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import edu.feicui.everydaynews.R;

/**
 * Activity的基类
 */
public abstract class BaseActivity extends FragmentActivity {
	//获取当前Activity的类名 getSimpleName() getName();
	public  String TAG=this.getClass().getSimpleName();
	
	LinearLayout mRelContent;//内容
	
	ImageView mImgLeft;//左边的图片
	TextView mTvTittle;//标题的文字
	ImageView mImgRight;//右边的图片
	
	LayoutInflater mInflate;
	/**
	 * 1.base的onCreate   加载了 布局base_layout
	 * 2.强传入的布局  加载到  base_layout的mRelContent 里面
	 */
	
	
	  @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.base_activity);
		mRelContent=(LinearLayout) findViewById(R.id.rl_base_content);
		mImgLeft=(ImageView) findViewById(R.id.iv_base_left);
		mImgRight=(ImageView) findViewById(R.id.iv_base_right);
		mTvTittle=(TextView) findViewById(R.id.tv_base_tittle);
		mInflate=getLayoutInflater();
		
	  }
	  
	  @Override
	protected void onStart() {
		super.onStart();
		  Log.e(TAG, "onStart: " );
	}
	  @Override
	protected void onResume() {
		super.onResume();
		  Log.e(TAG, "onResume: " );
	}
	  @Override
	protected void onPause() {
		super.onPause();
		  Log.e(TAG, "onPause: " );
	}
	  
	  @Override
	protected void onRestart() {
		super.onRestart();
		  Log.e(TAG, "onRestart: " );
	}
	  
	@Override
	protected void onStop() {
		super.onStop();
		Log.e(TAG, "onStop: " );
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.e(TAG, "onDestroy: " );
	}
	  
	 
	  	  
	  /**
	   * 模板设计  在加载布局之后 自动调用 initView();
	   */
	  public void setContentView(int id){

		  mInflate.inflate(id, mRelContent);  
		  initView();//
	  }
	  /**
	   *  用于子类初始化工作
	   */
	  protected abstract void initView();
	
	
	
	
	
	/**
	 * 
	 * @param listener   绑定的监听器
	 * @param views    所要绑定点击事件的的View
	 */
    protected void setOnClickListeners(OnClickListener
			   listener,View ...views){
		//给每一个View绑定点击事件
		   if(listener==null){
			   return;
		   }
		   for (View view : views) {
			   view.setOnClickListener(listener);
		     }
	   }
     /**
     * 
     * @param listener  绑定的监听器
     * @param ids    所要绑定点击事件的View的id
     */
    protected void setOnClickListeners(OnClickListener listener,
			   int ...ids){
		//给每一个View绑定点击事件
		   if(listener==null){
			   return;
		   }
		   for (int id : ids) {
			   findViewById(id).setOnClickListener(listener);
		     }
		   TextView tv=new TextView(this);
		   
	   }

}
