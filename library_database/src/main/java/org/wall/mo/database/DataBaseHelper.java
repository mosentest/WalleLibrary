package org.wall.mo.database;

import java.io.File;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * http://www.w3cschool.cc/sqlite/sqlite-constraints.html<br/>
 * SQLite数据库的帮助类<br/>
 * 该类属于扩展类,主要承担数据库初始化和版本升级使用,其他核心全由核心父类完成<br/>
 * Created by moziqi on 2014/12/25.<br/>
 * 从接入工程，重新实现这个类，或者继续集成这个类
 */
public class DataBaseHelper extends SDCardSQLiteOpenHelper {

    public static final int VERSION = 1; // 下次更新的时候，修改数据库版本号

    private Context context;

    private volatile static DataBaseHelper mInstance;

    public static DataBaseHelper getInstance(Context context, String name) {
        if (mInstance == null) {
            synchronized (DataBaseHelper.class) {
                if (mInstance == null) {
                    mInstance = new DataBaseHelper(context, name, null);
                }
            }
        }
        return mInstance;
    }

    /**
     * @param context
     * @param name    //TODO 数据库名字
     * @param factory
     */
    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        onUpgrade(db, 0, VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //for (int version = oldVersion + 1; version <= newVersion; version++) {
        upgradeTo(db, 0);
        //}
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public String getDataBaseParentPath() {
        return null;
    }


    private void upgradeTo(SQLiteDatabase db, int version) {
        //switch (version) {
        //case 1:
        //case 2:
        //case 3:
        //	break;
        //default:
        //	throw new IllegalStateException("Don't know how to upgrade to " + version);
        //}
    }


}
