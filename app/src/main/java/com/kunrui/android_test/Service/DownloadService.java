package com.kunrui.android_test.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.kunrui.android_test.Interface.DownloadListener;
import com.kunrui.android_test.Interface.DownloadTask;
import com.kunrui.android_test.R;

import java.io.File;
import java.util.Arrays;


public class DownloadService extends Service {
    private DownloadTask downloadTask;
    private String downloadUrl;
    private String CHANNEL_ONE_ID = "download";
    private int no = 0;

    private DownloadListener listener = new DownloadListener() {
        @Override
        public void onProgress(int progress) {
            getNotificationManager().notify(1,getNotification("Downloading...",progress));
        }

        @Override
        public void onSuccess() {
            downloadTask = null;
            //下载成功时将前台服务通知关闭，并创建一个下载成功的通知
            stopForeground(true);
            getNotificationManager().notify(1,getNotification("Download Success",-1));
            Toast.makeText(DownloadService.this,"Download Success",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailed() {
            downloadTask = null;
            //下载失败时将前台服务通知关闭，并创建一个下载失败的通知
            stopForeground(true);
            getNotificationManager().notify(1,getNotification("Download Failed",-1));
            Toast.makeText(DownloadService.this,"Download Failed",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPaused() {
            downloadTask = null;
            Toast.makeText(DownloadService.this,"Download Paused",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCanceled() {
            downloadTask = null;
            stopForeground(true);
            Toast.makeText(DownloadService.this,"Download Canceled",Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError() {
            downloadTask = null;
            stopForeground(true);
            Toast.makeText(DownloadService.this,"Download Error",Toast.LENGTH_SHORT).show();
        }
    };


    public DownloadService() {
    }

    private DownloadBinder mBinder = new DownloadBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private NotificationManager getNotificationManager(){
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    private Notification getNotification(String title, int progress){
        Log.e("getNotification", "transfer");

        Intent intent =new Intent();
        intent.setAction("actRegister");
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, 0);  //用于操作通知栏进度条的点击事件
        Log.e("PendingIntent", "OK");

//        Intent intent = new Intent();
//        intent.setAction("MLY");
//        intent.putExtra("info", "hyx");
//        PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, FLAG_UPDATE_CURRENT);
//        context.sendBroadcast(intent);

        //也可以是getIntent, broadcast
//        Intent intent = new Intent(this, ListActivity.class);
//        PendingIntent pi = PendingIntent.getActivity(this,0,intent,FLAG_UPDATE_CURRENT); //requestCode是用于刷新参数的
        //NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
        builder.setContentIntent(pi);   //点击事件
        builder.setContentTitle(title);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Log.e("setChannelId", "YES");
            builder.setChannelId(CHANNEL_ONE_ID);
        } else
            Log.e("setChannelId", "NO");
//        builder.setTicker("Nature");
        if (progress>=0){
            //当progress大于或者等于0时才需显示下载进度
            builder.setContentText(progress+"%");
            builder.setProgress(100,progress,false);
        }
        return builder.build();
    }

    //事件调用
    public class DownloadBinder extends Binder {
        public AcationReceiver acationReceiver = new AcationReceiver();

        public void startDownload(String url){
            no = 0;
            Log.e("startDownload", "OK");
            if(downloadTask == null){
                Log.e("downloadTask", "null");
            }

            downloadUrl =url;
            downloadTask = new DownloadTask(listener);
            downloadTask.execute(downloadUrl);
            startForeground(1,getNotification("Downloading...",0));     //下载进度条显示

            Toast.makeText(DownloadService.this,"Download ...",Toast.LENGTH_SHORT).show();
        }
        public void pauseDownload(){
            no = 1;
            if(downloadTask != null){
                downloadTask.pauseDownload();
            }
        }

        public void cancelDownload(){
            no = 1;
            if (downloadTask !=null){
                downloadTask.cancelDownload();
            }
            if (downloadUrl!=null){
                //取消下载时需将文件删除，并将通知关闭
                String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
                String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                File file = new File(directory+fileName);
                if (file.exists()){
                    file.delete();
                }
                getNotificationManager().cancel(1);
                stopForeground(true);
                Toast.makeText(DownloadService.this,"Canceled",Toast.LENGTH_SHORT).show();
            }
        }

        public void setChannelID() {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                String CHANNEL_ONE_NAME = "Channel One";
                NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ONE_ID, CHANNEL_ONE_NAME, NotificationManager.IMPORTANCE_HIGH);
                getNotificationManager().createNotificationChannel(notificationChannel);
            }
        }
    }

    //动态广播, 用来接收暂停信息
    public class AcationReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            assert action != null;
            if(action.equals("actRegister")) {
                Log.e("AcationReceiver", "success");
                if (no == 0)
                    mBinder.pauseDownload();
                else if (no == 1)
                    mBinder.startDownload(downloadUrl);
            }
        }
    }
}
