package com.likeit.currenciesapp.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.likeit.currenciesapp.R;
import com.likeit.currenciesapp.base.BaseActivity;
import com.likeit.currenciesapp.configs.CommConfig;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WebActivity extends BaseActivity {
    public final static String WEB_TITLE = "WEB_TITLE";
    public final static String WEB_URL = "WEB_URL";
    @BindView(R.id.top_bar_title)
    TextView topBarTitle;
    @BindView(R.id.my_web)
    WebView myWeb;

    private String title = "";//标题
    private String url = "";//网页地址

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        if (getIntent() != null && getIntent().getExtras() != null) {
            title = getIntent().getExtras().getString(WEB_TITLE);
            url = getIntent().getExtras().getString(WEB_URL);
            Logger.d("title :" + title + "   url :" + url);
        }
        initTopBar(title);
        initWebView();
    }


    /**
     * 初始化WebView
     */
    public void initWebView() {
        //设置webChrome
        myWeb.setWebChromeClient(webChromeClient);
        //设置webClient
        myWeb.setWebViewClient(myWebViewClient);
        myWeb.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                try {
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } catch (Exception e) {

                }
            }
        });

        WebSettings settings = myWeb.getSettings();
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setBuiltInZoomControls(false);
        //设置WebView属性，能够执行Javascript脚本

        settings.setJavaScriptEnabled(true);
        settings.setBlockNetworkImage(false);
        settings.setLoadsImagesAutomatically(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setUserAgentString("Android/client");


        settings.setUseWideViewPort(true);// 将图片调整到适合webview的大小
        // 自适应屏幕
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);

        //小于Android4.2   移除WebView高危接口
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            myWeb.removeJavascriptInterface("searchBoxJavaBridge_");
        }
        // 加载页面
        myWeb.loadUrl(url);

    }


    /**
     * 获取浏览器信息
     */
    WebChromeClient webChromeClient = new WebChromeClient() {
        @Override
        public void onReceivedTitle(WebView view, String _title) {
            super.onReceivedTitle(view, _title);
            Logger.d("webview网页的_title  ：" + _title);
            if (TextUtils.isEmpty(title)) {
                topBarTitle.setText(_title);
            }
        }

        public void onProgressChanged(WebView view, int newProgress) {
            Logger.d("newProgress:" + newProgress);
            super.onProgressChanged(view, newProgress);
        }
    };

    /**
     * WebViewClient
     */
    WebViewClient myWebViewClient = new WebViewClient() {

        //设置不跳转系统浏览器，直接在本webview中打开网页
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            Logger.d("webview  url :" + url);
            view.loadUrl(url);
            return true;
        }


        //页面数据加载完毕回调下面方法
        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
        }

        //网页加载出错时
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            myWeb.loadDataWithBaseURL(null, "", CommConfig.MimeType, CommConfig.Encoding, null);
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && myWeb.canGoBack()) {
            myWeb.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
