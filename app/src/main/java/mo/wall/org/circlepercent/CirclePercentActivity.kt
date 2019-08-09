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
        mCpv.setCirclePercentDatas(Arrays.asList(
                CirclePercentView.createCirclePercentData(1, "11", resources.getColor(R.color.google_red)),
                CirclePercentView.createCirclePercentData(2,  "22", resources.getColor(R.color.google_green)),
                CirclePercentView.createCirclePercentData(3,  "33", resources.getColor(R.color.google_yellow)),
                CirclePercentView.createCirclePercentData(3,  "44", resources.getColor(R.color.google_blue)),
                CirclePercentView.createCirclePercentData(2,  "55", resources.getColor(R.color.colorAccent)),
                CirclePercentView.createCirclePercentData(1,  "66", resources.getColor(R.color.colorPrimaryDark))))
        mCpv.start()
    }


}
