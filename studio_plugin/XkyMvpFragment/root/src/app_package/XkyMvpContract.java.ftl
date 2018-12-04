package ${packageName};

import xxxxxxxx.base.core.BaseModel;
import xxxxxxxx.base.core.BaseModelPresenter;
import xxxxxxxx.base.core.BaseView;
import xxxxxxxx.base.core.IPresenterWrapper;
import xxxxxxxx.api.base.BaseEntityObserver;


/**
 * 作者 : 
 * 邮箱 : 
 * desc   :
 * version: 1.0
 */
public interface ${contractName} {

    interface View extends BaseView {
        public void ${commonUrlName}Success();
    }

	/**
	*
	*   Map<String, Object> params, BaseEntityObserver<XXXXXXXXXX> observer
	*
	* 	String url = App.getInstance().getString(R.string.xxxxxx);
    *   HttpApiService.sendPostRequest(url, params, observer);
	*/
    interface Model extends BaseModel {
        public void ${commonUrlName}(Map<String,Object> params, BaseEntityObserver<?>  observer);
    }

    abstract class Presenter extends BaseModelPresenter<Model, View> implements IPresenterWrapper {
        public abstract void ${commonUrlName}();
    }
}