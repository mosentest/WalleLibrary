package mo.wall.org.dropdownmenu

import android.os.Bundle
import android.os.Message
import android.view.TextureView
import android.view.View
import android.widget.TextView
import mo.wall.org.R
import mo.wall.org.base.BaseAppCompatActivity
import org.wall.mo.dropdownmenu.DropDownMenu
import org.wall.mo.dropdownmenu.TabBean
import java.util.*


/**
 * 作者 create by moziqi on 2018/7/13
 * 邮箱 709847739@qq.com
 * 说明
 **/
class DropDownMenuActivity : BaseAppCompatActivity() {

    var dropDownMenu: DropDownMenu? = null;

    private val headers = ArrayList<TabBean>()//new String[]{"距离排序", "搜索", "筛选"};
    private val popupViews = ArrayList<View>()

    private var constellationView: View? = null
    private var sortView: View? = null
    private var selectView: View? = null
    private var searchContentView: View? = null

    override fun handleMessageAct(msg: Message?) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dropdownmenu)
        dropDownMenu = findViewById(R.id.dropDownMenu)


        constellationView = layoutInflater.inflate(R.layout.view_native_content, null)

        searchContentView = layoutInflater.inflate(R.layout.view_native_search, null)

        var tv_cancel = searchContentView!!.findViewById(R.id.tv_cancel) as TextView

        tv_cancel.setOnClickListener(View.OnClickListener {
            dropDownMenu!!.showTabMenu()
        })

        sortView = layoutInflater.inflate(R.layout.view_native_sort, null)

        selectView = layoutInflater.inflate(R.layout.view_native_select, null)

        val tabBeanSort = TabBean()
        tabBeanSort.name = "距离排序"
        tabBeanSort.menuSelectedIcon = R.mipmap.icon_push
        tabBeanSort.menuUnselectedIcon = R.mipmap.icon_pull
        headers.add(tabBeanSort)
        val tabBeanSearch = TabBean()
        tabBeanSearch.name = "搜索"
        tabBeanSearch.menuSelectedIcon = R.mipmap.icon_store_unsearch
        tabBeanSearch.menuUnselectedIcon = R.mipmap.icon_store_unsearch
        tabBeanSearch.type = 1
        headers.add(tabBeanSearch)
        val tabBeanChoice = TabBean()
        tabBeanChoice.name = "筛选"
        tabBeanChoice.menuSelectedIcon = R.mipmap.icon_store_choice
        tabBeanChoice.menuUnselectedIcon = R.mipmap.icon_store_unchoice
        headers.add(tabBeanChoice)


        popupViews.add(sortView!!)
        //这里增加空的view
        val searchPopView = View(this)
        popupViews.add(searchPopView)
        //这里增加空的view
        popupViews.add(selectView!!)

        dropDownMenu!!.setDropDownMenu(headers, searchContentView!!, popupViews, constellationView!!)

    }

}