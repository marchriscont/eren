package net.fullerton.eren;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import net.fullerton.eren.adapters.ClassesAdapter;
import net.fullerton.eren.handlers.ClassObject;
import net.fullerton.eren.handlers.JSFunc;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClassesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClassesFragment extends Fragment {

    public ClassesFragment() {
        // Required empty public constructor
    }

    public static ClassesFragment newInstance(String category, String key) {
        ClassesFragment fragment = new ClassesFragment();
        Bundle bundle = new Bundle();
        bundle.putString("category", category);
        bundle.putString("key", key);
        fragment.setArguments(bundle);
        return fragment;
    }

    private HomeActivity parentActivity;
    private ClassObject[] classesList;

    //TODO: Add Class Status  property to ClassObject
    //TODO: handle errors such as criteria not met if no open classes
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentActivity = (HomeActivity)getActivity();

        Toast.makeText(getContext(), getArguments().getString("key"), Toast.LENGTH_LONG).show();
    }

    private ValueCallback<String> handleList = new ValueCallback<String>() {
        @Override
        public void onReceiveValue(String value) {
            System.out.println("Received from handleList: " + value);
            if(!value.equals("null")){
                //classid,date,room,instructor
                value = value.replace("\"", "");
                String[] values = value.split("\\\\n", -1);
                System.out.println("Values length: " + values.length);
                classesList = new ClassObject[values.length-1];
                int it = 0;
                for(String items : values){
                    if(items.isEmpty())
                        continue;
                    String[] item = items.split(",", -1);
                    classesList[it++] = new ClassObject(item[0], item[1], item[2], item[3]);
                }
                ClassesAdapter cAdapter = new ClassesAdapter(getActivity(), getArguments().getString("key") + " " + searchView.getQuery().toString(), classesList);
                listView.setAdapter(cAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getActivity(), PreviewActivity.class);
                        Bundle b = new Bundle();
                        b.putString("cProf", classesList[position].getProf());
                        b.putString("cRate", "2.0");
                        b.putString("cDetails", classesList[position].getNumber() + " | " + classesList[position].getDate());
                        b.putString("cRoom", classesList[position].getRoom());
                        intent.putExtras(b);
                        startActivityForResult(intent, 2);
                    }
                });
                listView.invalidateViews();
            }
        }
    };

    private ValueCallback<String> isShowingConfirmation = new ValueCallback<String>() {
        @Override
        public void onReceiveValue(String value) {
            if (value.equals("true")) {
                JSFunc.modalTopClick(parentActivity.mWebViewHome, "DERIVED_CLS_DTL_NEXT_PB$280$", "click");
            }else{
                JSFunc.returnValues(parentActivity.mWebViewHome, "(function() { return window.frames[\"ModalTop\"].document.getElementById(\"DERIVED_CLS_DTL_NEXT_PB$280$\") != null }) ()", isShowingConfirmation);
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 2) {
            System.out.println(data.getStringExtra("MESSAGE"));
            JSFunc.modalTopValue(parentActivity.mWebViewHome, "DERIVED_REGFRM1_CLASS_NBR", data.getStringExtra("CLASS_CODE"));
            JSFunc.modalTopClick(parentActivity.mWebViewHome, "DERIVED_REGFRM1_SSR_PB_ADDTOLIST2$9$", "click");
            // Wait for page load
            JSFunc.returnValues(parentActivity.mWebViewHome, "(function() { return window.frames[\"ModalTop\"].document.getElementById(\"DERIVED_CLS_DTL_NEXT_PB$280$\") != null }) ()", isShowingConfirmation);
        }
    }

    //TODO: Handle case if no internet | session timeout | website is down
    private ValueCallback<String> classesFound = new ValueCallback<String>() {
        @Override
        public void onReceiveValue(String value) {
            System.out.println("Received from classesFound: " + value);
            if(value.equals("null")) {
                JSFunc.returnValues(parentActivity.mWebView, "(function() { return window.frames[\"ModalTop\"].document.getElementById('ACE_$ICField48$0').children[0].children[1].innerText }) ();", classesFound);
            }else{
                JSFunc.returnValues(parentActivity.mWebView, "(function() { var alertBox = \"\";for(var i=0; i<(window.frames[\"ModalTop\"].document.getElementById(\"ACE_$ICField48$0\").tBodies[0].rows.length)/2; i++){ alertBox += window.frames[\"ModalTop\"].document.getElementById(\"MTG_CLASS_NBR$\" + i).innerText + \",\";alertBox += window.frames[\"ModalTop\"].document.getElementById(\"MTG_DAYTIME$\" + i).innerText + \",\";alertBox += window.frames[\"ModalTop\"].document.getElementById(\"MTG_ROOM$\" + i).innerText + \",\";alertBox += window.frames[\"ModalTop\"].document.getElementById(\"MTG_INSTR$\" + i).innerText + \"\\n\";}return alertBox; }) ();", handleList);
            }
        }
    };

    //detect if page is relocated to classes list or error
    private ValueCallback<String> onSubmit = new ValueCallback<String>() {
        @Override
        public void onReceiveValue(String value) {
            System.out.println("Received from onSubmit: " + value);
            System.out.println("Testing if null " + (value.equals("null")));
            if(!value.equals("null")){
                //TODO:error; handle later 'search failed
                System.out.println("Failed @ onSubmit");
            }else{
                JSFunc.returnValues(parentActivity.mWebView, "(function() { return window.frames[\"ModalTop\"].document.getElementById('ACE_$ICField48$0').children[0].children[1].innerText }) ();", classesFound);
            }
        }
    };

    private SearchView searchView;
    private ListView listView;
    private TextView categoryView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View lI = inflater.inflate(R.layout.fragment_classes, container, false);
        searchView = (SearchView)lI.findViewById(R.id.searchview);
        listView = (ListView)lI.findViewById(R.id.listview);
        categoryView = (TextView)lI.findViewById(R.id.categoryview);

        searchView.setQueryHint("Course #");
        searchView.setIconified(false);
        searchView.setInputType(InputType.TYPE_CLASS_PHONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                JSFunc.modalTopValue(parentActivity.mWebView, "SSR_CLSRCH_WRK_SUBJECT_SRCH$0", getArguments().getString("key"));
                JSFunc.modalTopValue(parentActivity.mWebView, "SSR_CLSRCH_WRK_CATALOG_NBR$1", query);
                JSFunc.modalTopClick(parentActivity.mWebView, "CLASS_SRCH_WRK2_SSR_PB_CLASS_SRCH", "click");
                JSFunc.returnValues(parentActivity.mWebView, "(function() { return window.frames[\"ModalTop\"].document.getElementById('DERIVED_CLSMSG_ERROR_TEXT') }) ();", onSubmit);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        int searchCloseButtonId = searchView.getContext().getResources().getIdentifier("android:id/search_close_btn", null, null);
        ImageView _closeBtn = lI.findViewById(searchCloseButtonId);
        _closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
            }
        });

        categoryView.setText(getArguments().getString("category"));

        return lI;
    }
}