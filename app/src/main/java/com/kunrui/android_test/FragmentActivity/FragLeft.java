package com.kunrui.android_test.FragmentActivity;

/*
 * https://blog.csdn.net/codeyanbao/article/details/81813501        IntentService, 后台下载
 */
import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kunrui.android_test.LiveDataBus.LiveDataBus;
import com.kunrui.android_test.R;

import java.util.Objects;

public class FragLeft extends Fragment {
    private TextView textView;
    private String sText = "";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        assert getArguments() != null;

        Log.e("Create Left Key", getArguments().getString("Key"));
        //创建订阅者
        LiveDataBus.get()
                .with("key_name", String.class)
                .observe(Objects.requireNonNull(getActivity()), new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        Log.e("observe ", s);
                    }
                });

        //Sticky模式, 可以获取之前的数据
        LiveDataBus.get()
                .with("sticky_key", String.class)
                .observeSticky(this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        Log.e("observeSticky ", s);
                        sText = s;
                    }
                });
        //observeForever 需要手动取消订阅
//        LiveDataBus.get()
//                .with("key_name", String.class)
//                .observeForever(observer);
//
//        LiveDataBus.get()
//                .with("key_name", String.class)
//                .removeObserver(observer);

        //observeStickyForever 需要手动取消订阅，Sticky模式
//        LiveDataBus.get()
//                .with("sticky_key", String.class)
//                .observeStickyForever(observer);
//        LiveDataBus.get()
//                .with("sticky_key", String.class)
//                .removeObserver(observer);
        return inflater.inflate(R.layout.side_left, container, false);
    }

    @SuppressLint("HandlerLeak")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("Activity Left", "Created");
        textView = getActivity().findViewById(R.id.side_left);
//        textView.setText(sText);
        Objects.requireNonNull(getActivity()).findViewById(R.id.side_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(sText);
            }
        });
//        explain = getActivity().getString(R.string.explain);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
//            Log.e("Hidden Change:", "Changed");
            textView.setText(sText);
        }
    }
}
