package mo.wall.org

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import kotlinx.android.synthetic.main.activity_launcher.*
import org.wall.mo.compat.statusbar.StatusBar28

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class LauncherActivity : AppCompatActivity() {
    private val mHideHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        StatusBar28.splash(this)

        setContentView(R.layout.activity_launcher)
        mHideHandler.postDelayed({
            startActivity(Intent(this, MainActivity().javaClass))
            finish()
        }, 3000)
    }

    override fun onBackPressed() {
//        super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        mHideHandler.removeCallbacksAndMessages(null)
    }
}
