package mo.wall.org.statusbar

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import mo.wall.org.R
import mo.wall.org.base.BaseAppCompatActivity

class StatusbarActivity : BaseAppCompatActivity() {

    override fun handleMessageAct(msg: Message?) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statusbar)
    }
}
