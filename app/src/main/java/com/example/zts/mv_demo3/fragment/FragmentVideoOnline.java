package com.example.zts.mv_demo3.fragment;

import android.text.Editable;
import android.text.method.KeyListener;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.zts.appbase.BaseFragment.BaseFragment;
import com.example.zts.appbase.view.CircleImageView;
import com.example.zts.mv_demo3.R;

/**
 * Created by ZTS on 2015/12/30.
 */
public class FragmentVideoOnline extends BaseFragment {
    private WebView webView;
    private CircleImageView circleImageView;
    private String newsUrl = "http://m.yinyuetai.com";
    private ProgressBar pb;
    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_video_online;
    }

    @Override
    public void initView(View rootview) {

        webView = (WebView) rootview.findViewById(R.id.music_online_webView);
        webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        pb = (ProgressBar) rootview.findViewById(R.id.webViewProgressBar);
        circleImageView = (CircleImageView) rootview.findViewById(R.id.music_online_CircleBtn);
    }

    @Override
    public void initData() {
/*        if(webView.canGoBack()){
            webView.goBack();
        }*/
    }

    @Override
    public void initEvent() {
        webView.loadUrl(newsUrl);

        //启用支持javascript
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        //自适应屏幕
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);

     /*   int screenDensity = getResources().getDisplayMetrics().densityDpi ;
        WebSettings.ZoomDensity zoomDensity = WebSettings.ZoomDensity.MEDIUM ;
        switch (screenDensity){
            case DisplayMetrics.DENSITY_LOW :
                zoomDensity = WebSettings.ZoomDensity.CLOSE;
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                zoomDensity = WebSettings.ZoomDensity.MEDIUM;
                break;
            case DisplayMetrics.DENSITY_HIGH:
                zoomDensity = WebSettings.ZoomDensity.FAR;
                break ;
        }
        settings.setDefaultZoom(zoomDensity);*/

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    pb.setVisibility(View.INVISIBLE);
                } else {
                    if (View.INVISIBLE == pb.getVisibility()) {
                        pb.setVisibility(View.VISIBLE);
                    }
                    pb.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(webView.canGoBack()){
                    webView.goBack();
                }
            }
        });

    }
}
