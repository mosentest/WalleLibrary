package mo.wall.org.circlepercent

import android.os.Bundle
import android.os.Message
import mo.wall.org.R
import mo.wall.org.base.BaseAppCompatActivity
import java.util.*

class CirclePercentActivity : BaseAppCompatActivity() {

    private lateinit var mCpv: CirclePercentView

    private lateinit var mCv: ColumnarView

    override fun handleMessageAct(msg: Message?) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circle_percent)


        mCpv = findViewById<CirclePercentView>(R.id.cpv)

        mCv = findViewById<ColumnarView>(R.id.cv)

        mCpv.setCirclePercentDatas(
                Arrays.asList(
                        CirclePercentView.createCirclePercentData("我不红色人我", resources.getColor(R.color.google_red), 0.08f, "10%", true),
                        CirclePercentView.createCirclePercentData("我是红色人我", resources.getColor(R.color.google_green), 0.08f, "10%", true),
                        CirclePercentView.createCirclePercentData("我好红色人我", resources.getColor(R.color.google_yellow), 0.08f, "10%", true),
                        CirclePercentView.createCirclePercentData("我人红色人我", resources.getColor(R.color.google_blue), 0.08f, "10%", true),
                        CirclePercentView.createCirclePercentData("我你红色人我", resources.getColor(R.color.colorAccent), 0.08f, "10%", true),
                        CirclePercentView.createCirclePercentData("我走红色人我", resources.getColor(R.color.google_yellow), 0.08f, "10%", true),
                        CirclePercentView.createCirclePercentData("我开红色人我", resources.getColor(R.color.google_green), 0.08f, "10%", true),
                        CirclePercentView.createCirclePercentData("我别红色人我", resources.getColor(R.color.google_red), 0.08f, "10%", true),
                        CirclePercentView.createCirclePercentData("我碰红色人我", resources.getColor(R.color.colorPrimaryDark), 0.08f, "10%", true),
//                        CirclePercentView.createCirclePercentData("我我红色人我", resources.getColor(R.color.colorPrimaryDark), 0.1f, "10%", true),
//                        CirclePercentView.createCirclePercentData("我不红色人我", resources.getColor(R.color.google_red), 0.3f, "3%", true),
//                        CirclePercentView.createCirclePercentData("我是红色人我", resources.getColor(R.color.google_green), 0.3f, "3%", true),
//                        CirclePercentView.createCirclePercentData("我好红色人我", resources.getColor(R.color.google_yellow), 0.3f, "3%", true),
//                        CirclePercentView.createCirclePercentData("我人红色人我", resources.getColor(R.color.google_blue), 0.3f, "3%", true),
//                        CirclePercentView.createCirclePercentData("我你红色人我", resources.getColor(R.color.colorAccent), 0.3f, "3%", true),
//                        CirclePercentView.createCirclePercentData("我走红色人我", resources.getColor(R.color.google_yellow), 0.3f, "3%", true),
                        CirclePercentView.createCirclePercentData("我开红色人我", resources.getColor(R.color.google_green), 0.08f, "10%", true),
                        CirclePercentView.createCirclePercentData("我别红色人我", resources.getColor(R.color.google_red), 0.08f, "10%", true),
                        CirclePercentView.createCirclePercentData("我碰红色人我", resources.getColor(R.color.google_green), 0.08f, "10%", true)))
                .start()

        mCv.setTotalCount(70f).setColumnarDatas(
                Arrays.asList(
                        ColumnarView.createColumnarData("我爱中", 30f, resources.getColor(R.color.google_red)),
                        ColumnarView.createColumnarData("2我爱中国，爱共产党，我爱中国qeaaa", 40f, resources.getColor(R.color.google_green)),
                        ColumnarView.createColumnarData("3我爱中国，爱", 50f, resources.getColor(R.color.google_yellow)),
                        ColumnarView.createColumnarData("4我爱中国，", 70f, resources.getColor(R.color.google_blue)),
                        ColumnarView.createColumnarData("5我爱中国，爱共", 50f, resources.getColor(R.color.colorAccent))
                )
        );
    }


}
