package edu.feicui.everydaynews.net;

import android.os.Environment;

import java.io.File;

/**
 * 静态常量
 * Created by Administrator on 2016/9/22.
 */
public class Constants {

    public static final int POST = 11;//post请求
    public static final int GET = 10;//get请求

    public static final int CONNECT_TIMEOUT = 5000;//请求超时

    public static final int READ_TIMEOUT = 5000;//读取超时

    public static final int LOGIN_REGISTER_SUCCESSFULL =0; //注册成功

    public static final int LOGIN_USERS_NUMBER_FULL=-1;//用户数量已满 不允许注册

    public static final int LOGIN_USERS_REPEAT=-2;//用户名重复

    public static final int LOGIN_EMAIL_REPEAT=-3;//用户邮箱重复

    public static final int ZERO=0;//正常登陆
    public static final int ONE=-1;//用户名或密码错误
    public static final int TWO=-2;//限制登陆(禁言,封IP)
    public static final int THREE=3;//限制登陆(异地登陆等异常)
    public static final int FOUR=4;
    public static final int FIVE=5;

    public static String TOKEN="";
/*
    权限请求
 */
    public static final int PERMISSION_CAMERA=201;
    /*
     *图片此次文件夹路径
     */
    public static final String DIR_PATH= Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"EveryDayNews";
    /*
     *图片存储路径
     */
    public static final String PHOTO_FILE_PATH=DIR_PATH+File.separator+"photo.jpeg";
    /*
        跳转相机的请求码
     */
    public static final int GOTO_CAMERA=202;
    /*
        图库请求码
     */
    public static final int GOTO_PICK=203;
}
