package mo.wall.org.main

import android.content.ComponentName
import android.content.Intent
import android.os.*
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.text.TextUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import mo.wall.org.R
import mo.wall.org.apkinfo.ApkInfoActivity
import mo.wall.org.autodensity.AutoDensityActivity
import mo.wall.org.behavior.BehaviorActivity
import mo.wall.org.camera1.MedioRecorderCamera1Activity
import mo.wall.org.camera2.Camera2Activity
import mo.wall.org.camera2.CameraV2GLSurfaceViewActivity
import mo.wall.org.circlepercent.CirclePercentActivity
import mo.wall.org.datausage.DataUsageSummaryActivity
import mo.wall.org.devicemanager.DeviceManagerActivity
import mo.wall.org.dialog.MyDialogActivity
import mo.wall.org.dropdownmenu.DropDownMenuActivity
import mo.wall.org.markerview.MarkerViewAct
import mo.wall.org.nestedscrolling.NestedScrollingActivity
import mo.wall.org.nodisplay.NoDisplayActivity
import mo.wall.org.ntp.NtpActivity
import mo.wall.org.opengl.OpenGLActivity
import mo.wall.org.opengl2.OpenGLES20Activity
import mo.wall.org.scroll.ScrollActivity
import mo.wall.org.statusbar.StatusbarActivity
import mo.wall.org.statusbar2.Statusbar2Activity
import mo.wall.org.throwcard.ThrowCardActivity
import mo.wall.org.webviewinscrollview.WebInScrollActivity
import org.wall.mo.base.helper.StartActivityCompat
import org.wall.mo.compat.statusbar.StatusBarUtil
import mo.wall.org.nestedrecyclerview.NestedRecyclerViewActivity


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

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        StatusBarUtil.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimaryDark))
        StatusBarUtil.setStatusBarDarkTheme(this, false)


        rvMain = findViewById(R.id.rv_main)
        rvMain!!.layoutManager = LinearLayoutManager(this);

        adapter = MainAdapter(this)
        rvMain!!.adapter = adapter;


        mainViewModel.loadData("mo")


        mainViewModel.datas.observe(this, Observer {
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