package com.crownstack.songs;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class WebFragment extends Fragment {

    @BindView(R.id.webView)
    WebView webView;

    public WebFragment() {
        // Required empty public constructor
    }


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_web, container, false);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        assert bundle != null;
        String url = bundle.getString("artistUrl");
        webView.setWebViewClient(new MyBrowser());
        WebSettings webSettings = webView.getSettings();
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(url);
        return view;
    }
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}
