package ${packageName};

import java.util.Map;
import com.xky.app.patient.base.core.BaseModel;
import com.xky.app.patient.base.core.BaseModelPresenter;
import com.xky.app.patient.base.core.BaseView;
import com.xky.app.patient.base.core.IPresenterWrapper;
import com.xky.app.patient.api.base.BaseEntityObserver;


/**
 * 作者 : 
 * 邮箱 : 
 * desc   :
 * version: 1.0
 */
public interface ${contractName} {

    interface View extends BaseView {
        public void ${commonUrlName}Success(${infoName} object, int pageFlag);
    }

	/**
	*
	*   Map<String, Object> params, BaseEntityObserver<XXXXXXXXXX> observer
	*
	* 	String url = App.getInstance().getString(R.string.xxxxxx);
    *   HttpApiService.sendPostRequest(url, params, observer);
	*/
    interface Model extends BaseModel {
        public void ${commonUrlName}(Map<String,Object> params, BaseEntityObserver<${infoName}>  observer);
    }

    abstract class Presenter extends BaseModelPresenter<Model, View> implements IPresenterWrapper {
        public abstract void ${commonUrlName}(int pageFlag);
    }
}