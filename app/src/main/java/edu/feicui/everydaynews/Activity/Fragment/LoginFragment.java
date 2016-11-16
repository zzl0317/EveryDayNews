package edu.feicui.everydaynews.Activity.Fragment;

import android.inputmethodservice.ExtractEditText;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.ExtractedText;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.feicui.everydaynews.Activity.HomeActivity;
import edu.feicui.everydaynews.Entity.LoginInfo;
import edu.feicui.everydaynews.R;
import edu.feicui.everydaynews.View.SeverletUrl;
import edu.feicui.everydaynews.net.Constants;
import edu.feicui.everydaynews.net.MyHttp;
import edu.feicui.everydaynews.net.OnResuilFinishListener;
import edu.feicui.everydaynews.net.Response;
import edu.feicui.everydaynews.net.Utils;

/**
 * 注册
 * Created by Administrator on 2016/10/11.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

//    TextView mTvTittle;//标题的文字
    TextInputEditText mEtEmail;
    TextInputEditText mEtAccount;
    TextInputEditText mEtPassword;
    Button mBtnLogin;
    HomeActivity activity;
    String mLoginEmail;
    String mLoginAccount;
    String mLoginPassWord;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_fragment,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity= (HomeActivity) getActivity();

    }

    /**
     * 在onActivityCreated之前执行
     * 在onCreateView之后执行
     * @param view
     * @param savedInstanceState
     * 进行初始化
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //TODO      进行初始化操作
//        mTvTittle=(TextView) view.findViewById(R.id.tv_base_tittle);
        mBtnLogin= (Button) view.findViewById(R.id.btn_right_loging);
        mEtEmail= (TextInputEditText) view.findViewById(R.id.et_register_email);
        mEtAccount= (TextInputEditText) view.findViewById(R.id.et_register_nick_name);
        mEtPassword= (TextInputEditText) view.findViewById(R.id.et_register_pass_word);

        mBtnLogin.setOnClickListener(this);
//        mTvTittle.setText("注册");
    }

    /**
     * 网络请求验证邮箱是否注册成功
     *  1.注册成功跳到登录页面
     *  2.注册失败(1.服务器已满不允许注册，2.用户名重复，3.邮箱重复)
     *         不允许注册返回的结果
     *               可以弹Toast窗口
     *              可以用dialog做
     *              可以用popupWindow做
     */
    public void getHttpData(){
    Map<String,String> params=new HashMap<>();
    params.put("email",mLoginEmail);
    params.put("uid",mLoginAccount);
    params.put("pwd",mLoginPassWord);
    params.put("ver","0000000");
        /**
         * 网络请求加链接
         */
    MyHttp.get(activity, SeverletUrl.Login, params, new OnResuilFinishListener() {
        @Override
        public void success(Response response) {//请求成功
            Gson gson=new Gson();

            LoginInfo loginInfo= gson.fromJson(response.resuit.toString(), LoginInfo.class);
            switch (loginInfo.data.result){
                case Constants.LOGIN_REGISTER_SUCCESSFULL:
                    //注册成功
                    // 跳到登录页
                    activity.RepaceFragment(2);

                    break;
                case Constants.LOGIN_USERS_NUMBER_FULL:
                    //用户数量已满  不允许注册
                    Toast.makeText(activity, "服务器已满，请换个服务器注册", Toast.LENGTH_SHORT).show();
                    break;
                case Constants.LOGIN_USERS_REPEAT:
                    //用户名重复
                    Toast.makeText(activity, "用户名重复，请换个用户名", Toast.LENGTH_SHORT).show();
                    break;
                case Constants.LOGIN_EMAIL_REPEAT:
                    //用户邮箱重复
                    Toast.makeText(activity, "用户名邮箱重复，请换个用邮箱", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void failed(Response response) {
            Log.e("aac", "failed: " );
            //TODO  用Dialog做弹框
            Toast.makeText(activity, "注册失败", Toast.LENGTH_SHORT).show();
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

        mLoginEmail=mEtEmail.getText().toString();
        mLoginAccount=mEtAccount.getText().toString();
        mLoginPassWord=mEtPassword.getText().toString();
        boolean LoginUserCode= Utils.LoginPatten(mLoginEmail,mLoginAccount,mLoginPassWord);
            if(LoginUserCode){
                getHttpData();
             }else {
                //邮箱、账号、密码输入错误
                Toast.makeText(activity,"邮箱、账号、密码输入错误",Toast.LENGTH_SHORT).show();
            }


        }

}
