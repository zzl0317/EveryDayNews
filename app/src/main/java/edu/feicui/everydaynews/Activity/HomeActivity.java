package edu.feicui.everydaynews.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import edu.feicui.everydaynews.Activity.Fragment.CollectFragment;
import edu.feicui.everydaynews.Activity.Fragment.LoginFragment;
import edu.feicui.everydaynews.Activity.Fragment.NewsFragment;
import edu.feicui.everydaynews.Activity.Fragment.PassWordFragment;
import edu.feicui.everydaynews.Activity.Fragment.RegisterFragment;
import edu.feicui.everydaynews.Adapter.LifeListViewAdapter;
import edu.feicui.everydaynews.Adapter.NewsListAdapter;
import edu.feicui.everydaynews.Entity.LeftMenu;
import edu.feicui.everydaynews.Entity.NewsArray;
import edu.feicui.everydaynews.R;
import edu.feicui.everydaynews.View.XXListView.XListView;
import edu.feicui.everydaynews.net.MyHttp;
import edu.feicui.everydaynews.net.OnResuilFinishListener;
import edu.feicui.everydaynews.net.Response;
import edu.feicui.everydaynews.View.SeverletUrl;


/**
 * 主界面
 * Created by Administrator on 2016/9/29.
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {


    /**
     * 注意：
     * 主内容区的布局代码要放在侧滑菜单布局的前面,
     * 这可以帮助DrawerLayout判断谁是侧滑菜单，谁是主内容区:
     * 侧滑菜单的部分的布局（这里是ListView）可以设置layout_gravity属性，
     * 他表示侧滑菜单是在左边还是右边。
     */

    /**
     *思路是拿到tittle左右两个的ID  点击弹出 左右两边的侧拉
     */

    /**
     * 成员变量的声明
     */


    TextView mTvTittle;//tittle的文字
    ImageView mImgLeft;//左边的图片
    ImageView mImgRight;//右边的图片
    DrawerLayout drawerLayout;

    LinearLayout mLayout;
    TextView mTvWarNews;//军事
    TextView mTvSocietyNews;//社会
    ImageView mIvPic;//右边的箭头
    ListView mLvHomeLeft;//左边侧拉
    RelativeLayout mLvHomeRight;//右边侧拉

    NewsListAdapter mAdapter;//侧滑的适配器
    XListView mXListView;
    ArrayList<LeftMenu> mLeftMenuList;
    LifeListViewAdapter mLeftListViewAdapter;
    ImageView mIvRightPic;//右边登录图片
    TextView mTvRightText;//点击登录的按钮

    CollectFragment mCollectFragment;//收藏新闻
    PassWordFragment mPassWordFragment;//忘记密码
    LoginFragment mLoginFragment;//注册
    RegisterFragment mRegisterFragment;//登录
    NewsFragment mNewsFragment;//新闻列表
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    /**
     * 初始化操作
     */
    @Override
    protected void initView() {

        mIvRightPic= (ImageView) findViewById(R.id.iv_right_pic);//右边侧拉界面的图片
        mTvRightText= (TextView) findViewById(R.id.tv_right_text);//登录
        mLayout= (LinearLayout) findViewById(R.id.ll_dutum);//展示社会和军事的布局
        mTvWarNews= (TextView) findViewById(R.id.tv_home_war_news);//社会
        mTvSocietyNews= (TextView) findViewById(R.id.tv_home_society_news);//军事
        mIvPic= (ImageView) findViewById(R.id.iv_home_pic);//右边的箭头
        mLvHomeLeft= (ListView) findViewById(R.id.lv_home_left);//左边侧拉的界面
        mXListView = (XListView) findViewById(R.id.lv_news_fragment);//新闻的列表
        mLvHomeRight= (RelativeLayout) findViewById(R.id.tv_home_right);////右边的测啦界面
        int [] icon={R.mipmap.biz_navigation_tab_news,R.mipmap.biz_navigation_tab_read,
                R.mipmap.biz_navigation_tab_local_news,R.mipmap.biz_navigation_tab_ties,
                R.mipmap.biz_navigation_tab_pics};
        String [] data={"新闻","收藏    ","本地","跟帖","图片"};
        String [] datas={"NEWS","FAVORITE","LOCAL","COMMENT","PHOTO"};
        mLeftMenuList = new ArrayList<>();
        for (int i = 0; i <5 ; i++) {
            LeftMenu leftMenu=new LeftMenu();
            leftMenu.data=data[i];
            leftMenu.datas=datas[i];
            leftMenu.icon=icon[i];
            mLeftMenuList.add(leftMenu);
        }
        mCollectFragment=new CollectFragment();//收藏新闻
        mNewsFragment=new NewsFragment();//新闻列表
        mRegisterFragment=new RegisterFragment();//登录
        mLoginFragment=new LoginFragment();//注册
        mPassWordFragment=new PassWordFragment();//忘记密码
        //新闻列表的适配器
        mLeftListViewAdapter=new LifeListViewAdapter(this,mLeftMenuList,R.layout.left_activity);
        mAdapter=new NewsListAdapter(this,null,R.layout.item_news);
        mLvHomeLeft.setAdapter(mLeftListViewAdapter);

/*
----------------------------点击title左右两边的图标  *** 弹出左右两边的侧拉 页面----------------
 */
        mTvTittle= (TextView) findViewById(R.id.tv_base_tittle);
        mImgLeft= (ImageView) findViewById(R.id.iv_base_left);//tittle左边的图片
        mImgRight= (ImageView) findViewById(R.id.iv_base_right);//tittle右边的图片
        drawerLayout= (DrawerLayout) findViewById(R.id.dl_home_drawer_layout);//整体的布局

        /**
         * ---------------------------------------绑定点击事件操作---------------------------
         */
        mTvSocietyNews.setOnClickListener(this);//军事
        mTvWarNews.setOnClickListener(this);//社会
        mImgRight.setOnClickListener(this);//给tittle右边的图片绑定点击事件
        mImgLeft.setOnClickListener(this);//给tittle左边的图片绑定点击事件
        mIvRightPic.setOnClickListener(this);//右边侧拉界面的图片绑定点击事件
        mTvRightText.setOnClickListener(this);//给点击登录文字绑定点击事件
        mLvHomeLeft.setOnItemClickListener(this);
        mIvPic.setOnClickListener(this);//给右边箭头绑定点击事件

/**
 * ---------------------------------------------进行碎片的操作 显示和隐藏-------------------
 */


        /**
         * 获取FragmentManager的对象    getSupportFragmentManager
         *进行碎片处理FragmentTransaction  beginTransaction()
         */
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction transaction=fm.beginTransaction();
        /**
         * 通过transaction.add()
         * 将碎片添加Activity中  显示哪个Fragment
         */
        transaction.add(R.id.ll_new_content,mNewsFragment);//新闻列表
        transaction.add(R.id.ll_new_content,mRegisterFragment);//登录页面
        transaction.add(R.id.ll_new_content,mLoginFragment);//注册页面
        transaction.add(R.id.ll_new_content,mPassWordFragment);//忘记密码页面
        transaction.add(R.id.ll_new_content,mCollectFragment);//收藏新闻
        /**
         * 用hide和show来控制Fragment在Activity中的显示或隐藏
         */
        transaction.show(mNewsFragment);//显示新闻列表
        transaction.hide(mRegisterFragment);//隐藏登录页面
        transaction.hide(mLoginFragment);//隐藏注册页面
        transaction.hide(mPassWordFragment);//隐藏忘记密码页面
        transaction.hide(mCollectFragment);//隐藏收藏新闻页面
        transaction.commit();//提交


    }
    /**
     * Called when a view has been clicked.
     *-------------------------------------跳转事件操作---------------------------------
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_base_left://点击弹出左边  关闭右边
                drawerLayout.openDrawer(Gravity.LEFT);
                drawerLayout.closeDrawer(Gravity.RIGHT);
                break;
            case R.id.iv_base_right://点击弹出右边   关闭左边
                drawerLayout.openDrawer(Gravity.RIGHT);
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
            case R.id.iv_right_pic://右边侧拉界面的图片
                FragmentManager fm1=getSupportFragmentManager();
                FragmentTransaction transaction1=fm1.beginTransaction();
                transaction1.hide(mNewsFragment);
                transaction1.show(mRegisterFragment);
                transaction1.commit();
                drawerLayout.closeDrawer(Gravity.RIGHT);
                mTvTittle.setText("用户登录");
                mLayout.setVisibility(View.GONE);
                break;
            case R.id.tv_right_text://右边侧拉界面的文字
                FragmentManager fm2=getSupportFragmentManager();
                FragmentTransaction transaction2=fm2.beginTransaction();
                transaction2.hide(mRegisterFragment);
                transaction2.show(mRegisterFragment);
                transaction2.commit();
                drawerLayout.closeDrawer(Gravity.RIGHT);
                mTvTittle.setText("用户登录");
                mLayout.setVisibility(View.GONE);
//                Intent intent=new Intent(this,RegisterActivity.class);
//                startActivity(intent);
                break;
            case R.id.iv_home_pic:////新闻列表右边的箭头
                FragmentManager fm4=getSupportFragmentManager();
                FragmentTransaction transaction4=fm4.beginTransaction();
                transaction4.show(mCollectFragment);
                transaction4.hide(mNewsFragment);
                transaction4.commit();
                mTvTittle.setText("分类");
//                mLayout.setVisibility(View.GONE);
                break;
            case R.id.tv_home_war_news://军事
                mNewsFragment.getHttp(1);
                break;

            case R.id.tv_home_society_news://社会
                mNewsFragment.getHttp(2);
                break;

        }
    }

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p/>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that was clicked.
     *
     *点击listView的
     *
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                drawerLayout.closeDrawer(Gravity.LEFT);
                FragmentManager fm3=getSupportFragmentManager();
                FragmentTransaction transaction3=fm3.beginTransaction();
                transaction3.hide(mRegisterFragment);
                transaction3.show(mNewsFragment);
                transaction3.commit();
                mTvTittle.setText("资讯");
                break;
            case 1:
                drawerLayout.closeDrawer(Gravity.LEFT);
                FragmentManager fm4=getSupportFragmentManager();
                FragmentTransaction transaction4=fm4.beginTransaction();
                transaction4.hide(mNewsFragment);
                transaction4.show(mCollectFragment);
                transaction4.commit();
                mTvTittle.setText("收藏新闻");
                mLayout.setVisibility(View.GONE);

                break;
            case 2:
                Toast.makeText(this,"LOCAL",Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(this,"COMMENT",Toast.LENGTH_SHORT).show();
                break;
            case 4:
                Toast.makeText(this,"PHOTO",Toast.LENGTH_SHORT).show();
                break;

        }
    }
    public void RepaceFragment(int flag){
        switch (flag){
            case 0://注册
                FragmentManager fragment=getSupportFragmentManager();
                FragmentTransaction transaction=fragment.beginTransaction();
                transaction.show(mLoginFragment);
                transaction.hide(mCollectFragment);
                transaction.hide(mNewsFragment);
                transaction.hide(mPassWordFragment);
                transaction.hide(mRegisterFragment);
                transaction.commit();
                break;
            case 1://忘记密码
                FragmentManager fragment1=getSupportFragmentManager();
                FragmentTransaction transaction1=fragment1.beginTransaction();
                transaction1.show(mPassWordFragment);
                transaction1.hide(mCollectFragment);
                transaction1.hide(mNewsFragment);
                transaction1.hide(mLoginFragment);
                transaction1.hide(mRegisterFragment);
                transaction1.commit();
                break;
            case 2://登录
                FragmentManager fragment2=getSupportFragmentManager();
                FragmentTransaction transaction2=fragment2.beginTransaction();
                transaction2.show(mRegisterFragment);
                transaction2.hide(mCollectFragment);
                transaction2.hide(mNewsFragment);
                transaction2.hide(mLoginFragment);
                transaction2.hide(mPassWordFragment);
                transaction2.commit();
                break;
        }
    }
}