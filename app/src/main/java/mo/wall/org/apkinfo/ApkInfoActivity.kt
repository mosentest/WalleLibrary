package mo.wall.org.apkinfo

import android.os.Bundle
import android.os.Message
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import mo.wall.org.R
import mo.wall.org.base.BaseAppCompatActivity
import org.wall.mo.utils.GoogleAdIdUtils
import org.wall.mo.utils.thread.CacheThreadExecutor
import org.wall.mo.utils.thread.ExRunnable

/**
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019-11-27 12:59
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class ApkInfoActivity : BaseAppCompatActivity() {

    private lateinit var mEdPkg: EditText
    private lateinit var mEdAfp: EditText
    private lateinit var mEdAsha: EditText
    private lateinit var mEdSize: EditText


    private lateinit var mEdVerCode: EditText
    private lateinit var mEdVerName: EditText
    private lateinit var mEdAppName: EditText
    private lateinit var mEdMinsdkversion: EditText

    private lateinit var mBtn: Button


    override fun handleMessageAct(msg: Message?) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apkinfo)

        mEdPkg = findViewById<EditText>(R.id.ed_pkg)
        mEdAfp = findViewById<EditText>(R.id.ed_afp)
        mEdAsha = findViewById<EditText>(R.id.ed_asha)
        mEdSize = findViewById<EditText>(R.id.ed_size)


        mEdVerCode = findViewById<EditText>(R.id.ed_ver_code)
        mEdVerName = findViewById<EditText>(R.id.ed_ver_name)
        mEdAppName = findViewById<EditText>(R.id.ed_app_name)
        mEdMinsdkversion = findViewById<EditText>(R.id.ed_minsdkversion)

        mBtn = findViewById<Button>(R.id.btn)



        mBtn.setOnClickListener {
            val packageName = mEdPkg.text.toString().trim()
            if (TextUtils.isEmpty(packageName)) {
                return@setOnClickListener
            }
            val g = AFPUtils.G(this@ApkInfoActivity, packageName)
            Log.i("g", g)
            mEdAfp.setText(g)
            val g1 = ASHASUtils.G(this@ApkInfoActivity, packageName)
            mEdAsha.setText(g1)
            Log.i("g1", g1)

            val d = apk_sizeUtils.D(this@ApkInfoActivity, packageName)
            mEdSize.setText("" + d)
            Log.i("d", d.toString())


            mEdVerCode.setText("" + AppInfo.getVersionCode(this@ApkInfoActivity, packageName))

            mEdVerName.setText("" + AppInfo.getversionName(this@ApkInfoActivity, packageName))

            mEdAppName.setText("" + AppInfo.getAppName(this@ApkInfoActivity, packageName))

            mEdMinsdkversion.setText("" + AppInfo.getMinSdkVersion(this@ApkInfoActivity, packageName))
        }


        CacheThreadExecutor.getExecutor().execute(object : ExRunnable() {
            override fun exMsg(errorMsg: String?) {

            }

            override fun runEx() {
                val googleAdId = GoogleAdIdUtils.getGoogleAdId(applicationContext)
                Log.i("d", """googleAdId:$googleAdId""")
            }

        })

    }

}