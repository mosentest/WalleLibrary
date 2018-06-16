package org.wall.mo.database.dao;

import android.content.ContentValues;

import org.wall.mo.database.SQLiteTemplate;

import java.util.List;

/**
 * @author moziqi
 * @date 创建时间：2016年8月17日 下午6:20:19
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public interface BaseDAO<T> {
	
	public String getTableName();
	
	public ContentValues getContentValues(T bean);
	
	public long insert(T bean);

	public long update(T bean);
	
	public void  insertOrUpdate(T bean);

	public long deleteAll();

	public T findBy_Id(int _id);
	
	public T findById(int id);

	public List<T> findAll();
	
	public SQLiteTemplate.RowMapper<T> getRowMapper();
	
}
