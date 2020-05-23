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
    private lateinit var mDstOut: DstOutView


    private var progress: Int? = 2

    var animate: ViewPropertyAnimator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        ThinkingFactory.rep(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_throw_card)

        mView = findViewById<View>(R.id.view)


        mDstOut = findViewById<DstOutView>(R.id.dstOut)

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
        var runnable = object : Runnable {
            override fun run() {
                progress = progress!! + 1
                mDstOut.setProgress(progress!!)
                handler.postDelayed(this, 500)
            }
        }
        handler.postDelayed(runnable, 500)
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
