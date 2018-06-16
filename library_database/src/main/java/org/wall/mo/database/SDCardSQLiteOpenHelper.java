package org.wall.mo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;

/**
 * SQLite数据库针对SD卡的核心帮助类<br/>
 * 该类主要用于直接设置,创建,升级数据库以及打开,关闭数据库资源<br/>
 * Created by moziqi on 2014/12/25.<br/>
 */
public abstract class SDCardSQLiteOpenHelper {

    private static final String TAG = "SDCardSQLiteOpenHelper";

    private final static String DATA_BASE = "databases";

    private final Context context;
    private final String name;

    private final SQLiteDatabase.CursorFactory factory;
    private final int version;

    private SQLiteDatabase sqLiteDatabase;
    private boolean isInitializing = false;

    /**
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    public SDCardSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        if (version < 1) {
            throw new IllegalArgumentException("数据库版本必须大于等于1，当前版本" + version);
        }
        this.context = context;
        this.name = name;
        this.factory = factory;
        this.version = version;
    }

    public synchronized SQLiteDatabase getWritableDataBase() {
        if (this.sqLiteDatabase != null && this.sqLiteDatabase.isOpen() && this.sqLiteDatabase.isReadOnly()) {
            return this.sqLiteDatabase;
        }
        if (isInitializing) {
            throw new IllegalArgumentException("getWritableDataBase 重复调用了。");
        }
        boolean success = false;
        SQLiteDatabase db = null;
        try {
            isInitializing = true;
            if (this.name == null) {
                db = sqLiteDatabase.create(null);
            } else {
                String path = getDataBasePath(name).getPath();
                // TODO 创建数据库
                db = SQLiteDatabase.openOrCreateDatabase(path, factory);
                int version = db.getVersion();
                if (version != this.version) {
                    db.beginTransaction();
                    try {
                        if (version == 0) {
                            onCreate(db);
                        } else {
                            onUpgrade(db, version, this.version);
                        }
                        db.setVersion(this.version);
                        db.setTransactionSuccessful();
                    } finally {
                        db.endTransaction();
                    }
                }
                onOpen(db);
                success = true;
                return db;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            isInitializing = false;
            if (success) {
                if (sqLiteDatabase != null) {
                    try {
                        sqLiteDatabase.close();
                    } catch (Exception e) {
                    }
                }
                sqLiteDatabase = db;
            } else {
                if (db != null)
                    db.close();
            }
        }
        return db;
    }

    public synchronized SQLiteDatabase getReadableDatabase() {
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
            return sqLiteDatabase; // The database is already open for business
        }

        if (isInitializing) {
            throw new IllegalStateException("getReadableDatabase called recursively");
        }

        try {
            return getWritableDataBase();
        } catch (SQLiteException e) {
            if (this.name == null)
                throw e; // Can't open a temp database read-only!

            Log.e(TAG, "Couldn't open " + this.name + " for writing (will try read-only):", e);
        }

        SQLiteDatabase db = null;
        try {
            isInitializing = true;
            String path = getDataBasePath(this.name).getPath();
            db = SQLiteDatabase.openDatabase(path, factory, SQLiteDatabase.OPEN_READWRITE);
            if (db.getVersion() != this.version) {
                throw new SQLiteException("Can't upgrade read-only database from version " + db.getVersion() + " to " + this.version + ": " + path);
            }

            onOpen(db);
            Log.w(TAG, "Opened " + this.name + " in read-only mode");
            sqLiteDatabase = db;
            return sqLiteDatabase;
        } finally {
            isInitializing = false;
            if (db != null && db != sqLiteDatabase)
                db.close();
        }
    }

    public synchronized void close() {
        if (isInitializing) {
            throw new IllegalStateException("在初始化前已经关闭");
        }
        if (sqLiteDatabase != null && this.sqLiteDatabase.isOpen()) {
            this.sqLiteDatabase.close();
            this.sqLiteDatabase = null;
        }
    }

    /**
     * @param name
     * @return
     */
    public File getDataBasePath(String name) {
        String EXTERN_PATH = getDataBaseParentPath();
        if (TextUtils.isEmpty(EXTERN_PATH)) {
            EXTERN_PATH = this.context.getFilesDir().getParent();
        }
        String path = EXTERN_PATH + File.separator + DATA_BASE;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return new File(EXTERN_PATH + File.separator + name);
    }

    private static String getSDCardDirectory() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            return sdcardDir.getAbsolutePath();
        }
        return null;
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should
     * happen.
     *
     * @param db The database.
     */
    public abstract void onCreate(SQLiteDatabase db);

    /**
     * Called when the database needs to be upgraded. The implementation should
     * use this method to drop tables, add tables, or do anything else it needs
     * to upgrade to the new schema version.
     * <p/>
     * <p/>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new
     * columns you can use ALTER TABLE to insert them into a live table. If you
     * rename or remove columns you can use ALTER TABLE to rename the old table,
     * then create the new table and then populate the new table with the
     * contents of the old table.
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    public abstract void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);

    /**
     * Called when the database has been opened. Override method should check
     * {@link SQLiteDatabase#isReadOnly} before updating the database.
     *
     * @param db The database.
     */
    public void onOpen(SQLiteDatabase db) {
    }


    public abstract String getDataBaseParentPath();
}
