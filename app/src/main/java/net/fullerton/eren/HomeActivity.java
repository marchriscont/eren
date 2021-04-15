package net.fullerton.eren;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import net.fullerton.eren.handlers.JSFunc;

import java.util.HashMap;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class HomeActivity extends AppCompatActivity {
    //USER INPUT
    HashMap<String, String> classCatList = new HashMap<String, String>(); //hashmap to hold the class cats and their values
    Vector<String> vect = new Vector<String>();
    ArrayAdapter<String> adapter;
    String classCat, classNumber;
    EditText classCatInput;
    EditText classNumberInput;
    Button searchButton;
    SearchView mySearchView;
    ListView myList;
    //webview activity
    private boolean doneLoading = false;
    private boolean classPageTest = false;
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
        //------------------------------------------------------------
        //GET THE USER INPUT FIRST
        //------------------------------------------------------------
        mySearchView = (SearchView) findViewById(R.id.searchBar);
        myList = (ListView) findViewById(R.id.classList);
        classCatInput = (EditText) findViewById(R.id.class_cat);
        classNumberInput = (EditText) findViewById(R.id.class_num);
        searchButton = (Button) findViewById(R.id.search);
        System.out.println("VALUES BEFORE");
        //------------------------------------------------------------
        //List of classes(making a hashmap for the name of the course group and the value to input to the JS)
        //------------------------------------------------------------
        classCatList.put("Accounting", "ACCT");
        classCatList.put("African American Studies", "AFAM");
        classCatList.put("Aging Studies", "AGNG");
        classCatList.put("American Studies", "AMST");
        classCatList.put("Anthropology", "ANTH");
        classCatList.put("Arabic", "ARAB");
        classCatList.put("Art", "ART");
        classCatList.put("Art Education", "ARTE");
        classCatList.put("Asian American Studies", "ASAM");
        classCatList.put("Astronomy", "ASTR");
        classCatList.put("Biological Sciences", "BIOL");
        classCatList.put("Business Administration", "BUAD");
        classCatList.put("Chemistry and Biochemistry", "CHEM");
        classCatList.put("Chicana and Chicano Studies", "CHIC");
        classCatList.put("Child and Adolescent Studies", "CAS");
        classCatList.put("Chinese", "CHIN");
        classCatList.put("Cinema and Television Arts", "CTVA");
        classCatList.put("Civil & Environmental Engr", "EGCE");
        classCatList.put("Coll of Natural Sci & Math", "CNSM");
        classCatList.put("Comm Sciences and Disorders", "COMD");
        classCatList.put("Communications", "COMM");
        classCatList.put("Comparative Literature", "CPLT");
        classCatList.put("Computer Engineering", "EGCP");
        classCatList.put("Computer Science", "CPSC");
        classCatList.put("Counseling", "COUN");
        classCatList.put("Credential Studies", "CRED");
        classCatList.put("Criminal Justice", "CRJU");
        classCatList.put("Dance", "DANC");
        classCatList.put("EXED Business & Econ", "SBAE");
        classCatList.put("EXED Education", "SEDU");
        classCatList.put("EXED Engineer & Comp Sci", "SECS");
        classCatList.put("EXED Humanities/Soc Sci", "SHSS");
        classCatList.put("EXED Natural Sci & Math", "SNSM");
        classCatList.put("Economics", "ECON");
        classCatList.put("Education Leadership Doctorate", "EDD");
        classCatList.put("Educational Leadership", "EDAD");
        classCatList.put("Electrical Engineering", "EGEE");
        classCatList.put("Elementary & Bilingual Ed", "EDEL");
        classCatList.put("English", "ENGL");
        classCatList.put("English Education", "ENED");
        classCatList.put("Environmental Studies", "ENST");
        classCatList.put("Ethnic Studies", "ETHN");
        classCatList.put("Extended Education", "EXED");
        classCatList.put("Finance", "FIN");
        classCatList.put("French", "FREN");
        classCatList.put("General Engineering", "EGGN");
        classCatList.put("Geography and the Environment", "GEOG");
        classCatList.put("Geological Sciences", "GEOL");
        classCatList.put("German", "GRMN");
        classCatList.put("Graduate Studies", "GRAD");
        classCatList.put("History", "HIST");
        classCatList.put("Honors", "HONR");
        classCatList.put("Human Communication Studies", "HCOM");
        classCatList.put("Human Services", "HUSR");
        classCatList.put("Info Systems & Decision Sci", "ISDS");
        classCatList.put("Instructional Design & Tech", "IDT");
        classCatList.put("International Studies", "INST");
        classCatList.put("Italian", "ITAL");
        classCatList.put("Japanese", "JAPN");
        classCatList.put("Kinesiology", "KNES");
        classCatList.put("Korean", "KORE");
        classCatList.put("Latin American Studies", "LTAM");
        classCatList.put("Liberal Studies", "LBST");
        classCatList.put("Linguistics", "LING");
        classCatList.put("Management", "MGMT");
        classCatList.put("Marketing", "MKTG");
        classCatList.put("Master of Social Work", "MSW");
        classCatList.put("Math Education", "MAED");
        classCatList.put("Mathematics", "MATH");
        classCatList.put("Mechanical Engineering", "EGME");
        classCatList.put("Military Science", "MLSC");
        classCatList.put("Modern Languages & Literatures", "MLNG");
        classCatList.put("Music", "MUS");
        classCatList.put("Music Education", "MUSE");
        classCatList.put("Nursing", "NURS");
        classCatList.put("Persian", "PERS");
        classCatList.put("Philosophy", "PHIL");
        classCatList.put("Physics", "PHYS");
        classCatList.put("Political Science", "POSC");
        classCatList.put("Portuguese", "PORT");
        classCatList.put("Psychology", "PSYC");
        classCatList.put("Public Health", "PUBH");
        classCatList.put("Reading", "READ");
        classCatList.put("Registrar", "REG");
        classCatList.put("Religious Studies", "RLST");
        classCatList.put("Secondary Education", "EDSC");
        classCatList.put("Sociology", "SOCI");
        classCatList.put("Spanish", "SPAN");
        classCatList.put("Special Education", "SPED");
        classCatList.put("Teach English Spkrs Oth Lang", "TESL");
        classCatList.put("Theatre", "THTR");
        classCatList.put("Vietnamese", "VIET");
        classCatList.put("Women & Gender Studies", "WGST");
        //------------------------------------------------------------
        //putting all the values from the hashMap to the list(For the list view in the UI)
        //------------------------------------------------------------
        Set<String> keys = classCatList.keySet();
        for(String key: keys) {
            vect.add(key);
        }
        System.out.println("VALUES ENTERED");

        //------------------------------------------------------------
        //This part filters the list based on what the user inputs into the search bar
        //------------------------------------------------------------
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, vect);
        myList.setAdapter(adapter);
        mySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });

        //------------------------------------------------------------
        //THIS IS WHERE THE SEARCH BEGINS AFTER THE BUTTON IS CLICKED
        //------------------------------------------------------------
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classCat = classCatInput.getText().toString();
                classNumber = classNumberInput.getText().toString();
                BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
                navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

                searchView = (SearchView) findViewById(R.id.searchBar);
                mWebView = (WebView) findViewById(R.id.webview);
                //mWebView.setVisibility(View.GONE);
                mWebView.getSettings().setJavaScriptEnabled(true);
                mWebView.getSettings().setDomStorageEnabled(true);
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mWebView.loadUrl("https://my.fullerton.edu/Portal/Dashboard/Psoft/ClassScheduleSearch");

                WebViewClient webViewClient = new WebViewClient() {
                    @Override

                    public void onPageFinished(WebView view, String url) {
                        try {
                            TimeUnit.SECONDS.sleep(3);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Page finished here");
                        if(!doneLoading) {
                            JSFunc.alert(mWebView, "window.frames[\"ModalTop\"].document.getElementById('SSR_CLSRCH_WRK_SUBJECT_SRCH$0')");
                        }
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
                        if(!doneLoading) {
                            if (message == "null") {
                                //since this whole onJsAlert is being called for every js alert then we check the messsage through this every time
                                //sleep timer for letting elements load
                                JSFunc.alert(mWebView, "window.frames[\"ModalTop\"].document.getElementById('SSR_CLSRCH_WRK_SUBJECT_SRCH$0')");
                            } else { //set a boolean variable to true if webpage is done loading
                                doneLoading = true;
                                //handler.post(runnable);
                                JSFunc.modalTopValue(view, "SSR_CLSRCH_WRK_SUBJECT_SRCH$0", classCat); //class category]
                                JSFunc.modalTopValue(view, "SSR_CLSRCH_WRK_CATALOG_NBR$1", classNumber); //class number
                                JSFunc.modalTopClick(view, "CLASS_SRCH_WRK2_SSR_PB_CLASS_SRCH", "click"); //press the search button
                            }
                        }
                        if(message.split("|")[1] != "null"){
                            JSFunc.alert(mWebView, "window.frames[\"ModalTop\"].document.getElementById(\"ACE_$ICField48$0\").children[0].children[1].innerText");
                        }


                        return false; //disable alerts: return super.onJsAlert(view, url, message, result);
                    }
                };
                mWebView.setWebChromeClient(webChromeClient);
                mWebView.setWebViewClient(webViewClient);


            }
        });

       /* BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        searchView = (SearchView) findViewById(R.id.searchBar);
        mWebView = (WebView) findViewById(R.id.webview);
        //mWebView.setVisibility(View.GONE);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mWebView.loadUrl("https://my.fullerton.edu/Portal/Dashboard/Psoft/ClassScheduleSearch"); */


       /* WebViewClient webViewClient = new WebViewClient() {
            @Override

            public void onPageFinished(WebView view, String url) {
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Page finished here");
                if(!doneLoading) {
                    JSFunc.alert(mWebView, "window.frames[\"ModalTop\"].document.getElementById('SSR_CLSRCH_WRK_SUBJECT_SRCH$0')");
                }
                super.onPageFinished(view, url);


            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                System.out.println("URL PUSHED: " + request.getUrl());
                return super.shouldOverrideUrlLoading(view, request);
            }
        }; */
        //changing class category
       /* WebChromeClient webChromeClient = new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                System.out.println("MESSAGE GET: " + message);
                if(!doneLoading) {
                    if (message == "null") {
                        //since this whole onJsAlert is being called for every js alert then we check the messsage through this every time
                        //sleep timer for letting elements load
                        JSFunc.alert(mWebView, "window.frames[\"ModalTop\"].document.getElementById('SSR_CLSRCH_WRK_SUBJECT_SRCH$0')");
                    } else { //set a boolean variable to true if webpage is done loading
                        doneLoading = true;
                        //handler.post(runnable);
                        JSFunc.modalTopValue(view, "SSR_CLSRCH_WRK_SUBJECT_SRCH$0", classCat); //class category]
                        JSFunc.modalTopValue(view, "SSR_CLSRCH_WRK_CATALOG_NBR$1", classNumber); //class number
                        JSFunc.modalTopClick(view, "CLASS_SRCH_WRK2_SSR_PB_CLASS_SRCH", "click"); //press the search button
                    }
                }
                if(message.split("|")[1] != "null"){
                    JSFunc.alert(mWebView, "window.frames[\"ModalTop\"].document.getElementById(\"ACE_$ICField48$0\").children[0].children[1].innerText");
                }


                return false; //disable alerts: return super.onJsAlert(view, url, message, result);
            }
        }; */


        /*mWebView.setWebChromeClient(webChromeClient);
        mWebView.setWebViewClient(webViewClient); */
    }
    // Create the Handler
    /*private Handler handler = new Handler(); */

    // Define the code block to be executed
    /*private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // Insert custom code here
            JSFunc.alert(mWebView, "window.frames[\"ModalTop\"].document.getElementById('SSR_CLSRCH_WRK_SUBJECT_SRCH$0')+'|'+window.frames[\"ModalTop\"].document.getElementById('ACE_$ICField48$0')");

            // Repeat every 3 seconds
            handler.postDelayed(runnable, 3000);
        }
    }; */
}