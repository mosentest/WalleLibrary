package ${packageName};

import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * 作者 : 
 * 邮箱 : 
 * desc   :
 * version: 1.0
 */
public class ${presenterName} extends ${contractName}.Presenter {

    @Override
    protected ${contractName}.Model createModel() {
        return new ${modelName}();
    }

    @Override
    public void start() {

    }

    @Override
    public void initBundleData(@NonNull Bundle bundle) {

    }

    public void ${commonUrlName}() {

    }
}
