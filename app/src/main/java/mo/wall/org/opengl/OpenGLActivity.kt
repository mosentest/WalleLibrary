package mo.wall.org.opengl

import android.os.Bundle
import android.os.Message
import android.widget.ImageView
import mo.wall.org.R
import mo.wall.org.base.BaseAppCompatActivity
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.graphics.SurfaceTexture
import android.hardware.Camera
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.TextureView
import java.io.IOException
import android.graphics.ImageFormat


class OpenGLActivity : BaseAppCompatActivity(), TextureView.SurfaceTextureListener {


    private var findBackCamera: Int = -1;

    private lateinit var mIv: ImageView

    private lateinit var mSurView: SurfaceView

    private lateinit var mTexture: TextureView


    private lateinit var camera: Camera

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture?, width: Int, height: Int) {

    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture?) {
    }

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture?): Boolean {
        return false
    }

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture?, width: Int, height: Int) {
        try {
            if (camera != null) {
                camera.setPreviewTexture(surface)
                FixCameraUtils.setCameraDisplayOrientation(this, findBackCamera, camera)
                camera.startPreview()

                camera.autoFocus { success, camera ->

                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }


    override fun handleMessageAct(msg: Message?) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_gl)

        mIv = findViewById<ImageView>(R.id.iv)


        val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
        mIv.setImageBitmap(bitmap)


        mSurView = findViewById<SurfaceView>(R.id.surView)

        mSurView.holder.addCallback(object : SurfaceHolder.Callback {

            override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

            }

            override fun surfaceDestroyed(holder: SurfaceHolder?) {
            }

            override fun surfaceCreated(holder: SurfaceHolder?) {
                if (holder != null) {
                    var paint = Paint()
                    paint.isAntiAlias = true
                    paint.style = Paint.Style.STROKE
                    var canvas = holder.lockCanvas()
                    canvas.drawBitmap(bitmap, 0f, 0f, paint)
                    holder.unlockCanvasAndPost(canvas)
                }
            }

        })



        mTexture = findViewById<TextureView>(R.id.texture)

        mTexture.surfaceTextureListener = this

        findBackCamera = FixCameraUtils.findBackCamera()
        camera = Camera.open(findBackCamera)
        FixCameraUtils.setCameraDisplayOrientation(this, findBackCamera, camera)

        val parameters = camera.parameters
        parameters.previewFormat = ImageFormat.NV21
        //如果不设置会按照系统默认配置最低160x120分辨率
        //val displayMetrics = resources.displayMetrics
        //parameters.setPictureSize(displayMetrics.widthPixels, displayMetrics.heightPixels)
        camera.parameters = parameters

        camera.setPreviewCallback(object : Camera.PreviewCallback {
            override fun onPreviewFrame(data: ByteArray?, camera: Camera?) {

            }

        })
    }


    override fun onStart() {
        super.onStart()
        if (camera != null) {
            camera.startPreview()
        }
    }


    override fun onStop() {
        super.onStop()
        if (camera != null) {
            camera.stopPreview()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        if (camera != null) {
            camera.release()
        }
    }

}
