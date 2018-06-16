package org.wall.mo.database;

import android.database.Cursor;

/**
 * @param <T>
 * @author shimiso
 */
public interface RowMapper<T> {
    /**
     * @param cursor 游标
     * @param index  下标索引
     * @return
     */
    public T mapRow(Cursor cursor, int index);
}