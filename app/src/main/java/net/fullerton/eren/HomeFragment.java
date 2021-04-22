package net.fullerton.eren;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import net.fullerton.eren.adapters.ScheduleAdapter;
import net.fullerton.eren.handlers.JSFunc;
import net.fullerton.eren.handlers.ScheduleObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private TextView title;
    private ListView schedule;

    public HomeFragment() {
        // Required empty public constructor
    }
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    private void sleep(){
       try{
           Thread.sleep(1700);
       }catch(Exception e){}
    }

    private String[] terms;
    private ScheduleObject[] scheduleObj;

    private ValueCallback<String> allocSchedule = new ValueCallback<String>() {
        @Override
        public void onReceiveValue(String value) {
            System.out.println("allocSchedule received: " + value);
            if(!value.equals("null")){
                value = value.replace("\"", "");
                String[] values = value.split("\\|");
                scheduleObj = new ScheduleObject[values.length];
                int it = 0;
                for(String items : values){
                    if(items.isEmpty())
                        continue;
                    String[] item = items.split(",");
                    scheduleObj[it++] = new ScheduleObject(item[0].split("\\(")[0], item[1], item[2], item[3], item[4], item[5]);
                }
                ScheduleAdapter adapter = new ScheduleAdapter(getActivity(), scheduleObj);
                schedule.setAdapter(adapter);
                schedule.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(getActivity(), "Clicked on " + position, Toast.LENGTH_LONG).show();
                    }
                });
                schedule.invalidateViews();
            }else{
                System.out.println("allocSchedule Failed"); //TODO: circumvent error
            }
        }
    };

    private ValueCallback<String> onFinalLoad = new ValueCallback<String>() {
        @Override
        public void onReceiveValue(String value) {
            if(value.equals("true")){
                JSFunc.returnValues(parentActivity.mWebViewHome, "(function(){var test = window.frames[\"ModalTop\"].document.getElementById('trSTDNT_ENRL_SSVW$0_row1');var it = 2;while(test != null){test = window.frames[\"ModalTop\"].document.getElementById('trSTDNT_ENRL_SSVW$0_row' + it);it++;};var sBuilder = \"\";for(var i = 0; i < it-2; i++){sBuilder += window.frames[\"ModalTop\"].document.getElementById('E_CLASS_NAME$span$' + i).innerText.replace('\\n','');sBuilder += ',';sBuilder += window.frames[\"ModalTop\"].document.getElementById('E_CLASS_DESCR$' + i).innerText.replace('\\n','');sBuilder += ',';sBuilder += window.frames[\"ModalTop\"].document.getElementById('DERIVED_REGFRM1_SSR_MTG_SCHED_LONG$160$$' + i).innerText.replace('\\n','');sBuilder += ',';sBuilder += window.frames[\"ModalTop\"].document.getElementById('DERIVED_REGFRM1_SSR_MTG_LOC_LONG$161$$' + i).innerText.replace('\\n','');sBuilder += ',';sBuilder += window.frames[\"ModalTop\"].document.getElementById('DERIVED_REGFRM1_SSR_INSTR_LONG$162$$' + i).innerText.replace('\\n','');sBuilder += ',';sBuilder += window.frames[\"ModalTop\"].document.getElementById('STDNT_ENRL_SSVW_UNT_TAKEN$' + i).innerText.replace('\\n','');sBuilder += '|';};return sBuilder;})();", allocSchedule);
            }else{
                JSFunc.returnValues(parentActivity.mWebViewHome, "window.frames[\"ModalTop\"].document.getElementById('win0divDERIVED_REGFRM1_GROUP6GP').innerText.indexOf(\"Class Schedule\") > -1", onFinalLoad);
            }
        }
    };

    private ValueCallback<String> allocTerms = new ValueCallback<String>() {
        @Override
        public void onReceiveValue(String value) {
            System.out.println("allocTerms received: " + value);
            if(!value.equals("null")){
                value = value.replace("\"", "");
                String[] values = value.split("\\|");
                terms = new String[values.length-1];
                int it = 0;
                for(String item : values){
                    if(item.isEmpty() || item.equals("undefined"))
                        continue;
                    terms[it++] = item;
                }
                JSFunc.modalTopClick(parentActivity.mWebViewHome, "SSR_DUMMY_RECV1$sels$0$$0", "click");
                JSFunc.modalTopClick(parentActivity.mWebViewHome, "DERIVED_SSS_SCT_SSR_PB_GO", "click");
                JSFunc.returnValues(parentActivity.mWebViewHome, "window.frames[\"ModalTop\"].document.getElementById('win0divDERIVED_REGFRM1_GROUP6GP').innerText.indexOf(\"Class Schedule\") > -1", onFinalLoad);
            }else{
                JSFunc.returnValues(parentActivity.mWebViewHome, "(function() { var tgt = window.frames[\"ModalTop\"].document.getElementById('SSR_DUMMY_RECV1$scroll$0').children[0].children[1].innerText;var sBuilder=\"\";for(var i = 0; i < tgt.split('\\n').length/4; i++) { sBuilder += tgt.split('\\n')[2+4*i];sBuilder+=\"|\" } return sBuilder })();", allocTerms);
                //JSFunc.returnValues(parentActivity.mWebViewHome, "(function() { var tgt = window.frames[\"ModalTop\"].document.getElementById('SSR_DUMMY_RECV1$scroll$0').children[0].children[1].innerText;var sBuilder=\"\";for(var i = 0; i < tgt.split('\\n').length/8; i++) { sBuilder += tgt.split('\\n')[3+8*i];sBuilder+=\"|\" } return sBuilder })();", allocTerms);
            }
        }
    };

    private ValueCallback<String> test = new ValueCallback<String>() {
        @Override
        public void onReceiveValue(String value) {
            System.out.println("TEST VALUE: " + value);
        }
    };

    private ValueCallback<String> termsLoaded = new ValueCallback<String>() {
        @Override
        public void onReceiveValue(String value) {
            System.out.println("termsLoaded received: " + value);
            if(value.equals("true")){
                JSFunc.returnValues(parentActivity.mWebViewHome, "window.frames[\"ModalTop\"].document.getElementById('SSR_DUMMY_RECV1$scroll$0').children[0].children[1].innerText", test);
                JSFunc.returnValues(parentActivity.mWebViewHome, "(function() { var tgt = window.frames[\"ModalTop\"].document.getElementById('SSR_DUMMY_RECV1$scroll$0').children[0].children[1].innerText;var sBuilder=\"\";for(var i = 0; i < tgt.split('\\n').length/4; i++) { sBuilder += tgt.split('\\n')[2+4*i];sBuilder+=\"|\" } return sBuilder })();", allocTerms);
            }else{
                JSFunc.returnValues(parentActivity.mWebViewHome, "window.frames[\"ModalTop\"].document.getElementById('DERIVED_REGFRM1_TITLE1').innerText == \"Select Term\"", termsLoaded);
            }
        }
    };

    private ValueCallback<String> isPageLoaded = new ValueCallback<String>() {
        @Override
        public void onReceiveValue(String value) {
            System.out.println("isPageLoaded received: " + value);
            if(value.equals("true")){
                JSFunc.modalTopClick(parentActivity.mWebViewHome, "DERIVED_SSS_SCR_SSS_LINK_ANCHOR3", "click");
                // length: 24 --> 24/8 == 3 options
                // 3, 3 + 8*i
                // i == term SSR_DUMMY_RECV1$sels${i}$$0
                // Spring 2021|Summer 2021|Fall 2021| --> Length 4 when .split("|")
                JSFunc.returnValues(parentActivity.mWebViewHome, "window.frames[\"ModalTop\"].document.getElementById('DERIVED_REGFRM1_TITLE1').innerText == \"Select Term\"", termsLoaded);
            }else{
                JSFunc.returnValues(parentActivity.mWebViewHome, "window.frames[\"ModalTop\"].document.getElementById('DERIVED_SSS_SCR_SSS_LINK_ANCHOR3') != null", isPageLoaded);
            }
        }
    };

    public HomeActivity parentActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        parentActivity = ((HomeActivity)getActivity());
        parentActivity.mWebViewHome.loadUrl("https://my.fullerton.edu/Portal/Dashboard/PSoft/StudentCenter/");
        JSFunc.returnValues(parentActivity.mWebViewHome, "window.frames[\"ModalTop\"].document.getElementById('DERIVED_SSS_SCR_SSS_LINK_ANCHOR3') != null", isPageLoaded);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View iI = inflater.inflate(R.layout.fragment_home, container, false);
        title = (TextView)iI.findViewById(R.id.semesterview);
        schedule = (ListView)iI.findViewById(R.id.scheduleview);
        return iI;
    }
}