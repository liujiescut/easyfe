package com.scut.easyfe.ui.activity;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.OtherUtils;
import com.umeng.socialize.media.Constant;

public class WebActivity extends BaseActivity {
    private WebView mWebView;
    private String mUrl;
    private String mTitle;

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_web_view);
    }

    @Override
    protected void initData() {
        if (getIntent() != null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                mTitle = extras.getString(Constants.Key.WEB_TITLE, "");
                mUrl = extras.getString(Constants.Key.WEB_URL, "");
            }
        }
    }

    @Override
    protected void initView() {
        if(null != mTitle && mTitle.length() != 0) {
            ((TextView) OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText(mTitle);
        }else{
            ((View)OtherUtils.findViewById(this, R.id.web_view_title)).setVisibility(View.GONE);
        }

        mWebView = OtherUtils.findViewById(this, R.id.web_wv_content);
    }

    @Override
    protected void fetchData() {
        mWebView.loadUrl(mUrl);
        startLoading("加载中");
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        mWebView.setWebViewClient(new BaseWebViewClient() {
            @Override
            protected boolean doOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                //加载一半就消失吧，好像这样好看一些
                if (progress >= 50) {
                    stopLoading();
                }
            }
        });
    }

    public void onBackClick(View view){
        finish();
    }


    @Override
    protected void onPause() {
        mWebView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mWebView.stopLoading();
        mWebView.removeAllViews();
        mWebView.destroy();
        super.onDestroy();
    }

    private abstract class BaseWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            // 为了加快加载速度，先阻止图片加载，待数据加载完毕在onPageFinished回调中开启图片加载功能
            view.getSettings().setBlockNetworkImage(true);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            // 网页加载完毕，开启加载图片
            view.getSettings().setBlockNetworkImage(false);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (TextUtils.isEmpty(url)) {
                return true;
            }
            //继续做URL解析，执行特定的业务逻辑处理
            return doOverrideUrlLoading(view, url);
        }
        /**
         * 子类中继续处理URL拦截操作
         *
         * @param view
         * @param url
         * @return
         */
        protected abstract boolean doOverrideUrlLoading(WebView view, String url);
    }
}
