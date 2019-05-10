//HttpsURLConnection这种方法， 私有CA签证是无法响应的， 必须重新书写校验证书TrustManager方法
//OKhttp3.0第三方库
package com.kunrui.android_test.Model;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class  model_url implements Model {
    @Override
    public String httpGet(String Path) throws Exception{
        System.out.println("Path:" + Path);
        if(Path.equals(""))
            return "Path NULL";

        byte[] data = new byte[1024];
        int len;
        URL url = new URL(Path.trim());
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        //conn.setRequestProperty("Content-type", "application/x-javascript->json");
        conn.setRequestMethod("GET");
        conn.setDoOutput(true);
        InputStream inStream = conn.getInputStream();
        while (-1 != (len = inStream.read(data))) {
            outStream.write(data, 0, len);
        }
        inStream.close();
        System.out.println(new String(outStream.toByteArray()));
        return new String(outStream.toByteArray());
    }

    @Override
    public Bitmap ImageGet(String Path) throws Exception {
        URL url = new URL(Path.trim());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        InputStream inputStream = conn.getInputStream();
        return BitmapFactory.decodeStream(inputStream);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public WebView InitWebView(WebView webView) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); //启用javascript
        webSettings.setAppCacheEnabled(false);   //启用appCache
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
//        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//缓存

        //设置可自由缩放网页、JS生效
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);

        // 如果页面中链接，如果希望点击链接继续在当前browser中响应，
        // 而不是新开Android的系统browser中响应该链接，必须覆盖webview的WebViewClient对象
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }
        });
        return webView;
    }

    private boolean checkPermissionGranted(Context context, String permission) {
        return PermissionChecker.checkPermission(context, permission, android.os.Process.myPid(), android.os.Process.myUid(), context.getPackageName()) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public Integer checkPermission(Context context, String permission) {
        if(!checkPermissionGranted(context, permission)) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{permission}, 0x1111);
            return -1;
        }
        return 1;
    }
}
