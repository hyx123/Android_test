package com.kunrui.android_test.FragmentActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kunrui.android_test.LiveDataBus.LiveDataBus;
import com.kunrui.android_test.R;

import java.util.Objects;

public class FragRight extends Fragment {
    private int id = 0;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.side_right, container, false);
    }

    @SuppressLint("HandlerLeak")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Objects.requireNonNull(getActivity()).findViewById(R.id.side_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id++;
                LiveDataBus.get().with("key_name").setValue(String.valueOf(id));   //主线程发送消息
                LiveDataBus.get().with("sticky_key").postValue(String.valueOf(id));   //后台线程发送消息(也可以在主线程中发送), 订阅者会在主线程收到消息
//                LiveDataBus.get().with("key_name").postValue(value);  后台线程
            }
        });
    }
}
