package net.fullerton.eren;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.SearchView;
import android.widget.TextView;

import net.fullerton.eren.handlers.JSFunc;

import org.openqa.selenium.SearchContext;

public class HomeActivity extends AppCompatActivity {

    private boolean doneLoading = false;
    private SearchView searchView;
    private WebView mWebView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        searchView = (SearchView) findViewById(R.id.searchBar);
        mWebView = (WebView) findViewById(R.id.webview);
        //mWebView.setVisibility(View.GONE);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.loadUrl("https://my.fullerton.edu/Portal/Dashboard/Psoft/ClassScheduleSearch");


        WebViewClient webViewClient = new WebViewClient() {
            @Override

            public void onPageFinished(WebView view, String url) {
                JSFunc.alert(mWebView,"window.frames[\"ModalTop\"].document.getElementById('SSR_CLSRCH_WRK_SUBJECT_SRCH$0')");
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                System.out.println("URL PUSHED: " + request.getUrl());
                return super.shouldOverrideUrlLoading(view, request);
            }
        };
        WebChromeClient webChromeClient = new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                System.out.println("MESSAGE GET: " + message);
                if(message == "null" && !doneLoading){
                    //since this whole onJsAlert is being called for every js alert then we check the messsage through this every time
                    //sleep timer for letting elements load
                    JSFunc.alert(mWebView,"window.frames[\"ModalTop\"].document.getElementById('SSR_CLSRCH_WRK_SUBJECT_SRCH$0')");
                } else if(!doneLoading){ //set a boolean variable to true if webpage is done loading
                    doneLoading = true;
                    JSFunc.modalTop(view, "SSR_CLSRCH_WRK_SUBJECT_SRCH$0", "CPSC");


                }
                return false; //disable alerts: return super.onJsAlert(view, url, message, result);
            }
        };
        mWebView.setWebChromeClient(webChromeClient);
        mWebView.setWebViewClient(webViewClient);
    }

}
