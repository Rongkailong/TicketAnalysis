package com.rkl.ticket.ticket_analysis;

import android.annotation.SuppressLint;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.rkl.common_library.permission.PermissionUtil;
import com.rkl.common_library.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;


public class WebViewActivity extends AppCompatActivity {
    private long lastClick=0;
    private WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_webview);
        initView();
    }


    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {
        mWebView = findViewById(R.id.web_view);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                mWebView.setVisibility(View.GONE);
            }
        });
        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {

                Log.i("tmd", "onDownloadStart: "+url);
                PermissionUtil permissionUtil =new PermissionUtil(WebViewActivity.this, new PermissionUtil.PermissionCallback() {
                    @Override
                    public void onSuccessful() {
                        List<String> lst = new ArrayList<String>();
                        lst.add(url);  //第一个参数是要下载的地址。如http://www.test.com/download/1.apk
                        DownloadFile downloadFile = new DownloadFile(WebViewActivity.this);
                        downloadFile.execute(lst);
                    }

                    @Override
                    public void onFailure() {

                    }
                });
                permissionUtil.requestStorage();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                // TODO Auto-generated method stub
                // handler.cancel();// Android默认的处理方式
                handler.proceed();// 接受所有网站的证书
                // handleMessage(Message msg);// 进行其他处理
            }
        });
        //显示网页,参数中设置要显示的网页即可
        mWebView.loadUrl(getIntent().getStringExtra("url"));
    }


}
