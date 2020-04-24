package mo.wall.org.dialog


import android.os.Bundle
import android.content.Context
import android.os.Message
import android.view.View

import mo.wall.org.databinding.FragmentMyDialogBinding

import org.wall.mo.base.mvp.BaseMVPMaxLifecycleFragment

import mo.wall.org.R
import okhttp3.Call
import org.wall.mo.http.CallXLife
import org.wall.mo.http.NetCallX
import org.wall.mo.http.OkHttpX
import java.lang.Exception


class MyDialogFragment : BaseMVPMaxLifecycleFragment<MyDialogContract.View,
        MyDialogContract.Presenter,
        FragmentMyDialogBinding,
        MyDialogAcceptPar>(),
        MyDialogContract.View {

    override fun showInfoDialog(msg: String?) {

    }


    override fun initView(rootView: View, savedInstanceState: Bundle?) {
        super.initView(rootView, savedInstanceState)
        //        lifecycle.addObserver()
        //        viewLifecycleOwner.lifecycle.addObserver()

        //https://www.wandouip.com/t5i234203/

        /**
         *
        【种瓜达人】广州-落后后-落后与贫困交加的单身人民群众 14:36:35
        aactivity用this
        【种瓜达人】广州-落后后-落后与贫困交加的单身人民群众 14:36:41
        fragment用owner
         */
        mCallXLife = CallXLife.getCallLife(viewLifecycleOwner.lifecycle)
    }

    override fun createPresenter(): MyDialogContract.Presenter {
        return MyDialogPresenter()
    }


    override fun onAbsV4Attach(context: Context) {

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_my_dialog
    }


    override fun getTopBarTitleViewId(): Int {
        return 0
    }

    override fun getTopBarBackViewId(): Int {
        return 0
    }

    lateinit var mCallXLife: CallXLife

    override fun onFragmentFirstVisible() {


        mViewDataBinding?.message?.setOnClickListener {

            OkHttpX.getInstance(activity).setDebug(true)
            val map = mutableMapOf<String, String>()
            map.put("aa", "ccc")
            OkHttpX.getInstance(activity).postJsonAsync("https://www.pyhtech.com/appin", map,
                    object : NetCallX(mCallXLife) {
                        override fun failed(call: Call?, e: Exception?) {
                        }

                        override fun success(call: Call?, `object`: String?) {
                        }

                    });
        }
    }

    /**
     * 会通过activity的onIntent传值过来
     *
     * @param bundle
     */
    override fun loadIntentData(bundle: Bundle) {

    }

    override fun handleSubMessage(msg: Message) {

    }

    override fun showShortToast(msg: String) {

    }

    override fun showLongToast(msg: String) {

    }

    override fun showDialog(msg: String) {

    }

    override fun hideDialog() {

    }

    override fun statusLoadingView() {

    }

    override fun statusNetWorkView() {

    }

    override fun statusErrorView(type: Int, msg: String) {

    }

    override fun statusContentView() {

    }

    companion object {
        @JvmStatic
        fun newInstance(args: Bundle?): MyDialogFragment {
            val fragment = MyDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }

}
