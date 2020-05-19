package org.wall.mo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 作者 : moziqi
 * 邮箱 : 709847739@qq.com
 * 时间   : 2020/5/19-16:19
 * desc   :
 * 参考这个工具
 * https://www.jianshu.com/p/38efc0ccc218
 * version: 1.0
 */
public class SearchHistoryUtils {

    private final static String PREFERENCE_NAME = "sp_search_history";

    /**
     * 健康咨询的key
     */
    public final static String SEARCH_HEALTH_HISTORY = "search_health_history";

    /**
     * 健康咨询最多5个
     */
    public final static int TOTAL_HEALTH = 5;

    // 保存搜索记录
    public static void saveSearchHistory(Context context, String inputText, String sp_key, int total) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        if (TextUtils.isEmpty(inputText)) {
            return;
        }
        String longHistory = sp.getString(sp_key, "");  //获取之前保存的历史记录
        String[] tmpHistory = longHistory.split(","); //逗号截取 保存在数组中
        List<String> historyList = new ArrayList<String>(Arrays.asList(tmpHistory)); //将改数组转换成ArrayList
        SharedPreferences.Editor editor = sp.edit();
        if (historyList.size() > 0) {
            //1.移除之前重复添加的元素
            for (int i = 0; i < historyList.size(); i++) {
                if (inputText.equals(historyList.get(i))) {
                    historyList.remove(i);
                    break;
                }
            }
            historyList.add(0, inputText); //将新输入的文字添加集合的第0位也就是最前面(2.倒序)
            if (historyList.size() > total) {
                historyList.remove(historyList.size() - 1); //3.最多保存8条搜索记录 删除最早搜索的那一项
            }
            //逗号拼接
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < historyList.size(); i++) {
                sb.append(historyList.get(i) + ",");
            }
            //保存到sp
            editor.putString(sp_key, sb.toString());
            editor.commit();
        } else {
            //之前未添加过
            editor.putString(sp_key, inputText + ",");
            editor.commit();
        }
    }

    //获取搜索记录
    public static List<String> getSearchHistory(Context context, String sp_key) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        String longHistory = sp.getString(sp_key, "");
        String[] tmpHistory = longHistory.split(","); //split后长度为1有一个空串对象
        List<String> historyList = new ArrayList<String>(Arrays.asList(tmpHistory));
        if (historyList.size() == 1 && historyList.get(0).equals("")) { //如果没有搜索记录，split之后第0位是个空串的情况下
            historyList.clear();  //清空集合，这个很关键
        }
        return historyList;
    }

    public static void clear(Context context, String sp_key) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        sp.edit().remove(sp_key).commit();
    }
}
