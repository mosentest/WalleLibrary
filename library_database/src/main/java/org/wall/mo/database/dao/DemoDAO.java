package org.wall.mo.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.wall.mo.database.RowMapper;
import org.wall.mo.database.SQLiteTemplate;
import org.wall.mo.database.WalleContentValues;
import org.wall.mo.database.WalleDBManager;
import org.wall.mo.database.bean.DemoBean;
import org.wall.mo.utils.thread.ExCallable;
import org.wall.mo.utils.thread.ExRunnable;
import org.wall.mo.utils.thread.SQLiteThreadExecutor;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class DemoDAO implements IBaseDAO<DemoBean> {
    @Override
    public String getTableName() {
        return DemoBean.Constants.TABLE.getTableName();
    }

    @Override
    public ContentValues getContentValues(DemoBean bean) {
        WalleContentValues walleContentValues = new WalleContentValues();
        walleContentValues.add(DemoBean.Constants.ID, bean.getId());
        return walleContentValues.create();
    }

    @Override
    public long insert(DemoBean bean) {
        SQLiteDatabase sqLiteDatabase = WalleDBManager.getInstance().openDatabase();
        long insert = SQLiteTemplate.getInstance().insert(sqLiteDatabase, getTableName(), getContentValues(bean));
        return insert;
    }

    /**
     * 同步获取
     */
    public int doSync() {
        Future<Integer> submit = SQLiteThreadExecutor.getExecutor().submit(new ExCallable<Integer>() {
            @Override
            public Integer callEx() {
                //执行
                return null;
            }

            @Override
            public void exMsg(String errorMsg) throws RuntimeException {
                //存在异常
            }
        });
        try {
            return submit.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 异步执行
     */
    public void doAsync() {
        SQLiteThreadExecutor.getExecutor().execute(new ExRunnable() {
            @Override
            public void runEx() {
                //执行
            }

            @Override
            public void exMsg(String errorMsg) throws RuntimeException {
                //存在异常
            }
        });
    }

    @Override
    public long update(DemoBean bean) {
        return 0;
    }

    @Override
    public void insertOrUpdate(DemoBean bean) {

    }

    @Override
    public long deleteAll() {
        return 0;
    }

    @Override
    public DemoBean findBy_Id(int _id) {
        return null;
    }

    @Override
    public DemoBean findById(int id) {
        return null;
    }

    @Override
    public List<DemoBean> findAll() {
        return null;
    }

    @Override
    public RowMapper<DemoBean> getRowMapper() {
        RowMapper<DemoBean> demoBeanRowMapper = new RowMapper<DemoBean>() {
            @Override
            public DemoBean mapRow(Cursor cursor, int index) {
                return null;
            }
        };
        return demoBeanRowMapper;
    }
}
