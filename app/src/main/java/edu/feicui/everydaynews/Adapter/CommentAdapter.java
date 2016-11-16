package edu.feicui.everydaynews.Adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import edu.feicui.everydaynews.Entity.CommentInfo;
import edu.feicui.everydaynews.Entity.CommentItem;
import edu.feicui.everydaynews.R;

/**
 * Created by Administrator on 2016/10/25.
 */
public class CommentAdapter extends MyBaesAdapter<CommentItem>{

    public CommentAdapter(Context mContext, ArrayList<CommentItem> mList, int mLayoutId) {
        super(mContext, mList, mLayoutId);
    }

    /**
     * @param holder      对应条目的holder
     * @param convertView 对应条目的view
     * @param position    对应条目的位置
     * @param commentItem
     */
    @Override
    public void putView(Holder holder, View convertView, int position, CommentItem commentItem) {
        TextView mTvCommentId= (TextView) convertView.findViewById(R.id.iv_news_comment_id);//评论者编号     cid
        ImageView mIvUserPic= (ImageView) convertView.findViewById(R.id.iv_news_comment_pic);//头像          portrait
        TextView mTvUserName= (TextView) convertView.findViewById(R.id.iv_news_comment_user);//用户名        uid
        TextView mTvCommentDate= (TextView) convertView.findViewById(R.id.iv_news_comment_date);//时间       stamp
        TextView mTvCommentAccount= (TextView) convertView.findViewById(R.id.iv_news_comment_account);//评论内容   content
        mTvCommentId.setText(commentItem.cid+"");
        Glide.with(mContext).load(commentItem.portrait).into(mIvUserPic);
        mTvUserName.setText(commentItem.uid);
        mTvCommentDate.setText(commentItem.stamp);
        mTvCommentAccount.setText(commentItem.content);
    }
}
