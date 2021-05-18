package net.fullerton.eren;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import net.fullerton.eren.adapters.ClassesAdapter;
import net.fullerton.eren.handlers.Essential;
import net.fullerton.eren.handlers.JSFunc;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    public SearchFragment() {
        // Required empty public constructor
    }
    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    private ListView list;
    private ListView suggestList;
    private SearchView search;

    private boolean classesPageLoaded = false;

    public ValueCallback<String> pageLoadedTest = new ValueCallback<String>(){
        public void onReceiveValue(String value) {
            System.out.println("Received value: " + value);
            if(!value.equals("null")){
                // On page load, set semester
                parentActivity.mWebView.evaluateJavascript("(function() {var currentVal = window.frames[\"ModalTop\"].document.getElementById(\"CLASS_SRCH_WRK2_STRM$35$\").value;window.frames[\"ModalTop\"].document.getElementById(\"CLASS_SRCH_WRK2_STRM$35$\").value = (parseInt(currentVal) + 4).toString();})()", null);
                classesPageLoaded = true;
            }else{
                JSFunc.returnValues(parentActivity.mWebView, "(function() { return window.frames[\"ModalTop\"].document.getElementById('SSR_CLSRCH_WRK_SUBJECT_SRCH$0') })();", pageLoadedTest);
            }
        };
    };

    public ValueCallback<String> getOptions = new ValueCallback<String>() {
        @Override
        public void onReceiveValue(String value) {
            value = value.replace("\"", "");
            String[] values = value.split("\\\\n", -1);
            for (String item : values) {
                if(item.isEmpty())
                    continue;
                String[] split = item.split("\\|", -1);
                availableClasses.put(split[0], split[1]);
                keys.add(split[0]);
            } //TODO: handle errors?
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, keys);
            System.out.println("Keys size: " + keys.size());
            suggestList.setAdapter(adapter);
            suggestList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(!classesPageLoaded)
                        JSFunc.returnValues(parentActivity.mWebView, "(function() { return window.frames[\"ModalTop\"].document.getElementById('SSR_CLSRCH_WRK_SUBJECT_SRCH$0') })();", pageLoadedTest);
                    else{
                        category = availableClasses.get(parent.getItemAtPosition(position));
                        FragmentManager fragmentManager = parentActivity.getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.view2, new ClassesFragment().newInstance(parent.getItemAtPosition(position).toString(), category)).addToBackStack("search").commit();
                    }
                }
            });
            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        }
    };

    public ValueCallback<String> isSearching = new ValueCallback<String>() {
        @Override
        public void onReceiveValue(String value) {
            System.out.println("RECEIVED VALUE: " + value);
            if(value.equals("true")){
                if(availableClasses.isEmpty())
                    JSFunc.returnValues(parentActivity.mWebView,
                            "(function() { var sBuilder = \"\";var items = window.frames[\"ModalTop\"].document.getElementById('SSR_CLSRCH_WRK_SUBJECT_SRCH$0').children;for(let item in items){ if(items[item].value) { sBuilder += (items[item].innerText + '|' + items[item].value) + '\\n' } };return sBuilder; }) ();", getOptions);
            }else{
                JSFunc.returnValues(parentActivity.mWebView, "window.frames[\"ModalTop\"].document.getElementById('DERIVED_REGFRM1_TITLE1').textContent == \"Enter Search Criteria\"", isSearching);
            }
        }
    };

    public HomeActivity parentActivity;
    private HashMap<String, String> availableClasses = new HashMap<>();
    private Vector<String> keys = new Vector<String>();

    //TODO: Currently resets when switch out and switch back on nav menu. Will add saved states later.
    public String category;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentActivity = ((HomeActivity)getActivity());
        parentActivity.mWebView.loadUrl("https://my.fullerton.edu/Portal/Dashboard/Psoft/ClassScheduleSearch");
        JSFunc.returnValues(parentActivity.mWebView, "window.frames[\"ModalTop\"].document.getElementById('DERIVED_REGFRM1_TITLE1').textContent == \"Enter Search Criteria\"", isSearching);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View lI = inflater.inflate(R.layout.fragment_search, container, false);
        suggestList = (ListView)lI.findViewById(R.id.searchsuggestions);
        search = (SearchView)lI.findViewById(R.id.searchview);
        search.setIconified(false);
        return lI;
    }
}