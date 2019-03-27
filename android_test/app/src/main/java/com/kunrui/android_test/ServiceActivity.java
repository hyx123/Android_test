/* Created by hyx
 * In 2019.3.26
 * Use for Service
 */

package com.kunrui.android_test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.kunrui.android_test.Service.ServiceTest;

public class ServiceActivity extends Activity {
    private Button start, stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_activity);

        start = findViewById(R.id.startService);
        stop = findViewById(R.id.stopService);

        start.setOnClickListener(new myListen());
        stop.setOnClickListener(new myListen());
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
            }
        }
    }
}
