package com.kunrui.android_test.Service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/* https://www.cnblogs.com/huangjialin/p/7738104.html 创建参考
 * https://blog.csdn.net/imxiangzi/article/details/76039978  补充说明
 */

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
        return new Mybind();
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    //暴露的接口, 在当前绑定的activity关闭时， 链接必须断掉
    public class Mybind extends Binder {
        public void getString() {
            Log.e("ServiceTest", "->>>> getString");
        }
    }

    public ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mybind = (ServiceTest.Mybind) service;
            mybind.getString();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public ServiceTest.Mybind mybind;
}
