package mo.wall.org.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mo.wall.org.R

/**
 * 作者 create by moziqi on 2018/6/30
 * 邮箱 709847739@qq.com
 * 说明
 **/
class MainAdapter_Kotiln : androidx.recyclerview.widget.RecyclerView.Adapter<MainAdapter_Kotiln.Holder> {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        var view: View = LayoutInflater.from(context)
                .inflate(R.layout.activity_main_item_content, null, false);
        var holder = Holder(view)
        return holder
    }

    override fun getItemCount(): Int {
        return lists!!.size;
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
    }

    var context: Context? = null
    var lists: ArrayList<Entity>? = null

    constructor(context: Context?) : super() {
        this.context = context
    }

    public class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }


    fun setData(lists: ArrayList<Entity>?) {
        this.lists = lists;
        notifyDataSetChanged()
    }

}