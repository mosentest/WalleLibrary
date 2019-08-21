package mo.wall.org.circlepercent

import android.os.Bundle
import android.os.Message
import mo.wall.org.R
import mo.wall.org.base.BaseAppCompatActivity
import java.util.*

class CirclePercentActivity : BaseAppCompatActivity() {

    private lateinit var mCpv: CirclePercentLineView

    private lateinit var mCv: ColumnarView

    override fun handleMessageAct(msg: Message?) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circle_percent)


        mCpv = findViewById<CirclePercentLineView>(R.id.cpv)

        mCv = findViewById<ColumnarView>(R.id.cv)

        mCpv.setTotalNum(100)
                .setCirclePercentDatas(
                        Arrays.asList(
                                CirclePercentLineView.createCirclePercentData(5, "我不红色人我", resources.getColor(R.color.google_red)),
                                CirclePercentLineView.createCirclePercentData(5, "我是红色人我", resources.getColor(R.color.google_green)),
                                CirclePercentLineView.createCirclePercentData(20, "我好红色人我", resources.getColor(R.color.google_yellow)),
                                CirclePercentLineView.createCirclePercentData(20, "我人红色人我", resources.getColor(R.color.google_blue)),
                                CirclePercentLineView.createCirclePercentData(8, "我你红色人我", resources.getColor(R.color.colorAccent)),
                                CirclePercentLineView.createCirclePercentData(4, "我走红色人我", resources.getColor(R.color.google_yellow)),
                                CirclePercentLineView.createCirclePercentData(4, "我开红色人我", resources.getColor(R.color.google_green)),
                                CirclePercentLineView.createCirclePercentData(4, "我别红色人我", resources.getColor(R.color.google_red)),
                                CirclePercentLineView.createCirclePercentData(4, "我碰红色人我", resources.getColor(R.color.google_green)),
                                CirclePercentLineView.createCirclePercentData(20, "我我红色人我", resources.getColor(R.color.colorPrimaryDark))))
                .start()

        mCv.setTotalCount(60f).setColumnarDatas(
                Arrays.asList(
                        ColumnarView.createColumnarData("我爱中", 30f, resources.getColor(R.color.google_red)),
                        ColumnarView.createColumnarData("2我爱中国，爱共产党，我爱中国", 40f, resources.getColor(R.color.google_green)),
                        ColumnarView.createColumnarData("3我爱中国，爱", 50f, resources.getColor(R.color.google_yellow)),
                        ColumnarView.createColumnarData("4我爱中国，", 60f, resources.getColor(R.color.google_blue)),
                        ColumnarView.createColumnarData("5我爱中国，爱共", 50f, resources.getColor(R.color.colorAccent))
                )
        );
    }


}
