package com.kunrui.android_test.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

//https://www.cnblogs.com/huangjialin/p/7738104.html

public class ServiceTest extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.w("ServiceTest", " ->>>> onCreate ");
        System.out.println("onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.w("ServiceTest", " ->>>> onStartCommand ");
        System.out.println("onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.w("ServiceTest", " ->>>> onDestroy ");
        System.out.println("onDestroy");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
