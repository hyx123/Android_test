/* Created by hyx
 * In 2019.3.26
 * Touch on ListView
 * https://www.2cto.com/kf/201804/738696.html NDK导入
 */

package com.kunrui.android_test.Activity;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kunrui.android_test.ListView.Fruit;
import com.kunrui.android_test.ListView.FruitAdapter;
import com.kunrui.android_test.Presenter.PersenterURL;
import com.kunrui.android_test.R;
import com.kunrui.android_test.Service.DownloadService;
import com.kunrui.android_test.qrdemo.QRScannerActivity;
import com.kunrui.common.EventUtil;

import java.util.ArrayList;
import java.util.List;


public class ListActivity extends AppCompatActivity{
    private List<Fruit> fruitList = new ArrayList<>();
    private PersenterURL persenterURL = new PersenterURL();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);

        persenterURL.checkPermission(ListActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        persenterURL.checkPermission(ListActivity.this, Manifest.permission.CALL_PHONE);

        initFruits();
        FruitAdapter adapter = new FruitAdapter(ListActivity.this, R.layout.fruit_item, fruitList);
        final ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            System.out.println("position:" + position);
            Log.d("position", String.valueOf(position));

            TextView view_text = view.findViewById(R.id.fruit_name);
            if (!String.valueOf(view_text.getText()).equals("flutter component"))
                Toast.makeText(getApplicationContext(), view_text.getText(), Toast.LENGTH_SHORT).show();

            Intent intent;
            switch (String.valueOf(view_text.getText())) {
                case "Service Fragment":
                    intent = new Intent(ListActivity.this, ServiceActivity.class);
                    startActivity(intent);
                    break;
                case "Service Back DownLoad":
                    intent = new Intent(ListActivity.this, ServiceBakDlActivity.class);
                    startActivity(intent);
                    break;
                case "RecyclerView":
                    intent = new Intent(ListActivity.this, RecyclerActivity.class);
                    startActivity(intent);
                    break;
                case "Executors":
                    intent = new Intent(ListActivity.this, ExecutorsHttps.class);
                    startActivity(intent);
                    break;
                case "lte":
                    intent = new Intent(ListActivity.this, LteMsg.class);
                    startActivity(intent);
                    break;
                case "CustomView":
                    intent = new Intent(ListActivity.this, CustomView.class);
                    startActivity(intent);
                    break;
                case "QRCode":
                    intent = new Intent(ListActivity.this, QRScannerActivity.class);
                    startActivity(intent);
                    break;
                case "C++ NDK":
                    intent = new Intent(ListActivity.this, NdkCPlusPlus.class);
                    startActivity(intent);
                    break;
                case "侧滑Menu handlerThread":
                    intent = new Intent(ListActivity.this, SimpleSwipeMenu.class);
                    startActivity(intent);
                    break;
                case "BroadCast":
                    intent = new Intent(ListActivity.this, BroadCast.class);
                    startActivity(intent);
                    break;
                case "home component":
                    EventUtil.open(ListActivity.this, "com.kunrui.home.MainActivity");
                    break;
                case "flutter component":
                    break;
                case "Pop Menu":
                    EventUtil.open(ListActivity.this, "com.kunrui.home.MenuPop");
                    break;
                case "Activity Intent":
                    //https://www.cnblogs.com/cocoabird/p/8392274.html      其他应用activity跳转
                    intent = new Intent("net.blogjava.mobile.MYACTION", Uri.parse("info://调用不了吗"));
//                    intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:12345678"));  // 调用电话接口
                    intent.putExtra("value", "hello intent");
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        });
        Intent intent = new Intent(ListActivity.this, DownloadService.class);
        startService(intent);//启动服务
        bindService(intent,connection,BIND_AUTO_CREATE);//绑定服务
    }

    private void initFruits() {
        fruitList.add(new Fruit("Service Fragment", R.drawable.ic_action_globe));
        fruitList.add(new Fruit("Service Back DownLoad", R.drawable.ic_action_globe));
        fruitList.add(new Fruit("Content Provider", R.drawable.ic_action_globe));
        fruitList.add(new Fruit("RecyclerView", R.drawable.ic_action_globe));
        fruitList.add(new Fruit("Executors", R.drawable.ic_action_globe));
        fruitList.add(new Fruit("lte", R.drawable.ic_action_globe));
        fruitList.add(new Fruit("CustomView", R.drawable.ic_action_globe));
        fruitList.add(new Fruit("QRCode", R.drawable.ic_action_globe));
        fruitList.add(new Fruit("C++ NDK", R.drawable.ic_action_globe));
        fruitList.add(new Fruit("侧滑Menu handlerThread", R.drawable.ic_action_globe));
        fruitList.add(new Fruit("BroadCast", R.drawable.ic_action_globe));
        fruitList.add(new Fruit("home component", R.drawable.ic_action_globe));
        fruitList.add(new Fruit("flutter component", R.drawable.ic_action_globe));
        fruitList.add(new Fruit("Pop Menu", R.drawable.ic_action_globe));
        fruitList.add(new Fruit("Activity Intent", R.drawable.ic_action_globe));
    }

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

    //动态广播注册
    public void registBroadCast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("actRegister");
        registerReceiver(downloadBinder.acationReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
        unregisterReceiver(downloadBinder.acationReceiver);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
