package com.kunrui.android_test.FragmentActivity;

/*
 * https://blog.csdn.net/codeyanbao/article/details/81813501        IntentService, 后台下载
 */
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kunrui.android_test.R;

public class FragLeft extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        assert getArguments() != null;
        Log.e("Create Left Key", getArguments().getString("Key"));

        return inflater.inflate(R.layout.side_left, container, false);
    }

    @SuppressLint("HandlerLeak")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("Activity Left", "Created");
//        explain = getActivity().getString(R.string.explain);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            Log.e("Hidden Change:", "Changed");
        }
    }
}
