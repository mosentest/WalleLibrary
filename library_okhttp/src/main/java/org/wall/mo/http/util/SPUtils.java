package org.wall.mo.http.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

import static android.R.id.edit;

/**
 * 对SharedPreference的封装
 * 在包名目录下创建一个shared_pres目录，并维护一个config.xml文件
 * 所有数据的读取和存入都是对这个文件的操作
 * Created by Administrator on 2017/6/15.
 */

public class SPUtils {

    private SharedPreferences sp = null;

    private volatile static SPUtils instance;

    private Context ctx;

    public static SPUtils getInstance(Context ctx) {
        if (instance == null) {
            synchronized (SPUtils.class) {
                if (instance == null) {
                    instance = new SPUtils(ctx);
                }
            }
        }
        return instance;
    }

    public SPUtils(Context ctx) {
        sp = ctx.getSharedPreferences(ctx.getPackageName() + "_walle_okhttp_config", Context.MODE_PRIVATE);
    }

    /**
     * 将一个boolean值存入sp文件中
     *
     * @param key   存储节点名称
     * @param value 存储节点的值
     */
    public void putBoolean(String key, boolean value) {
        sp.edit().putBoolean(key, value).commit();//获取sp编辑器,放入bool值，并提交
    }

    /**
     * 根据key读取一个boolean值value，没有的话使用defvalue代替
     *
     * @param key
     * @param defValue
     */
    public boolean getBoolean(String key, boolean defValue) {
        boolean b = sp.getBoolean(key, defValue);
        return b;
    }

    /**
     * 将一个String值存入sp文件中
     *
     * @param key   存储节点名称
     * @param value 存储节点的值
     */
    public void putString(String key, String value) {
        sp.edit().putString(key, value).commit();
    }

    /**
     * 从sp中根据key取出String值
     *
     * @param key      存储节点名称
     * @param defValue 存储节点默认值
     * @return string
     */
    public String getString(String key, String defValue) {
        String string = sp.getString(key, defValue);
        return string;

    }

    /**
     * 移除sp中的一个节点
     * <p>
     * 环境
     *
     * @param key 节点名称
     */
    public void removeFromSP(String key) {
        final SharedPreferences.Editor edit = sp.edit();
        edit.remove(key).commit();
    }

    /**
     * 从sp中根据key取出int值
     *
     * @param key
     * @param defValue
     * @return
     */
    public int getInt(String key, int defValue) {
        int i = sp.getInt(key, defValue);
        return i;

    }

    /**
     * 将一个int值存入sp文件中
     *
     * @param key
     * @param value
     */
    public void putInt(String key, int value) {
        sp.edit().putInt(key, value).commit();
    }

    /**
     * 从sp中根据key取出float值
     *
     * @param key
     * @param defValue
     * @return
     */
    public float getFloat(String key, float defValue) {
        float i = sp.getFloat(key, defValue);
        return i;
    }

    /**
     * 将一个float值存入sp文件中
     *
     * @param key
     * @param value
     */
    public void putFloat(String key, float value) {
        sp.edit().putFloat(key, value).commit();
    }

    /**
     * 从sp中根据key取出int值
     *
     * @param key
     * @param defValue
     * @return
     */
    public Set<String> getStringSet(String key, Set<String> defValue) {
        Set<String> sets = sp.getStringSet(key, defValue);
        return sets;

    }

    /**
     * 将一个int值存入sp文件中
     *
     * @param key
     * @param sets
     */
    public void putStringSet(String key, Set<String> sets) {
        sp.edit().putStringSet(key, sets).commit();
    }


}