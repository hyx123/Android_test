package com.kunrui.android_test.Activity;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.kunrui.android_test.R;

public class BroadCast extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broad_cast);

        findViewById(R.id.button).setOnClickListener(this::send);
    }

    public void send(View v) {
        Intent intent = new Intent();
        intent.setAction("MLY");
        intent.putExtra("info", "hyx");
        intent.setComponent(new ComponentName(this, "com.kunrui.android_test.BroadCast.NewReciver"));
        sendBroadcast(intent);
    }
}
