/* Created by hyx
 * In 2019.4.17
 * Touch on ListView
 * https://blog.csdn.net/qq_24675479/article/details/81411996 自定义View大全
 */
package com.kunrui.android_test.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;

import com.kunrui.android_test.Presenter.PersenterURL;
import com.kunrui.android_test.R;

public class CustomView extends AppCompatActivity {
    private WebView webView;
    private EditText editText;
    private PersenterURL persenterURL = new PersenterURL();

    @Override
    protected void onCreate(Bundle bundleSavedInstance) {
        super.onCreate(bundleSavedInstance);
        setContentView(R.layout.custom_view);

        webView = findViewById(R.id.customView_web);
        editText = findViewById(R.id.webUrl);

        webView = persenterURL.initWebView(webView);
        webView.loadUrl(String.valueOf(editText.getText()));

        findViewById(R.id.gone).setOnClickListener(v -> webView.loadUrl(String.valueOf(editText.getText())));
    }
}
