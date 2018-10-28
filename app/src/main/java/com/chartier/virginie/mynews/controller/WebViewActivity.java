package com.chartier.virginie.mynews.controller;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.chartier.virginie.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WebViewActivity extends AppCompatActivity {

    public static final String EXTRA_URL = "EXTRA_URL";

    // FOR DESIGN

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.web_view) WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);

        configureToolbar();
        WebViewReader();
    }


    // This method calls the layout of the toolbar and fixes it in the action bar, then a return home function is displayed
    private void configureToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    // This method load and display an Uri into a web page view, the webView gets an Uri from an array object method
    private void WebViewReader() {
        String uri = getIntent().getStringExtra(EXTRA_URL);
        mWebView.loadUrl(uri);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
    }
}

