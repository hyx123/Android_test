package com.kunrui.android_test.Presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.webkit.WebView;

public interface Presenter {
    String loadData(String Path) throws Exception;
    Bitmap loadImg(String Path) throws Exception;
    WebView initWebView(WebView webView);
    Integer checkPermission(Context context, String permission);
}
