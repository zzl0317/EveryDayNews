package edu.feicui.everydaynews.Provider;

import android.app.Activity;
import android.content.UriMatcher;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import edu.feicui.everydaynews.R;

/**
 * Created by Administrator on 2016/10/24.
 */
public class MainActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
         *----------------------访问者-----------------------
         */

        Uri uri=Uri.parse("edu.feicui.everydaynews.TEXT_PROVIDER/tea");
        /*
         *----------------------提供者-----------------------
         */
        UriMatcher matcher=new UriMatcher(UriMatcher.NO_MATCH);
        /*
         *-------------------添加所有可能的匹配
         */
        matcher.addURI("edu.feicui.everydaynews.TEXT_PROVIDER","stu",11);
        matcher.addURI("edu.feicui.everydaynews.TEXT_PROVIDER","tea",22);
        matcher.addURI("edu.feicui.everydaynews.TEXT_PROVIDER","tet",33);
        /*
         *拿到相应数据的匹配码
         */
        int code=matcher.match(uri);
        Log.e("aac", "code: "+code );
    }
}
