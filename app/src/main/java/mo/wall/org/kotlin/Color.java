package mo.wall.org.kotlin;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * https://www.jianshu.com/p/05ab8bd3c713
 */
@IntDef({
        Color.RED,
        Color.GREEN,
        Color.BLACK,
        Color.YELLOW
})
@Retention(RetentionPolicy.SOURCE)
public @interface Color {
    int RED = 1;
    int GREEN = 2;
    int BLACK = 3;
    int YELLOW = 4;
}