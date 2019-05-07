/* Created by hyx
 * In 2019.3.26
 * Use for Service
 * Service是运行在主线程之中的
 */

package com.kunrui.android_test.Activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

import com.kunrui.android_test.R;
import com.kunrui.android_test.Service.ServiceTest;

public class ServiceActivity extends Activity {
    private Button start, stop, bind, unbind;
    public ServiceTest mybind = new ServiceTest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_activity);

        start = findViewById(R.id.startService);
        stop = findViewById(R.id.stopService);
        bind = findViewById(R.id.bindService);
        unbind = findViewById(R.id.unbindService);

        start.setOnClickListener(new myListen());
        stop.setOnClickListener(new myListen());
        bind.setOnClickListener(new myListen());
        unbind.setOnClickListener(new myListen());
    }

    public class myListen implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.startService:
                    startService(new Intent(ServiceActivity.this, ServiceTest.class));
                    break;
                case R.id.stopService:
                    stopService(new Intent(ServiceActivity.this, ServiceTest.class));
                    break;
                case R.id.bindService:
                    Intent bindService = new Intent(ServiceActivity.this, ServiceTest.class);
                    bindService(bindService, mybind.connection, BIND_AUTO_CREATE);
                    break;
                case R.id.unbindService:
                    mybind.mybind.getString();
                    unbindService(mybind.connection);
                    break;
            }
        }
    }
}
