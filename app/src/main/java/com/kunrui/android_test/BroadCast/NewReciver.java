package com.kunrui.android_test.BroadCast;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.widget.Toast;

public class NewReciver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        @SuppressLint("ShowToast") Toast t = Toast.makeText(context,"静态广播："+intent.getStringExtra("info"), Toast.LENGTH_SHORT);
        t.setGravity(Gravity.TOP,0,0);
        t.show();
    }
}
