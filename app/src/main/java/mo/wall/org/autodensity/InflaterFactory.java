package mo.wall.org.autodensity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import java.lang.reflect.Constructor;

public class InflaterFactory implements LayoutInflater.Factory {

    private LayoutInflater.Factory mBaseFactory;
    private ClassLoader mClassLoader;

    public InflaterFactory(LayoutInflater.Factory base, ClassLoader classLoader) {
        if (null == classLoader) {
            throw new IllegalArgumentException("classLoader is null");
        }
        mBaseFactory = base;
        mClassLoader = classLoader;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attributeSet) {
        View v = null;
        try {
            Class<?> clazz = mClassLoader.loadClass(name);
            Constructor c = clazz.getConstructor(Context.class, AttributeSet.class);
            v = (View) c.newInstance(context, attributeSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return v != null ? v : (mBaseFactory != null ? mBaseFactory.onCreateView(name, context, attributeSet) : null);
    }
}