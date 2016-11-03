package com.infrastructure.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.infrastructure.R;

public class WebviewActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvTitle;
    ImageView ivBack;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        //setTitle("");
        String t = getIntent().getStringExtra("title");
        String url = getIntent().getStringExtra("url");
        tvTitle = (TextView) findViewById(R.id.public_title);
        tvTitle.setText(t);
        //tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setSingleLine();
        tvTitle.setMaxEms(10);
        tvTitle.setEllipsize(TextUtils.TruncateAt.END);

        ivBack = (ImageView) findViewById(R.id.public_back);
        ivBack.setOnClickListener(this);
        ivBack.setVisibility(View.VISIBLE);
        webView = (WebView) findViewById(R.id.webview);
        webView.loadUrl(url);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.public_back) {
            finish();
        }
    }
}
