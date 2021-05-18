package net.fullerton.eren;

import android.os.Bundle;
import android.os.Handler;
import android.renderscript.Sampler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import net.fullerton.eren.handlers.JSFunc;

import java.util.HashMap;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

/*TO DO
-CHECK FOR CASE WHERE "CLASSES ARE FULL OR IF CLASS DOESN't EXIST"
-
 */
public class HomeActivity extends AppCompatActivity {
    private HomeFragment homeFrag;
    private SearchFragment searchFrag;
    private CartFragment cartFrag;
    public WebView mWebViewAdd;
    public WebView mWebViewCart;
    public WebView mWebViewHome;
    public WebView mWebView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragmentTransaction.replace(R.id.view2, homeFrag.newInstance()).commit();
                    return true;
                case R.id.navigation_dashboard:
                    fragmentTransaction.replace(R.id.view2, searchFrag.newInstance()).commit();
                    return true;
                case R.id.navigation_notifications:
                    fragmentTransaction.replace(R.id.view2, cartFrag.newInstance()).commit();
                    return true;
            }
            return false;
        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //TODO: Unique webviews per tab
        //HOME: mWebViewHome
        //SEARCH: mWebView
        //CART: mWebViewCart
        initWebview();
        initWebviewHome();
        initWebviewCart();
        initWebviewAdd();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void initWebview(){
        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.setVisibility(View.GONE);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

        WebViewClient webViewClient = new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                System.out.println("Page finished here");
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
                result.confirm();
                return true;
            }
        };

        mWebView.setWebChromeClient(webChromeClient);
        mWebView.setWebViewClient(webViewClient);

        searchFrag = new SearchFragment();
    }
    //TODO: Hide keyboard when login button pressed manually
    private void initWebviewHome(){
        mWebViewHome = (WebView) findViewById(R.id.webviewHome);
        mWebViewHome.setVisibility(View.GONE);
        mWebViewHome.getSettings().setJavaScriptEnabled(true);
        mWebViewHome.getSettings().setDomStorageEnabled(true);
        mWebViewHome.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

        WebViewClient webViewClient = new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                System.out.println("Home Page finished here");
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                System.out.println("Home URL PUSHED: " + request.getUrl());
                return super.shouldOverrideUrlLoading(view, request);
            }
        };

        WebChromeClient webChromeClient = new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                System.out.println("Home MESSAGE GET: " + message);
                result.confirm();
                return true;
            }
        };

        mWebViewHome.setWebChromeClient(webChromeClient);
        mWebViewHome.setWebViewClient(webViewClient);

        homeFrag = new HomeFragment();
    }

    private void initWebviewCart() {
        mWebViewCart = (WebView) findViewById(R.id.webviewCart);
        mWebViewCart.setVisibility(View.GONE);
        mWebViewCart.getSettings().setJavaScriptEnabled(true);
        mWebViewCart.getSettings().setDomStorageEnabled(true);
        mWebViewCart.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

        WebViewClient webViewClient = new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                System.out.println("Cart Page finished here");
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                System.out.println("CART URL PUSHED: " + request.getUrl());
                return super.shouldOverrideUrlLoading(view, request);
            }
        };

        WebChromeClient webChromeClient = new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                System.out.println("Cart MESSAGE GET: " + message);
                result.confirm();
                return true;
            }
        };

        mWebViewCart.setWebChromeClient(webChromeClient);
        mWebViewCart.setWebViewClient(webViewClient);

        cartFrag = new CartFragment();
    }

    private ValueCallback<String> cart_isLoadedSemesterSelect = new ValueCallback<String>() {
        @Override
        public void onReceiveValue(String value) {
            if(value.equals("true")) {
                mWebViewAdd.evaluateJavascript("window.frames[\"ModalTop\"].document.getElementById('SSR_DUMMY_RECV1$sels$2$$0').checked = true", null);
                JSFunc.modalTopClick(mWebViewAdd, "DERIVED_SSS_SCT_SSR_PB_GO", "click");
            }else{
                System.out.println("Loading Semester select page");
                JSFunc.returnValues(mWebViewAdd, "(function() { return window.frames[\"ModalTop\"].document.getElementById('DERIVED_SSS_SCT_SSR_PB_GO') != null }) ()", cart_isLoadedSemesterSelect);
            }
        }
    };

    private ValueCallback<String> cart_isLoadedPortal = new ValueCallback<String>() {
        @Override
        public void onReceiveValue(String value) {
            if(value.equals("true")) {
                JSFunc.modalTopClick(mWebViewAdd, "DERIVED_SSS_SCR_SSS_LINK_ANCHOR3", "click");
                JSFunc.returnValues(mWebViewAdd, "(function() { return window.frames[\"ModalTop\"].document.getElementById('DERIVED_SSS_SCT_SSR_PB_GO') != null }) ()", cart_isLoadedSemesterSelect);
            } else {
                System.out.println("Loading portal");
                JSFunc.returnValues(mWebViewAdd, "(function() { return window.frames[\"ModalTop\"].document.getElementById('DERIVED_SSS_SCL_SS_WEEKLY_SCHEDULE') != null }) ()", cart_isLoadedPortal);
            }
        }
    };

    private void initWebviewAdd() {
        mWebViewAdd = (WebView) findViewById(R.id.webviewAdd);
        mWebViewAdd.setVisibility(View.VISIBLE);
        mWebViewAdd.getSettings().setJavaScriptEnabled(true);
        mWebViewAdd.getSettings().setDomStorageEnabled(true);
        mWebViewAdd.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

        WebViewClient webViewClient = new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                System.out.println("Cart Page finished here");
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                System.out.println("CART URL PUSHED: " + request.getUrl());
                return super.shouldOverrideUrlLoading(view, request);
            }
        };

        WebChromeClient webChromeClient = new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                System.out.println("Cart MESSAGE GET: " + message);
                result.confirm();
                return true;
            }
        };

        mWebViewAdd.setWebChromeClient(webChromeClient);
        mWebViewAdd.setWebViewClient(webViewClient);

        mWebViewAdd.loadUrl("https://my.fullerton.edu/Portal/Dashboard/PSoft/StudentCenter/");
        JSFunc.returnValues(mWebViewAdd, "(function() { return window.frames[\"ModalTop\"].document.getElementById('DERIVED_SSS_SCL_SS_WEEKLY_SCHEDULE') != null }) ()", cart_isLoadedPortal);
    }
}