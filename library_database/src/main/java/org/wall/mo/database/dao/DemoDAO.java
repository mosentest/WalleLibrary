package org.wall.mo.database.dao;

import android.content.ContentValues;

import org.wall.mo.database.SQLiteTemplate;
import org.wall.mo.database.bean.DemoBean;
import org.wall.mo.utils.thread.ExCallable;
import org.wall.mo.utils.thread.ExRunnable;
import org.wall.mo.utils.thread.SQLiteThreadExecutor;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class DemoDAO implements BaseDAO<DemoBean> {
    @Override
    public String getTableName() {
        return null;
    }

    @Override
    public ContentValues getContentValues(DemoBean bean) {
        return null;
    }

    @Override
    public long insert(DemoBean bean) {
        return 0;
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
    public SQLiteTemplate.RowMapper<DemoBean> getRowMapper() {
        return null;
    }
}
