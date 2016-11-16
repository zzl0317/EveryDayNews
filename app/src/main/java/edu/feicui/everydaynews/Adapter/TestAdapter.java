package edu.feicui.everydaynews.Adapter;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import edu.feicui.everydaynews.Entity.Person;
import edu.feicui.everydaynews.R;


/**
 * Created by Administrator on 2016/9/27.
 */
public class TestAdapter extends MyBaesAdapter<Person>{

    public TestAdapter(Context mContext, ArrayList mList,  int mLayoutId) {
        super(mContext,mList,mLayoutId);


    }

    @Override
    public void putView(Holder holder, View convertView, int position, Person person) {
        TextView mTvName= (TextView) convertView.findViewById(R.id.tv_item_name);
        TextView mTvAge= (TextView) convertView.findViewById(R.id.tv_item_age);
        mTvName.setText(person.name);
        mTvAge.setText(person.age+"");
    }
}
