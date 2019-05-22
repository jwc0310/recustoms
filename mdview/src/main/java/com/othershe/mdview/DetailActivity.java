package com.othershe.mdview;

import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.detail_img)
    ImageView mDetailImg;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.webview)
    WebView mWebView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.detail_content)
    CoordinatorLayout mCoordinatorLayout;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        unbinder = ButterKnife.bind(this);

        init();
    }

    private void init() {
        //使mToolbar取代原来的ActionBar
        setSupportActionBar(mToolbar);
        //设置mToolbar左上角显示返回图标
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        //设置返回图标的点击事件
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //设置还没收缩时状态下字体颜色
        mToolbarLayout.setExpandedTitleColor(Color.RED);
        //设置收缩后Toolbar上字体的颜色
        mToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        //设置image背景
        mDetailImg.setBackgroundResource(R.mipmap.bg);

        mWebView.loadUrl("http://www.jianshu.com/");
        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                //必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
                mToolbarLayout.setTitle(title);
            }
        });

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(mCoordinatorLayout, "点我分享哦！", Snackbar.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
