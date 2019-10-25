package mo.wall.org.throwcard

import android.os.Bundle
import android.os.Message
import android.view.View
import mo.wall.org.R
import mo.wall.org.base.BaseAppCompatActivity

class ThrowCardActivity : BaseAppCompatActivity() {


    private lateinit var mView: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_throw_card)

        mView = findViewById<View>(R.id.view)


        mView.setOnClickListener {

            mView.animate()
                    .rotation(-45f)
                    .translationX(-mView.width.toFloat() * 2)
                    .translationY(mView.height.toFloat())
                    .setDuration(10 * 1000)
                    .start()
        }
    }


    override fun handleMessageAct(msg: Message?) {
    }

}
