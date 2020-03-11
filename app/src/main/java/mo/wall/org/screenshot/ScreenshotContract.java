package mo.wall.org.screenshot;

import org.wall.mo.base.mvp.BaseContract;
import org.wall.mo.base.mvp.BaseContract.BasePresenter;

public class ScreenshotContract {

    public interface View extends BaseContract.BaseView {
    }

    public static abstract class Presenter extends BaseContract.BasePresenter<View> {
    }
}
