package net.fullerton.eren;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import net.fullerton.eren.adapters.ClassesAdapter;
import net.fullerton.eren.adapters.RateAdapter;
import net.fullerton.eren.handlers.JSFunc;
import net.fullerton.eren.handlers.RateObject;

public class PreviewActivity extends AppCompatActivity {

    private ListView rateList;

    private TextView cProf;
    private TextView cRate;
    private TextView cDetails;
    private TextView cRoom;
    //TODO: Add Waitlisted/etc icons

    private Button cancelButton;

    private WebView webView;

    private Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        context = this;

        rateList = findViewById(R.id.ratelist);

        cProf = findViewById(R.id.className);
        cRate = findViewById(R.id.classProf);
        cDetails = findViewById(R.id.classInfo);
        cRoom = findViewById(R.id.classRoom);

        Bundle b = getIntent().getExtras();
        if(b != null){
            cProf.setText(b.getString("cProf"));
            cRate.setText(b.getString("cRate"));
            cDetails.setText(b.getString("cDetails"));
            cRoom.setText((b.getString("cRoom").contains("WEB") ? "WEB" : b.getString("cRoom")));
        }

        cancelButton = findViewById(R.id.buttoncancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        webView = findViewById(R.id.webviewRate);
        initWebview();
    }

    private RateObject[] rateObjects;

    private ValueCallback<String> createRatings = new ValueCallback<String>() {
        @Override
        public void onReceiveValue(String value) {
            if(!value.equals("null")){
                value = value.substring(1,value.length()-1);
                String[] values = value.split("\\|");
                rateObjects = new RateObject[values.length];
                int it = 0;
                for(String item : values){
                    if(item.isEmpty())
                        continue;
                    String[] items = item.split("`");
                    rateObjects[it++] = new RateObject(items[0].split("\\\\n")[0], items[1], items[2]);
                }
                RateAdapter cAdapter = new RateAdapter(context, rateObjects);
                rateList.setAdapter(cAdapter);
                rateList.invalidateViews();
            }else{
                //handle later
            }
        }
    };

    private ValueCallback<String> isPageOK = new ValueCallback<String>() {
        @Override
        public void onReceiveValue(String value) {
            if(value.equals("false")){
                JSFunc.returnValues(webView, "(function(){var sBuilder=\"\";var rate = document.getElementsByClassName(\"CardNumRating__StyledCardNumRating-sc-17t4b9u-0 eWZmyX\");var date = document.getElementsByClassName(\"TimeStamp__StyledTimeStamp-sc-9q2r30-0 bXQmMr RatingHeader__RatingTimeStamp-sc-1dlkqw1-3 BlaCV\");var elmts = document.getElementsByClassName(\"Comments__StyledComments-dzzyvm-0 gRjWel\");for(var i = 0; i < elmts.length-1; i++){ sBuilder += (rate[i*2].innerText.split('QUALITY\\n')[1] + \"`\" + date[i*2].innerText + \"`\" + elmts[i+1].innerText); sBuilder += \"|\" }; return sBuilder})();", createRatings);
            }else{
                JSFunc.returnValues(webView, "document.getElementsByClassName(\"Comments__StyledComments-dzzyvm-0 gRjWel\") == null", isPageOK);
            }
        }
    };

    private void initWebview(){
        webView.setVisibility(View.GONE);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.loadUrl("https://www.ratemyprofessors.com/ShowRatings.jsp?tid=2248287");
        //TODO: Dynamic RateMyProf

        WebViewClient webViewClient = new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                System.out.println("Preview Page finished here");

                JSFunc.returnValues(webView, "document.getElementsByClassName(\"Comments__StyledComments-dzzyvm-0 gRjWel\") == null", isPageOK);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                System.out.println("Preview URL PUSHED: " + request.getUrl());
                return super.shouldOverrideUrlLoading(view, request);
            }
        };

        WebChromeClient webChromeClient = new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                System.out.println(" Preview MESSAGE GET: " + message);
                result.confirm();
                return true;
            }
        };

        webView.setWebChromeClient(webChromeClient);
        webView.setWebViewClient(webViewClient);
    }
}