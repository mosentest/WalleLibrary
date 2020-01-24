package org.wall.mo.http.cookies;

import android.content.Context;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * https://github.com/franmontiel/PersistentCookieJar
 * <p>
 * https://www.cnblogs.com/shenchanghui/p/6409699.html
 * <p>
 * 自动管理Cookies
 */
public class CookiesManager implements CookieJar {

    private Context mContext;


    private final PersistentCookieStore cookieStore;

    public CookiesManager(Context context) {
        this.mContext = context;
        cookieStore = new PersistentCookieStore(mContext);
    }


    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {
                cookieStore.add(url, item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url);
        return cookies;
    }

    public PersistentCookieStore getCookieStore() {
        return cookieStore;
    }
}