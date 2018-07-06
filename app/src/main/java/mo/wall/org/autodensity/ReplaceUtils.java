package mo.wall.org.autodensity;

import android.content.Context;
import android.content.res.Resources;

import java.lang.reflect.Field;

/**
 * 作者 create by moziqi on 2018/7/6
 * 邮箱 709847739@qq.com
 * 说明
 **/
public class ReplaceUtils {

    public static void replaceContextResources(Context context, Resources resources) {
        try {
            Field field = context.getClass().getDeclaredField("mResources");
            field.setAccessible(true);
            field.set(context, resources);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
