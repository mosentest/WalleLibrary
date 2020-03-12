package mo.wall.org.main

import android.content.ComponentName
import android.content.Intent
import android.os.*
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.text.TextUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.runtime.Permission
import mo.wall.org.R
import org.wall.mo.base.helper.StartActivityCompat
import org.wall.mo.compat.statusbar.StatusBarUtil
import org.wall.mo.utils.log.WLog


class MainActivity : AppCompatActivity() {


    lateinit var mainViewModel: MainViewModel

    companion object {

    }

    var handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
        }
    }

    var rvMain: androidx.recyclerview.widget.RecyclerView? = null;

    var lists: ArrayList<Entity>? = null;

    lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AndPermission.with(this).runtime().permission(Permission.Group.CAMERA,
                Permission.Group.STORAGE,
                arrayOf(Permission.READ_PHONE_STATE),
                Permission.Group.LOCATION,
                Permission.Group.MICROPHONE)
                .onGranted({

                }).onDenied({

                }).start()

        //mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        StatusBarUtil.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimaryDark))
        StatusBarUtil.setStatusBarDarkTheme(this, false)


        rvMain = findViewById(R.id.rv_main)
        rvMain!!.layoutManager = LinearLayoutManager(this);

        adapter = MainAdapter(this)
        rvMain!!.adapter = adapter;


        mainViewModel.loadData("mo")


        mainViewModel.datas.observe(this, Observer {

            WLog.i("mk", " in main activity setItem " + Thread.currentThread().name)

            adapter.setItems(it)
        })

        adapter.setOnItemClickListener { view, pos ->
            val item = adapter.items.get(pos)
            val clazz = item.clazz
            if (!TextUtils.isEmpty(clazz)) {
                var intent = Intent()
                var componet = ComponentName(applicationContext.packageName, clazz)
                intent.setComponent(componet)
                intent.putExtra("title", item.title)
//                startActivity(intent)
                StartActivityCompat.startActivity(this, null, item.title, true, intent);
            }
        }

        //测试是否正常
        //ACache.get(this).put("aaa", "aaa");
        //val asString = ACache.get(this).getAsString("aaa");
    }
}