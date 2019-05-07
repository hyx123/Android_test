package com.kunrui.android_test.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.kunrui.android_test.R;
import com.kunrui.android_test.JNI_USE.jni_accent;

public class NdkCPlusPlus extends AppCompatActivity {
    private jni_accent jni_accent = new jni_accent();

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.c_plus_plus);

        TextView tv = findViewById(R.id.sample_text);
        tv.setText(jni_accent.stringFromJNI());
        Log.e("NIMethodWithLog", jni_accent.myNativeJNIMethodWithLog());
    }
}
