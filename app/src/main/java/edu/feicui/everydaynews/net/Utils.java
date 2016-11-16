package edu.feicui.everydaynews.net;

import android.util.Log;

import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/9/22.
 */
public class Utils {


    public static String getUrl(Map<String,String> params,int type){
        StringBuffer buffer=new StringBuffer();
        if (params != null&&params.size()!=0) {
            if (type == Constants.GET) {
                buffer.append("?");
            }
            Set<String> keySet=params.keySet();
            for (String key:keySet) {
                buffer.append(key)
                        .append("=")
                        .append(params.get(key)).append("&");
            }
            buffer.deleteCharAt(buffer.length()-1);
        }
        return  buffer.toString();
    }

    /**
     * 用正则验证邮箱  账号   密码
     */
    public static boolean LoginPatten(String email, String account,String password) {
        /**
         * 正则表达式的使用方法
         *  1.拿到Pattern
         *  2.拿到匹配器
         *  3.得到匹配的结果
         */
        String ReagisterEmail = "^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$";
        boolean booEmail = Pattern.matches(ReagisterEmail, email);
        String ReagisterAccount = "^[a-zA-Z[0-9]]{6,15}$";
        boolean booAccout = Pattern.matches(ReagisterAccount, account);
        String ReagisterPassWrod = "^[a-zA-Z[0-9]]{6,15}$";

        boolean booPassWrod = Pattern.matches(ReagisterPassWrod, password);
     if(booEmail&&booAccout&&booPassWrod){
        return true;
     }else {
         return false;
     }

    }

    /**
     * 用正则表示验证账号和密码
     * @param account
     * @param password
     * @return
     */
    public static boolean LoginPatten( String account,String password) {
        /**
         * 正则表达式的使用方法
         *  1.拿到Pattern
         *  2.拿到匹配器
         *  3.得到匹配的结果
         */
        String ReagisterAccount = "^[a-zA-Z[0-9]]{6,15}$";
        boolean booAccout = Pattern.matches(ReagisterAccount, account);
        String ReagisterPassWrod = "^[a-zA-Z[0-9]]{6,15}$";

        boolean booPassWrod = Pattern.matches(ReagisterPassWrod, password);
        if(booAccout&&booPassWrod){
            return true;
        }else {
            return false;
        }

    }

    /**
     * 验证邮箱
     * @param email
     * @return
     */
    public static boolean LoginPatten(String email) {
        /**
         * 正则表达式的使用方法
         *  1.拿到Pattern
         *  2.拿到匹配器
         *  3.得到匹配的结果
         */

        String ReagisterEmail = "^[a-zA-Z[0-9]]{6,15}$";

        boolean booEmail= Pattern.matches(ReagisterEmail, email);
        if(booEmail){
            return true;
        }else {
            return false;
        }

    }}
