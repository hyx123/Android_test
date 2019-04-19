package com.kunrui.android_test.Presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebView;

import com.kunrui.android_test.Model.model_url;

public class PersenterURL implements Presenter {
    private View v;
    private model_url model_url = new model_url();
//    public PersenterURL(View v) {
//        this.v = v;
//    }
    public PersenterURL() {
//        this.v = v;
    }

    @Override
    public String loadData(String Path) throws Exception {
        return model_url.httpGet(Path);
    }

    @Override
    public Bitmap loadImg(String Path) throws Exception {
        return model_url.ImageGet(Path);
    }

    @Override
    public WebView initWebView(WebView webView) {
        return model_url.InitWebView(webView);
    }

    @Override
    public Integer checkPermission(Context context, String permission) {
        return model_url.checkPermission(context, permission);
    }
//                    persenterURL.checkPermission(QRCode.this, Manifest.permission.CAMERA);
}
