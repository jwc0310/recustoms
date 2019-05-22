package com.andy.recustomviews.activity;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.andy.recustomviews.R;
import com.andy.recustomviews.views.ProgressWebView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * webview
 * Created by Administrator on 2017/2/14.
 */
public class BroswerActivity extends BaseActivity {

    @BindView(R.id.webView)
    ProgressWebView mWebView;
    @BindView(R.id.webView_click)
    Button webViewClick;
    private Unbinder unbinder;
    private String url = "file:///android_asset/test.html";

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_browser);
        unbinder = ButterKnife.bind(this);
        initWebView();
    }

    @Override
    public void onDestroy() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        finish();
        return false;
    }

    private void initWebView() {
        final String loadUrl = getIntent().getStringExtra("url");
        WebSettings setting = mWebView.getSettings();
        setting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS); //SINGLE_COLUMN
        setting.setLoadWithOverviewMode(true);
        setting.setBuiltInZoomControls(true);
        setting.setSupportZoom(true);
        setting.setJavaScriptEnabled(true);
        setting.setBuiltInZoomControls(true);
        setting.setDisplayZoomControls(true);
        setting.setAppCacheEnabled(true);
        setting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mWebView.loadUrl((loadUrl == null || loadUrl.equals("") || loadUrl.length() == 0) ? url : loadUrl);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e("Andy", "shouldOverrideUrlLoading = " + url);
                if (url.startsWith("https")){
                    Map extraHeaders = new HashMap();
                    extraHeaders.put("Referer", "http://wxpay.wxutil.com/mch/pay/h5.v2.php");
                    view.loadUrl(url, extraHeaders);
                }else {
                    view.loadUrl(url);
                }
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.e("Andy", "onPageStarted");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.e("Andy", "onPageFinished");
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });
        mWebView.addJavascriptInterface(new JsInteration(), "android");
    }

    @OnClick(R.id.webView_click)
    public void onViewClicked() {
        mWebView.loadUrl("javascript:s()");
        mWebView.evaluateJavascript("sum(2,3)", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String s) {
                Log.e("BrowserActivity", "onReceiveValue value=" + s);
            }
        });
    }

    public class JsInteration {
        @JavascriptInterface
        public String back() {
            Log.e("Andy", "I am called by js");
            return "hello world";
        }
    }
}
