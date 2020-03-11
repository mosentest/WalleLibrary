package mo.wall.org.invokeinstallpackage_p;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        try {
            new PackageManagerCompatP(this).testReplaceFlagSdcardInternal("", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
