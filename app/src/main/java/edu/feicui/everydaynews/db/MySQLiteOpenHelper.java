package edu.feicui.everydaynews.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.w3c.dom.Text;

/**
 * Created by Administrator on 2016/10/20.
 *
 * SQLite的优点：
 * 1.在读取数据的时候就可以减少访问网络的消耗，简化数据库的管理，程序的部署比较容易
 * 2.简单，访问的速度快(最好不要把它看做一个数据库，而是一个文件系统)
 * 3.支持常见的SQL语法 包括常见的数据库功能，  事务  索引   视图   触发器的部分功能  （麻雀虽小五脏俱全）
 * 4.平台广发性   Unix  Linux  MAC window....系统上运行
 * SQLIte的数据类型
 *   SQLite  可以给每个字段定义类型  但是也可以不制定，如果字段没有类型则与属于动态类型
 *   常见的SQLite的数据类型
 *      Integer： 有符号的整数类型
 *      Real：   浮点型
 *      BLOB：存储图片、等二进制信息
 *      Boolean数据类型：sqlite没有默认的类型
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper{

    public static final String DB_DATA= "text.db";//数据库名

    public final static int VERSION = 1;//版本号
   /*
    *自带的构造方法
    *
    * Context 上下文   当前事务发生的场景
    *
    * name   数据库的名称
    *
    * factory    游标     查询时使用
    *
    * version   指定数据库版本 高版本会自动调用低版本(自动调用  onUpgrade)
    */

    public MySQLiteOpenHelper(Context context) {
        super(context, DB_DATA, null, VERSION);
    }
    /**
     * 当数据库创建的时候被调用
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table user(account text,password text)";
        db.execSQL(sql);

    }
    /*
     *版本更新时调用
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }
}
