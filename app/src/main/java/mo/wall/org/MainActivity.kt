package mo.wall.org

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import mo.wall.org.autodensity.AutoDensityActivity
import mo.wall.org.datausage.DataUsageSummaryActivity
import mo.wall.org.devicemanager.DeviceManagerActivity
import mo.wall.org.dropdownmenu.DropDownMenuActivity
import mo.wall.org.ntp.NtpActivity
import mo.wall.org.statusbar.StatusbarActivity
import mo.wall.org.statusbar2.Statusbar2Activity
import org.wall.mo.base.StartActivityCompat

class MainActivity : AppCompatActivity() {

    var handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
        }
    }

    var rvMain: RecyclerView? = null;

    var lists: ArrayList<Entity>? = null;

    var adapter: MainAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvMain = findViewById(R.id.rv_main)
        rvMain!!.layoutManager = LinearLayoutManager(this);

        adapter = MainAdapter(this)
        rvMain!!.adapter = adapter;

        lists = ArrayList()

        lists?.add(createEntity("",
                "Framework源码学习",
                0,
                ""))

        lists?.add(createEntity("这个功能需要系统区，这是基于4.4源码扣出来的,需要放在系统区",
                "流量监控API",
                1,
                DataUsageSummaryActivity().javaClass.name))

        lists?.add(createEntity("修改setting的ntp地址，这是基于4.4源码扣出来的",
                "NTP协议",
                1,
                NtpActivity().javaClass.name))

        lists?.add(createEntity("激活设备管理器，通过反射激活，需要放在系统区",
                "激活设备管理器",
                1,
                DeviceManagerActivity().javaClass.name))

        lists?.add(createEntity("",
                "UI学习",
                0,
                ""))

        lists?.add(createEntity("根据今日头条的方法调整和整理",
                "来自今日头条自适应方案",
                1,
                AutoDensityActivity().javaClass.name))

        lists?.add(createEntity("增加切换搜索功能，tab的文字和图片一起居中，还有单独为每个tab设置不同图片...来源：https://github.com/dongjunkun/DropDownMenu",
                "DropDownMenu",
                1,
                DropDownMenuActivity().javaClass.name))

        lists?.add(createEntity("不透明、有自定义标题的情况", "状态栏1", 1, StatusbarActivity().javaClass.name))
        lists?.add(createEntity("透明、有自定义标题的情况", "状态栏2", 1, Statusbar2Activity().javaClass.name))


        adapter!!.setItems(lists)

        adapter!!.setOnItemClickListener { view, pos ->
            val item = lists!!.get(pos)
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


    /**
     * 创建实体
     */
    fun createEntity(content: String, title: String, type: Int, clazz: String): Entity {
        val entity = Entity()
        entity.content = content
        entity.title = title
        entity.type = type
        entity.clazz = clazz
        return entity
    }
}