package mo.wall.org.rxjava2;

import org.wall.mo.base.mvp.BaseContract;
import org.wall.mo.base.mvp.BaseContract.BasePresenter;

public class RxJavaContract {

    public interface View extends BaseContract.BaseView {
    }

    public static abstract class Presenter extends BaseContract.BasePresenter<View> {
    }
}
