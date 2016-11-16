package edu.feicui.everydaynews.Activity.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

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
 * 忘记密码
 * Created by Administrator on 2016/10/11.
 */
public class PassWordFragment extends Fragment implements View.OnClickListener {
    HomeActivity activity;//声明activity的对象
    Button mBtnPassWord;//忘记密码的按钮
    TextInputEditText mEditText;//输入注册时邮箱的输入框
    String mLoginEmail;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pass_word,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity= (HomeActivity) getActivity();
    }

    /**
     * 找回账号
     */
    public void getHttpPassword(){
        Map<String ,String> params=new HashMap<>();
        params.put("email",mLoginEmail);
        params.put("ver","0000000");
        MyHttp.get(activity, SeverletUrl.PASS_WORD, params, new OnResuilFinishListener() {
            @Override
            public void success(Response response) {
                Gson gson=new Gson();
                LoginInfo loginInfo=gson.fromJson(response.resuit.toString(),LoginInfo.class);
                switch (loginInfo.data.result){
                    case Constants.ZERO://发送邮箱成功
                    Toast.makeText(activity,"发送邮箱成功",Toast.LENGTH_SHORT).show();
                    case Constants.ONE:
                        Toast.makeText(activity,"发送失败(该邮箱未注册)",Toast.LENGTH_SHORT).show();
                        break;
                    case Constants.TWO:
                        Toast.makeText(activity,"发送失败(邮箱不存在或被封号)",Toast.LENGTH_SHORT).show();
                        break;
                }

            }

            @Override
            public void failed(Response response) {
                Toast.makeText(activity,"该邮箱不存在",Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //初始化忘记密码的按钮
        mBtnPassWord= (Button) view.findViewById(R.id.btn_right_pass_word);
        //初始化输入邮箱的输入框
        mEditText= (TextInputEditText) view.findViewById(R.id.et_register);
        mBtnPassWord.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        mLoginEmail=mEditText.getText().toString();
        boolean MatchEmail= Utils.LoginPatten(mLoginEmail);
        if(MatchEmail){
            getHttpPassword();
        }else {
            Toast.makeText(activity,"用户邮箱不正确",Toast.LENGTH_SHORT).show();
        }
    }
}
