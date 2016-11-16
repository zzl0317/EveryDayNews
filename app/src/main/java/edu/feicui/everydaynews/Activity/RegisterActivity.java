package edu.feicui.everydaynews.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import edu.feicui.everydaynews.Activity.Fragment.RegisterFragment;
import edu.feicui.everydaynews.Adapter.RegisterAdapter;
import edu.feicui.everydaynews.Entity.ItemInfo;
import edu.feicui.everydaynews.Entity.LoginInfo;
import edu.feicui.everydaynews.Entity.UserCenterInfo;
import edu.feicui.everydaynews.Manifest;
import edu.feicui.everydaynews.R;
import edu.feicui.everydaynews.View.SeverletUrl;
import edu.feicui.everydaynews.db.MySQLiteOpenHelper;
import edu.feicui.everydaynews.db.SQLiteOpenHelperUtil;
import edu.feicui.everydaynews.net.Constants;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;



/**
 * 用户中心
 * Created by Administrator on 2016/10/17.
 */
public class  RegisterActivity extends BaseActivity implements View.OnClickListener {

    ImageView mIvRegisterPic;
    PopupWindow pop;
    LayoutInflater inflater;
    RegisterAdapter adapter;

    LinearLayout mLlCamera;//相机
    LinearLayout mLlMapDepot;//图库
    ListView mLvContent;//登录日志内容
    Button mBtnRegister;
    LinearLayout mllBaseTittle;
    ImageView mIvBack;
    ImageView mIvLeft;
    ImageView mIvDelete;
    TextView mTvUserName;
    LinearLayout mLLUser;
    RegisterFragment mRegisterFragment;//登录
    EditText mEtAccount;//账号
    ItemInfo info;
    SQLiteDatabase database;

    public static final int PERMISSION_GRANTED=201;
    /**
     * 点击返回按钮给登录页面返回的结果码
     */
    Log log;
    Handler hander=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:

                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        SQLiteOpenHelperUtil util=new SQLiteOpenHelperUtil(this);
        util.insert();
    }

    /**
     * 用于子类初始化工作
     */
    @Override
    protected void initView() {
        Intent intent = getIntent();
        String str=intent.getStringExtra("account");

        inflater=getLayoutInflater();
          View view1=inflater.inflate(R.layout.activity_register,null);
        mEtAccount= (EditText) view1.findViewById(R.id.et_register_user);//用户名的输入框
        mTvUserName= (TextView) findViewById(R.id.tv_user_register);//用户名
        mTvUserName.setText(str);
        mLLUser= (LinearLayout) findViewById(R.id.ll_user);

        View view=inflater.inflate(R.layout.popupwindow_register,null);
        mIvRegisterPic= (ImageView) findViewById(R.id.iv_register_pic);//头像
        mLlCamera= (LinearLayout) view.findViewById(R.id.ll_register_camera);//相机
        mLlMapDepot= (LinearLayout) view.findViewById(R.id.ll_register_map_depot);//图库
        mIvLeft= (ImageView) findViewById(R.id.iv_base_left);
        mIvBack= (ImageView) findViewById(R.id.iv_news_deatil_left_pic);//返回键
        mIvDelete= (ImageView) findViewById(R.id.iv_base_right);

        mllBaseTittle= (LinearLayout) findViewById(R.id.ll_base_home);//标题
        mBtnRegister= (Button) findViewById(R.id.btn_register_qult);
        pop=new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        pop.setOutsideTouchable(true);
        pop.setBackgroundDrawable(new BitmapDrawable());
        mIvRegisterPic.setOnClickListener(this);

        mLvContent= (ListView) findViewById(R.id.lv_user_register);

        adapter=new RegisterAdapter(this,null,R.layout.item_user_recycler_view);
        mLvContent.setAdapter(adapter);

        getHttp();
        SQLiteOpenHelper helper=new MySQLiteOpenHelper(this);
        database=helper.getWritableDatabase();

        mIvLeft.setVisibility(View.GONE);
        mIvDelete.setVisibility(View.GONE);
        mIvBack.setVisibility(View.VISIBLE);
        mBtnRegister.setOnClickListener(this);
        mLlCamera.setOnClickListener(this);
        mLlMapDepot.setOnClickListener(this);
        mIvBack.setOnClickListener(this);
        mTvUserName.setOnClickListener(this);

        mRegisterFragment=new RegisterFragment();
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction transaction=fm.beginTransaction();
        transaction.add(R.id.ll_user,mRegisterFragment);
        transaction.show(mRegisterFragment);


    }

    /**
     * OkHttp请求框架
     */
    public void getHttp(){
        //实例化OkHttpClient的对象
        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                .connectTimeout(3000, TimeUnit.MILLISECONDS)
                .build();
        //新建一个请求
        okhttp3.Request request=new okhttp3.Request.Builder()
                .url(SeverletUrl.TOKEN_REGISTER)
                .get()
                .build();
        //加入一个请求
        Call call=okHttpClient.newCall(request);

        //执行请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("aac", "onFailure: 失败" );
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                ResponseBody boby=response.body();
                String str=boby.string();
                Gson gson=new Gson();
               info=gson.fromJson(str, ItemInfo.class);
                adapter.mList=info.data.loginlog;
                hander.sendEmptyMessage(0);
            }
        });

    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_register_pic://头像
                pop.showAtLocation(mIvRegisterPic, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.ll_register_camera://相机
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//版本是否大于API23
                    if (checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                            checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        getToCamera();
                    } else {
                        requestPermissions(new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_GRANTED);
                    }
                } else {
                    getToCamera();
                }
                break;
            case R.id.ll_register_map_depot://图库
                mapdepot();
                break;
            case R.id.btn_register_qult://退出登录按钮   跳回主界面
                String sql = "delete from user";
                database.execSQL(sql);
                finish();
                break;
            case R.id.iv_news_deatil_left_pic://返回到主界面
                Intent intent2 = new Intent(this, HomeActivity.class);
                intent2.putExtra("BACK_CODE", UserCenterInfo.data.uid );
                startActivity(intent2);
                finish();
                break;
            case 0:
                mapdepot();
                break;
        }
    }
    public void mapdepot(){
        Intent intent4 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent4, Constants.GOTO_PICK);


    }

/*
 *跳转系统相机
 */
    public void getToCamera(){

        Intent intent1=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //传递此次拍照照片存储的路径
        //指定一个路径存储图片    手机SD卡路径
       File fileDir=new File(Constants.DIR_PATH);

        // /申请权限

        if(!fileDir.exists()){//不存在 创建文件夹
            fileDir.mkdirs();//创建文件以及他父类不存在的文件夹
        }
        //向相机传递文件路径         DIR_PATH+File.separator+"Photo"+System.currenTimeMillis()+".jpg"
        intent1.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Constants.PHOTO_FILE_PATH)));

        startActivityForResult(intent1,Constants.GOTO_CAMERA);


    }
    /**
     *
     * @param requestCode 请求码
     * @param permissions   权限
     * @param grantResults   权限所对应的请求结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case Constants.PERMISSION_CAMERA:
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED&&grantResults[1]== PackageManager.PERMISSION_GRANTED){//同意相机权限
                   getToCamera();
                }else {
                    Toast.makeText(this,"打开相机需要权限   权限管理---->应用---->相机权限",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case Constants.GOTO_CAMERA://相机拍照结果
                if(resultCode==RESULT_OK){
                    //读取图片  路径
                    Bitmap bitmap=BitmapFactory.decodeFile(Constants.PHOTO_FILE_PATH);
                    mIvRegisterPic.setImageBitmap(bitmap);
                }
                break;
            case Constants.GOTO_PICK://图库选择的结果
                Uri uri=data.getData();
                //需要进行查询
                String[] filePathColumn={MediaStore.Images.Media.DATA};//指定一个列
                //游标
                Cursor curs=getContentResolver().query(uri,filePathColumn,null,null,null);
                curs.moveToFirst();//把游标移到第一个
                //拿到对应列的下标
                int columIndex=curs.getColumnIndex(filePathColumn[0]);

                String path=curs.getString(columIndex);
                try {
                   Bitmap bitmap= BitmapFactory.decodeStream(new FileInputStream(path));
                    mIvRegisterPic.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;

        }
    }
}


/**
 * 拿到登录界面输入框的输入内容  改变用户名
 *
 * Fragment 给Activity传值
 *      1.把Fragment添加到Activity
 *      在Activity中咋么拿到Fragment里面输入框的内容
 *          mEtAccount.getText().toString();
 *       拿到输入框内容 咋么改用户名
 *       mTvUserName.setText(mEtAccount.getText().toString());
 *
 *
 *
 * 如何从用户中心拿到用户名和头像 改登录页面的数据
 *
 *
 *
 */
