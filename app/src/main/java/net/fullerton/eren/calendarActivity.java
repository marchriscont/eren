package net.fullerton.eren;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.SearchView;

public class calendarActivity extends AppCompatActivity {

    private boolean doneLoading = false;
    private boolean classPageTest = false;
    private SearchView searchView;
    private WebView mWebView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    };

}
