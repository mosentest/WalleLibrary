package org.wall.mo.database;

import android.content.ContentValues;

public class WalleContentValues {

    private ContentValues contentValues;

    public WalleContentValues() {
        this.contentValues = new ContentValues();
    }

    public WalleContentValues add(String key, Object value) {
        if (value instanceof Integer) {
            contentValues.put(key, (Integer) value);
        } else if (value instanceof Float) {
            contentValues.put(key, (Float) value);
        } else if (value instanceof Long) {
            contentValues.put(key, (Long) value);
        } else if (value instanceof String) {
            contentValues.put(key, (String) value);
        } else if (value instanceof Short) {
            contentValues.put(key, (Short) value);
        } else if (value instanceof Byte) {
            contentValues.put(key, (Byte) value);
        } else if (value instanceof Double) {
            contentValues.put(key, (Double) value);
        } else if (value instanceof Boolean) {
            contentValues.put(key, (Boolean) value);
        } else if (value instanceof byte[]) {
            contentValues.put(key, (byte[]) value);
        } else {
            throw new RuntimeException("error type");
        }
        return this;
    }

    public ContentValues create() {
        return contentValues;
    }

}
