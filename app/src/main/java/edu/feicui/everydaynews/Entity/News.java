package edu.feicui.everydaynews.Entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/8.
 */
public class News implements Serializable{
   public String summary;//新闻的摘要
   public String icon;//图标
   public String stamp;//新闻发布的时间
   public String title;//标题
   public int nid;//新闻的ID、
   public String link;//链接
   public int type;//类型标示
}
