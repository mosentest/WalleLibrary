package mo.wall.org.circlepercent

import android.os.Bundle
import android.os.Message
import mo.wall.org.R
import mo.wall.org.base.BaseAppCompatActivity
import java.util.*

class CirclePercentActivity : BaseAppCompatActivity() {

    private lateinit var mCpv: CirclePercentView


    override fun handleMessageAct(msg: Message?) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circle_percent)
        mCpv = findViewById<CirclePercentView>(R.id.cpv)

        mCpv.setTotalNum(12)
                .setCirclePercentDatas(
                        Arrays.asList(
                                CirclePercentView.createCirclePercentData(1, "我是红色人", resources.getColor(R.color.google_red)),
                                CirclePercentView.createCirclePercentData(2, "我是绿色人", resources.getColor(R.color.google_green)),
                                CirclePercentView.createCirclePercentData(3, "我是黄色人", resources.getColor(R.color.google_yellow)),
                                CirclePercentView.createCirclePercentData(3, "我是蓝色人", resources.getColor(R.color.google_blue)),
                                CirclePercentView.createCirclePercentData(2, "我不知道自己干嘛", resources.getColor(R.color.colorAccent)),
                                CirclePercentView.createCirclePercentData(1, "我好像是什么颜色", resources.getColor(R.color.colorPrimaryDark))))
                .start()
    }


}
