package com.kunrui.android_test.Activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kunrui.android_test.Presenter.PersenterURL;
import com.kunrui.android_test.R;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorsHttps extends AppCompatActivity {
    private ImageView img;
    private Handler mhandler;
    private Runnable runnable;
    private PersenterURL persenterURL;
    private TextView textView;
    private EditText editText, editText2;
    private Button button, getImg;
    private ThreadPoolExecutor threadPoolExecutor;

    public String method = "";

    @SuppressLint("HandlerLeak")
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.executors);
        init();
        button.setOnClickListener(new myListen());
        getImg.setOnClickListener(new myListen());
    }

    @SuppressLint("HandlerLeak")
    public void init() {
        textView = findViewById(R.id.getMsg);
        editText = findViewById(R.id.url);
        editText2 = findViewById(R.id.url2);
        button = findViewById(R.id.ThreadPool);
        getImg = findViewById(R.id.getImage);
        persenterURL = new PersenterURL();
        img = findViewById(R.id.downLoad);

        threadPoolExecutor = new ThreadPoolExecutor(3,5,5, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(100));

        runnable = new Runnable() {
            @Override
            public void run() {
                Message message = mhandler.obtainMessage();
                Bundle bundle = new Bundle();
                String Path;
                try {
                    switch (method) {
                        case "ThreadPool":
                            Path = String.valueOf(editText.getText());
                            Log.d("ThreadPool", "run: ");
                            bundle.putString("data", persenterURL.loadData(Path));
                            message.what = 0;
                            break;
                        case "getImage":
                            Path = String.valueOf(editText2.getText());
                            Log.d("getImage", "run: ");
                            bundle.putParcelable("img", persenterURL.loadImg(Path));
                            message.what = 1;
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                message.setData(bundle);
                mhandler.sendMessage(message);
            }
        };

        mhandler = new Handler(){
            Bundle bundle;
            public void handleMessage (Message message) {
                super.handleMessage(message);
                bundle = message.getData();
                switch (message.what) {
                    case 0:
                        System.out.println("返回结果:" + bundle.get("data"));
                        textView.setText(String.valueOf(bundle.get("data")));
                        break;
                    case 1:
                        img.setImageBitmap((Bitmap) bundle.get("img"));
                        break;
                }
            }
        };
    }

    public class myListen implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ThreadPool:
                    method = "ThreadPool";
                    threadPoolExecutor.execute(runnable);
                    break;
                case R.id.getImage:
                    method = "getImage";
                    threadPoolExecutor.execute(runnable);
                    break;
            }
        }
    }
}
