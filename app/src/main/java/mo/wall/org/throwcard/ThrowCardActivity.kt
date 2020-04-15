package mo.wall.org.throwcard

import android.os.Build
import android.os.Bundle
import android.os.Message
import android.view.View
import android.view.ViewPropertyAnimator
import mo.wall.org.R
import mo.wall.org.base.BaseAppCompatActivity

class ThrowCardActivity : BaseAppCompatActivity() {


    private lateinit var mView: View

    var animate: ViewPropertyAnimator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_throw_card)

        mView = findViewById<View>(R.id.view)

        animate = mView.animate()

        mView.setOnClickListener {

            animate?.apply {
                rotation(-45f)
                        .translationX(-mView.width.toFloat() * 2)
                        .translationY(mView.height.toFloat())
                        .setDuration(10 * 1000)
                        .start()
            }

        }
    }


    override fun onDestroy() {
        super.onDestroy()
        animate?.setListener(null)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            animate?.setUpdateListener(null)
        }
        animate?.cancel()
    }

    override fun handleMessageAct(msg: Message?) {
    }

}
