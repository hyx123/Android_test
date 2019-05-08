/* Created by hyx
 * In 2019.3.26
 * Use for Service
 * Service是运行在主线程之中的
 * 解决一开始布局重叠， 用帧布局， id用父组件ID， fragment不为空
 */

package com.kunrui.android_test.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.kunrui.android_test.FragmentActivity.FragLeft;
import com.kunrui.android_test.FragmentActivity.FragMain;
import com.kunrui.android_test.FragmentActivity.FragRight;
import com.kunrui.android_test.R;
import com.kunrui.android_test.Service.ServiceTest;

import java.util.ArrayList;

public class ServiceActivity extends AppCompatActivity {
    private Button start, stop, bind, unbind;
    public ServiceTest mybind = new ServiceTest();
    private Fragment mCurrentFragment;
    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_activity);

        initFragmentList();

        start = findViewById(R.id.startService);
        stop = findViewById(R.id.stopService);
        bind = findViewById(R.id.bindService);
        unbind = findViewById(R.id.unbindService);

        start.setOnClickListener(new myListen());
        stop.setOnClickListener(new myListen());
        bind.setOnClickListener(new myListen());
        unbind.setOnClickListener(new myListen());

        findViewById(R.id.home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("Key", "Main");
                changeTab(fragmentArrayList.get(0), bundle);
            }
        });

        findViewById(R.id.left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("Key", "Main");
                changeTab(fragmentArrayList.get(1), bundle);
            }
        });

        findViewById(R.id.right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("Key", "Main");
                changeTab(fragmentArrayList.get(2), bundle);
            }
        });
    }

    //fragment初始化
    public void initFragmentList() {
        FragMain fragMain = new FragMain();
        FragLeft fragLeft = new FragLeft();
        FragRight fragRight = new FragRight();

        fragmentArrayList.add(fragMain);
        fragmentArrayList.add(fragLeft);
        fragmentArrayList.add(fragRight);
    }

    //切换fragment
    public void changeTab(Fragment fragmt, Bundle bundle) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (null != mCurrentFragment) {
            ft.hide(mCurrentFragment);
        }
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(fragmt.getClass().getName());
        if (null == fragment) {
            fragment = fragmt;
        }
        fragment.setArguments(bundle);
        mCurrentFragment = fragment;

        if (!fragment.isAdded()) {
            ft.add(R.id.mfg, fragment, fragment.getClass().getName());
        } else {
            ft.show(fragment);
        }
        ft.commit();
    }

    //service监听事件
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
