package com.kunrui.android_test.Activity;
/* Created by hyx
 * In 2019.5.8
 * https://blog.csdn.net/ipinki1218/article/details/81563021    创建
 * https://blog.csdn.net/qq_15527709/article/details/78853048   8.0适配
 */

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kunrui.android_test.R;
import com.kunrui.android_test.Service.DownloadService;

public class ServiceBakDlActivity extends AppCompatActivity implements View.OnClickListener {

    private DownloadService.DownloadBinder downloadBinder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("onServiceConnected", "YES");
            downloadBinder = (DownloadService.DownloadBinder) service;
            registBroadCast();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.back_dl_s);
        Button startDownload = findViewById(R.id.start_download);
        Button pauseDownload = findViewById(R.id.pause_download);
        Button cancelDownload = findViewById(R.id.cancel_download);
        startDownload.setOnClickListener(this);
        pauseDownload.setOnClickListener(this);
        cancelDownload.setOnClickListener(this);
        Intent intent = new Intent(ServiceBakDlActivity.this, DownloadService.class);
        startService(intent);//启动服务
        bindService(intent,connection,BIND_AUTO_CREATE);//绑定服务
        if (ContextCompat.checkSelfPermission(ServiceBakDlActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(ServiceBakDlActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }

//        //8.0崩溃问题
//        String CHANNEL_ONE_ID = "download";
//        String CHANNEL_ONE_NAME = "Channel One";
//        NotificationChannel notificationChannel = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            notificationChannel = new NotificationChannel(CHANNEL_ONE_ID,
//                    CHANNEL_ONE_NAME, NotificationManager.IMPORTANCE_HIGH);
//            notificationChannel.enableLights(true);
//            notificationChannel.setLightColor(Color.RED);
//            notificationChannel.setShowBadge(true);
//            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
//            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//            assert manager != null;
//            manager.createNotificationChannel(notificationChannel);
//        }
    }

    //动态广播注册
    public void registBroadCast() {
//        IntentFilter filter = new IntentFilter();
//        filter.addAction("actRegister");
//        registerReceiver(downloadBinder.acationReceiver, filter);
    }

    @Override
    public void onClick(View v) {
        if (downloadBinder == null){
            Log.e("downloadBinder", "null");
            return;
        }
        switch (v.getId()){
            case R.id.start_download:
                Log.e("start_download", "OK");
                String url="https://www.imooc.com/mobile/mukewang.apk";
                downloadBinder.startDownload(url);
                break;
            case R.id.pause_download:
                downloadBinder.pauseDownload();
                break;
            case R.id.cancel_download:
                downloadBinder.cancelDownload();
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"拒绝权限将无法使用程序",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        放到ListActivity中调用了
//        unbindService(connection);
//        unregisterReceiver(downloadBinder.acationReceiver);
    }
}
