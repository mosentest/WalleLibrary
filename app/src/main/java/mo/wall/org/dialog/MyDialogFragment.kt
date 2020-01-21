package mo.wall.org.dialog


import android.os.Bundle
import android.content.Context
import android.os.Message

import mo.wall.org.databinding.FragmentMyDialogBinding

import org.wall.mo.base.mvp.BaseMVPMaxLifecycleFragment

import mo.wall.org.R


class MyDialogFragment : BaseMVPMaxLifecycleFragment<MyDialogContract.Presenter,
        FragmentMyDialogBinding,
        MyDialogAcceptPar>(),
        MyDialogContract.View {

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

    override fun onFragmentFirstVisible() {

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
