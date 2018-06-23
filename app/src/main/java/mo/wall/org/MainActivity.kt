package mo.wall.org

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import org.wall.mo.utils.cache.ACache

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //测试是否正常
        ACache.get(this).put("aaa", "aaa");
        val asString = ACache.get(this).getAsString("aaa");
        Toast.makeText(this, asString, Toast.LENGTH_SHORT).show();
    }
}