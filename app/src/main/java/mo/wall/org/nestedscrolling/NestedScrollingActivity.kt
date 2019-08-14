package mo.wall.org.nestedscrolling

import android.os.Bundle
import android.os.Message
import mo.wall.org.R
import mo.wall.org.base.BaseAppCompatActivity

class NestedScrollingActivity : BaseAppCompatActivity() {
    override fun handleMessageAct(msg: Message?) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nested_scrolling)
    }
}
