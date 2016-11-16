package edu.feicui.everydaynews.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 操作数据库的工具类
 * Created by Administrator on 2016/10/24.
 */
public class SQLiteOpenHelperUtil {
    /*
     *可以从SQLiteOpenHelper 获得
     */
    SQLiteDatabase database;


    public SQLiteOpenHelperUtil(Context context){
        /*
         *1.拿到SQLiteOpenHelper
         *2.拿到SQLiteDatabase
         *  getWritableDatabase  读写     当数据库满了的时候  可能会造成异常
         *  getReadableDatabase  读写 耗时长     当数据库满了  会返回一个 只读数据库操作的对象
         */
        SQLiteOpenHelper helper=new MySQLiteOpenHelper(context);
        database=helper.getWritableDatabase();
//        helper.getReadableDatabase();
        /*
         *进行操作  增删改查
         */
    }
    public void insert() {
        /**
         * 1.sql    insert into  表名(列名，列名....  ) values（值，值....）
         *
         * 2.使用SQLiteDateBase
//         */
//        String sql="insert into user(_id,account,password) values (1,\"account\",password)";
//        Log.e("aac", "sql: "+sql );
//        database.execSQL(sql);
        /**
         * name  表名
         * nullColumnHack  默认值  可以为null
         * ContentValues  数据
         */
//        ContentValues values=new ContentValues();
//          values.put("_id",1);
//          values.put("name","account");
//          values.put("password",00);
//          database.insert("user",null,values);
    }
    /*
     *删除
     */
    public void delete(){
    /*
     *1.sql   delete   from  表名   where 条件
     */
        String sql="delete  from   user  where  id";
        database.execSQL(sql);
//        database.delete("user","name",);
    }
    /*
     *改
     */
    public void upDate(){
        String sql="update user set password=";
        database.execSQL(sql);
        /**
         *
         */
//        ContentValues values=new ContentValues();
//        values.put("_id",11);
//        values.put("name",11);
//        values.put("password",11);
//
//        database.update("user",values,null,null);
    }

    /**
     * 查询
     * 1.sql语句      select(列名，列名...)(*)from  表名  *表示查询所有
     * 2.使用SQLiteDateBase
     */
    public void select(){
        String sql="select * from user";
        /**
         * 返回值  Cursor  游标
         * sql  sql语句
         * selectionArgs  条件的值
         *
         */
        Cursor cursor=database.rawQuery(sql,null);
        /*
		 * table 表名
		 * columns    所要查询的列	 数组 	null(表示查询全部)
		 * 			new String[]{"_id","name"
		 * selection  查询的条件	null(没有条件)      _id=1
		 * selectionArgs   条件的值
		 * groupBy	分组相关
		 * having	分组相关
		 * orderBy	排序  null(默认排序)
		 */
//        Cursor cursor1=database.query("user",null,null,null,null,null,null);
//        //游标的位置
//       while (cursor.moveToFirst()) {
//           int id = cursor.getInt(cursor.getColumnIndex("_id"));
//           String account = cursor.getString(cursor.getColumnIndex("account"));
//           int password = cursor.getInt(cursor.getColumnIndex("password"));
//
//       }
    }
}
