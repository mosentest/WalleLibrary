package mo.wall.org.nestedscrolling

import android.os.Bundle
import android.os.Message
import androidx.core.content.ContextCompat
import android.widget.TextView
import android.widget.Toast
import mo.wall.org.R
import mo.wall.org.base.BaseAppCompatActivity
import org.wall.mo.compat.statusbar.StatusBarUtil

class NestedScrollingActivity : BaseAppCompatActivity() {

    private lateinit var mTvAAA: TextView

    override fun handleMessageAct(msg: Message?) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_nested_scrolling)

        mTvAAA = findViewById<TextView>(R.id.tvAAA)

        mTvAAA.setOnClickListener {
            Toast.makeText(applicationContext, "AA", Toast.LENGTH_SHORT).show()
        }
    }
}
