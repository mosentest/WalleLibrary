package ${packageName};

import com.xky.app.patient.api.HttpApiService;
import com.xky.app.patient.api.base.BaseEntityObserver;

import java.util.Map;

/**
 * 作者 : 
 * 邮箱 : 
 * desc   :
 * version: 1.0
 */
public class ${modelName} implements ${contractName}.Model {

    public void ${commonUrlName}(Map<String,Object> params, BaseEntityObserver<?>  observer){
        //String url = App.getInstance().getString(R.string.xxxxxx);
        String url = "";
        HttpApiService.sendPostRequest(url, params, observer);
    }
}