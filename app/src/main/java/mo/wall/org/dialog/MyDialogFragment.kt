package mo.wall.org.dialog


import android.os.Bundle
import android.content.Context
import android.os.Message
import android.view.View

import mo.wall.org.databinding.FragmentMyDialogBinding

import org.wall.mo.base.mvp.BaseMVPMaxLifecycleFragment

import mo.wall.org.R
import okhttp3.Call
import org.wall.mo.http.CallLife
import org.wall.mo.http.NetCall
import org.wall.mo.http.OkHttpX
import org.wall.mo.utils.log.WLog
import java.lang.Exception


class MyDialogFragment : BaseMVPMaxLifecycleFragment<MyDialogContract.Presenter,
        FragmentMyDialogBinding,
        MyDialogAcceptPar>(),
        MyDialogContract.View {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(mCallLife)
    }

    override fun initView(rootView: View, savedInstanceState: Bundle?) {
        super.initView(rootView, savedInstanceState)
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

    var mCallLife: CallLife = CallLife()

    override fun onFragmentFirstVisible() {
        mViewDataBinding.message.setOnClickListener {

            OkHttpX.getInstance(activity).setDebug(true)
            val map = mutableMapOf<String, String>()
            map.put("aa", "ccc")
            OkHttpX.getInstance(activity).postJsonAsync("https://www.pyhtech.com/appin", map, object : NetCall<String>() {
                override fun failed(call: Call?, e: Exception?) {
                    WLog.w("cccc", "ccc", e)
                }

                override fun getCallLife(): CallLife {
                    return mCallLife
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

    override fun onLoadDialogFail(flag: Int, failObj: Any) {

    }

    override fun onLoadToastFail(flag: Int, failObj: Any) {

    }


    override fun onCurDestroy() {
        lifecycle.removeObserver(mCallLife)
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
