package org.wall.mo.utils.ua;

import java.lang.reflect.Field;
import java.util.Locale;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

/**
 *
 * @author moziqi
 */
public class UAHelper {

	private final static String TAG = "UAHelper";

	/**
	 * UserAgent.
	 */
	private static String userAgent;

	/**
	 * Get the singleton UA.
	 *
	 * @return String.
	 * @see #newInstance()
	 */
	public static String instance(Context context) {
		if (TextUtils.isEmpty(userAgent))
			userAgent = newInstance(context);
		return userAgent;
	}

	/**
	 * Get User-Agent of System.
	 *
	 * @return UA.
	 */
	public static String newInstance(Context context) {
		String webUserAgent = null;
		try {
			Log.i(TAG, "UserAgentHelper");
			Class<?> sysResCls = Class.forName("com.android.internal.R$string");
			Field webUserAgentField = sysResCls.getDeclaredField("web_user_agent");
			Integer resId = (Integer) webUserAgentField.get(null);
			webUserAgent = context.getResources().getString(resId);
		} catch (Exception e) {
			webUserAgent = null;
		}
		Log.i(TAG, "UserAgentHelper>>>>webUserAgent$$" + webUserAgent);
		if (TextUtils.isEmpty(webUserAgent) || !webUserAgent.startsWith("Mozilla")) {
			webUserAgent = "Mozilla/5.0 (Linux; U; Android %s) AppleWebKit/534.30 (KHTML, like Gecko) Version/5.0 %sSafari/534.30";
			if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB_MR2) {
				webUserAgent = "Mozilla/5.0 (Linux; U; Android %s) AppleWebKit/534.13 (KHTML, like Gecko) Version/5.0 %sSafari/534.13";
			} else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
				webUserAgent = "Mozilla/5.0 (Linux; U; Android %s) AppleWebKit/533.1 (KHTML, like Gecko) Version/5.0 %sSafari/533.1";
			}
		}

		StringBuffer buffer = new StringBuffer();
		// Add version
		final String version = Build.VERSION.RELEASE;
		if (version.length() > 0) {
			buffer.append(version);
		} else {
			// default to "1.0"
			buffer.append("1.0");
		}
		buffer.append("; ");

		Locale locale = Locale.getDefault();
		final String language = locale.getLanguage();
		if (language != null) {
			buffer.append(language.toLowerCase(locale));
			final String country = locale.getCountry();
			if (!TextUtils.isEmpty(country)) {
				buffer.append("-");
				buffer.append(country.toLowerCase(locale));
			}
		} else {
			// default to "en"
			buffer.append("en");
		}
		// add the model for the release build
		if ("REL".equals(Build.VERSION.CODENAME)) {
			if (Build.MODEL.length() > 0) {
				buffer.append("; ");
				buffer.append(Build.MODEL);
			}
		}
		if (Build.ID.length() > 0) {
			buffer.append(" Build/");
			buffer.append(Build.ID);
		}
		return String.format(webUserAgent, buffer, "Mobile ");
	}

}