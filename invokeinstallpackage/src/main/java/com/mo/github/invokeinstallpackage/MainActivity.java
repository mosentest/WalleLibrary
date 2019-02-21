package com.mo.github.invokeinstallpackage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            new PackageManagerCompatQ(this).testReplaceFlagSdcardInternal("", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
