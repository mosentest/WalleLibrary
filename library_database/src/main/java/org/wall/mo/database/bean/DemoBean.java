package org.wall.mo.database.bean;


import android.provider.BaseColumns;

import org.wall.mo.database.Column;
import org.wall.mo.database.SQLiteTable;

public class DemoBean {
    private int _id;
    private int id; //id
    private int subId;//模块id

    public final static class Constants implements BaseColumns {

        private Constants() {
        }

        public static final String TABLE_NAME = "tb_demo";

        public static final String ID = "id";
        public static final String SUB_ID = "subId";

        public static final SQLiteTable TABLE = new SQLiteTable(TABLE_NAME)
                .addColumn(ID, Column.DataType.INTEGER)
                .addColumn(SUB_ID, Column.DataType.INTEGER);
    }


    public int get_id() {
        return _id;
    }


    public void set_id(int _id) {
        this._id = _id;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public int getSubId() {
        return subId;
    }


    public void setSubId(int subId) {
        this.subId = subId;
    }

    @Override
    public String toString() {
        return "DemoBean{" +
                "_id=" + _id +
                ", id=" + id +
                ", subId=" + subId +
                '}';
    }
}
