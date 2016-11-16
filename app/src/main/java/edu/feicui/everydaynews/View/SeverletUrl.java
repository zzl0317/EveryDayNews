package edu.feicui.everydaynews.View;

import android.provider.SyncStateContract;
import android.util.Log;

import edu.feicui.everydaynews.net.Constants;

/**
 * Created by Administrator on 16-9-30.
 */
public class SeverletUrl {
    /**
     * 根接口
     */
    public static final String BASE_URL="http://118.244.212.82:9092/newsClient";
    /**
     * 新闻列表数据
     */

    public static final String NEW_LIST=BASE_URL+"/news_list";
    /**
     * 注册
     */
    public static final String Login=BASE_URL+"/user_register";
    /**
     *  忘记密码
     */
    public static final String PASS_WORD=BASE_URL+"/user_forgetpass";
    /**
     * 登录login
     */
    public static final String LOGIN_REGISTER=BASE_URL+"/user_login";

    /**
     * 用户详情
     */
    public static final String TOKEN_REGISTER=BASE_URL+"/user_home?ver=0000000&imei=000000000000000&token ="+ Constants.TOKEN;
/**
 * 显示评论
 */
    public static final String COMMENT_SHOW=BASE_URL+"/cmt_list";
    /**
     * 发布评论
     */

    public static final String  COMMENT_RELEASE=BASE_URL+"/cmt_commit";

    public static int NID;

    /**
     * 评论总数
     */
    public static final String  COMMENT_TOTAL=BASE_URL+"/cmt_num?ver=0000000&nid=";

}
