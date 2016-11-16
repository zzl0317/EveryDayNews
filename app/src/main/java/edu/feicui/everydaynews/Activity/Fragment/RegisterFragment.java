package edu.feicui.everydaynews.Activity.Fragment;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import edu.feicui.everydaynews.Activity.HomeActivity;
import edu.feicui.everydaynews.Activity.RegisterActivity;
import edu.feicui.everydaynews.Entity.LoginInfo;
import edu.feicui.everydaynews.R;
import edu.feicui.everydaynews.View.SeverletUrl;
import edu.feicui.everydaynews.db.MySQLiteOpenHelper;
import edu.feicui.everydaynews.db.SQLiteOpenHelperUtil;
import edu.feicui.everydaynews.net.Constants;
import edu.feicui.everydaynews.net.MyHttp;
import edu.feicui.everydaynews.net.OnResuilFinishListener;
import edu.feicui.everydaynews.net.Utils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 登录
 * Created by Administrator on 2016/10/10.
 */
public class  RegisterFragment extends Fragment implements View.OnClickListener {

    SQLiteDatabase database;
    EditText mEtAccount;//账号
    EditText mEtPassWord;//密码
    Button mBtnUserLogin;
    Button mBtnPassWord;
    Button mBtnUserLoging;
    String mLoginAccount;
    String mLoginPassWord;
    HomeActivity activity;
    ImageView mIvRegisterPic;//头像
    Bitmap bitmap;

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    activity.RepaceFragment(0);
                    break;
                case 1:
                    mEtAccount.getText().toString();
                    break;
            }
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_register, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (HomeActivity) getActivity();
        SQLiteOpenHelper helper=new MySQLiteOpenHelper(activity);
        database=helper.getWritableDatabase();
        bitmap=BitmapFactory.decodeFile(Constants.PHOTO_FILE_PATH);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mIvRegisterPic= (ImageView) view.findViewById(R.id.iv_register_pic);//头像
        mEtAccount= (EditText) view.findViewById(R.id.et_register_user);//账号的输入框
        mEtPassWord= (EditText) view.findViewById(R.id.et_pass_word);//密码的输入框
        mBtnUserLogin = (Button) view.findViewById(R.id.btn_right_login);//注册按钮
        mBtnPassWord = (Button) view.findViewById(R.id.btn_right_pass_word);//忘记密码按钮
        mBtnUserLoging = (Button) view.findViewById(R.id.btn_right_loging);//登录按钮
        UserMessage();

   /*
    ******************************绑定事件操作****************************************
    */
        mBtnUserLogin.setOnClickListener(this);
        mBtnPassWord.setOnClickListener(this);
        mBtnUserLoging.setOnClickListener(this);
    }
    public void getData(){
        Map<String ,String> params=new HashMap<>();
        params.put("ver","0000000");
        params.put("uid",mLoginAccount);
        params.put("pwd",mLoginPassWord);
        params.put("device","0");
        MyHttp.get(activity, SeverletUrl.LOGIN_REGISTER, params, new OnResuilFinishListener() {
            @Override
            public void success(edu.feicui.everydaynews.net.Response response) {
                Gson gson=new Gson();
                LoginInfo loginInfo=gson.fromJson(response.resuit.toString(),LoginInfo.class);

                switch (loginInfo.data.result){
                    case Constants.ZERO://正常登录
                        Toast.makeText(activity,"正常登录",Toast.LENGTH_SHORT).show();
                        Constants.TOKEN=loginInfo.data.token;
//                        String sql="insert into user (account,password) values ("+mLoginAccount+","+mLoginPassWord+")";
//                        database.execSQL(sql);
                        ContentValues values = new ContentValues();
                        values.put("account",mLoginAccount);
                        values.put("password",mLoginPassWord);
                        database.insert("user", null, values);
                        Intent intent=new Intent(activity, RegisterActivity.class);
                        intent.putExtra("account", mLoginAccount);
                        startActivity(intent);
                        break;
                    case Constants.ONE://用户名或密码错误
                        Toast.makeText(activity,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                    break;
                    case Constants.TWO://限制登陆(禁言,封IP)
                        Toast.makeText(activity,"限制登陆(禁言,封IP)",Toast.LENGTH_SHORT).show();
                    break;
                    case Constants.THREE://限制登陆(异地登陆等异常)
                        Toast.makeText(activity,"限制登陆(异地登陆等异常)",Toast.LENGTH_SHORT).show();
                    break;

                }
            }
            @Override
            public void failed(edu.feicui.everydaynews.net.Response response) {
                Toast.makeText(activity,"网络连接失败",Toast.LENGTH_LONG).show();
            }
        });
    }


    public void UserMessage(){
        if(MySQLiteOpenHelper.DB_DATA==null){//判断数据库是否为空
           //为空 存入用户头像  账号
            //TODO 拿到头像
        String sql="insert into user(account userpic)values("+mLoginAccount+","+ mIvRegisterPic+")";
            database.execSQL(sql);
        }else {
           //不为空  存头像
//            String sql="insert into user(account userpic)values("+mLoginAccount+","+ mIvRegisterPic+")";
//            database.execSQL(sql);
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_right_login:
                activity.RepaceFragment(0);//注册
                break;
            case R.id.btn_right_pass_word://忘记密码
                activity.RepaceFragment(1);
                break;
            case R.id.btn_right_loging://登录
                activity.RepaceFragment(2);
                mLoginAccount=mEtAccount.getText().toString();
                mLoginPassWord=mEtPassWord.getText().toString();
                boolean LoginUserCode= Utils.LoginPatten(mLoginAccount,mLoginPassWord);
                if(LoginUserCode){
                    getData();
                }else {
                    Toast.makeText(activity,"账号、密码输入错误",Toast.LENGTH_SHORT).show();
                }
                break;
            case 0:
                activity.RepaceFragment(0);

                break;
        }
    }
}
