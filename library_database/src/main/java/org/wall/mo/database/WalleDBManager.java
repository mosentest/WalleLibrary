package org.wall.mo.database;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * SQLite数据库管理类<br/>
 * 主要负责数据库资源的初始化,开启,关闭,以及获得DatabaseHelper帮助类操作<br/>
 * Created by moziqi on 2014/12/25.<br/>
 */
public class WalleDBManager {

    private volatile static WalleDBManager dBManager = null;

    private SDCardSQLiteOpenHelper mSdCardSQLiteOpenHelper;

    /**
     * 构造函数
     *
     */
    private WalleDBManager() {
        super();
    }

    public static synchronized WalleDBManager getInstance() {
        if (null == dBManager) {
            synchronized (WalleDBManager.class) {
                if (null == dBManager) {
                    dBManager = new WalleDBManager();
                }
            }
        }
        return dBManager;
    }

    /**
     * 关闭数据库 注意:当事务成功或者一次性操作完毕时候再关闭
     */
    public void closeDatabase(SQLiteDatabase dataBase, Cursor cursor) {
        if (null != cursor) {
            cursor.close();
        }
        if (null != dataBase) {
            dataBase.close();
        }
    }

    /**
     * 打开数据库 注:SQLiteDatabase资源一旦被关闭,该底层会重新产生一个新的SQLiteDatabase
     */
    public SQLiteDatabase openDatabase() {
        return getDatabaseHelper().getWritableDataBase();
    }

    /**
     * 获取DataBaseHelper
     *
     * @return
     */
    public SDCardSQLiteOpenHelper getDatabaseHelper() {
        if (mSdCardSQLiteOpenHelper == null) {
            throw new RuntimeException("mSdCardSQLiteOpenHelper is null");
        }
        return mSdCardSQLiteOpenHelper;
    }

    /**
     * @param sdCardSQLiteOpenHelper
     */
    public void setSDCardSQLiteOpenHelper(SDCardSQLiteOpenHelper sdCardSQLiteOpenHelper) {
        if (this.mSdCardSQLiteOpenHelper == null) {
            this.mSdCardSQLiteOpenHelper = sdCardSQLiteOpenHelper;
        }
    }

    public void onDestroy() {
        if (mSdCardSQLiteOpenHelper != null) {
            mSdCardSQLiteOpenHelper.close();
        }
        mSdCardSQLiteOpenHelper = null;
        dBManager = null;
    }
}
