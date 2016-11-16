package edu.feicui.everydaynews.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.feicui.everydaynews.R;

import static edu.feicui.everydaynews.R.layout.fragment_activity;

/**
 * Created by Administrator on 2016/9/28.
 */
public class oneFragmentActivity extends Fragment{
    /**
     * 给Fragment加载   界面
     * @param inflater          布局填充器   主要就是加载xml  fragment布局
     * @param container         容器
     * @param savedInstanceState        数据的状态
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity,container,false);

        return view;
    }
    /**
     * Fagment  碎片   3.0以后推出的
     *          主要用来加载到Activity中进行显示的
     *          功能  用来   交互  展示界面的
     *       使用
     *          1.xml
     *             a.布局中  新建一个fragment标签
     *             b.新建一个fragment       继承自Fragment(V4包)
     *               注意：app下的Fragment可以用activity加载
     *               新建的Activity不能命名为FragmentActivity  否则onCreate方法是公开的
     *               如果直接崩溃的话   要给布局中的Fragment加id
     *      2.代码
     *          a：拿到FragmentManager    -------------getSupportFragmentManager    getFragmentManager
     *          b：拿到FragmentTransaction
     *            beginTransaction
     *              C：事务操作  添加  替换   移除
     *              D：事务需要提交
     *              注意：同一个Fragment不能添加多次
     *              切换两个Fragment
     *                  1.replace直接切换
     *                  2.先添加你所需要的所有的Fragment  通过  hide     show
     *                  控制隐藏和显示
     *
     *       生命周期
     *              onAttach-----------------》当Activity 和Fragment相关联的时候执行    add
     *              onCreate----------------》Fragment开始创建   执行
     *              onCreateView-----------》创建Fragment显示的View
     *              onViewCreated----------》当Fragment的View加载完   执行
     *              on
     *
     *
     *
     *
     */
}
