package com.kunrui.android_test.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public interface Model {
    String httpGet(String Path) throws Exception;
    Bitmap ImageGet(String Path) throws Exception;
    WebView InitWebView(WebView webView);
    Integer checkPermission(Context context, String permission);
}
