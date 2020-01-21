package mo.wall.org.dialog

import org.wall.mo.base.mvp.BaseContract
import org.wall.mo.base.mvp.BaseContract.BasePresenter

class MyDialogContract {

    interface View : BaseContract.BaseView

    abstract class Presenter : BaseContract.BasePresenter<View>()
}
