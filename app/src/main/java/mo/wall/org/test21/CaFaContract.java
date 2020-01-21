package mo.wall.org.test21;

import org.wall.mo.base.mvp.BaseContract;
import org.wall.mo.base.mvp.BaseContract.BasePresenter;

public class CaFaContract {

    public interface View extends BaseContract.BaseView {
    }

    public static abstract class Presenter extends BaseContract.BasePresenter<View> {
    }
}
