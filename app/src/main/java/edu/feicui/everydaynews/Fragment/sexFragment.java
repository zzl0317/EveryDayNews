package edu.feicui.everydaynews.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import edu.feicui.everydaynews.Activity.BaseActivity;
import edu.feicui.everydaynews.R;

/**
 * Created by Administrator on 2016/10/8.
 */
public class sexFragment extends Activity {
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sex_fragment);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        mListView= (ListView) findViewById(R.id.lv_sex_fragment);
        String [] data={"新闻","收藏","跟帖","本地","图片"};
        mListView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data));
    }
}
