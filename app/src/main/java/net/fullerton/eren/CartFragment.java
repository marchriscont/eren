package net.fullerton.eren;

import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import net.fullerton.eren.adapters.ScheduleAdapter;
import net.fullerton.eren.handlers.JSFunc;
import net.fullerton.eren.handlers.ScheduleObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {

    private ListView cart;

    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance() {
        CartFragment fragment = new CartFragment();
        return fragment;
    }

    private ScheduleObject[] scheduleObj;

    private ValueCallback<String> allocCart = new ValueCallback<String>() {
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
                    scheduleObj[it++] = new ScheduleObject(item[0].split("\\(")[0], item[1], item[2], item[3], "test", "test");
                }
                ScheduleAdapter adapter = new ScheduleAdapter(getActivity(), scheduleObj);
                cart.setAdapter(adapter);
                cart.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(getActivity(), "Clicked on " + position, Toast.LENGTH_LONG).show();
                    }
                });
                cart.invalidateViews();
            }else{
                System.out.println("allocSchedule Failed"); //TODO: circumvent error
            }
        }
    };

    private ValueCallback<String> isLoadedCart = new ValueCallback<String>() {
        @Override
        public void onReceiveValue(String value) {
            if(value.equals("true")){
                JSFunc.returnValues(parentActivity.mWebViewCart, "" +
                        "(function(){" +
                        "var test = window.frames[\"ModalTop\"].document.getElementById('trSSR_REGFORM_VW$0_row1');" +
                        "var it = 2;" +
                        "while(test != null){" +
                        "   test = window.frames[\"ModalTop\"].document.getElementById('trSSR_REGFORM_VW$0_row' + it);" +
                        "   it++;" +
                        "   var sBuilder = \"\";" +
                        "   for(var i = 0; i < it-2; i++){" +
                        "       sBuilder += window.frames[\"ModalTop\"].document.getElementById('win0divP_CLASS_NAME$'+i).innerText.replace('\\n','');" +
                        "       sBuilder += ',';" +
                        "       sBuilder += window.frames[\"ModalTop\"].document.getElementById('DERIVED_REGFRM1_SSR_MTG_SCHED_LONG$'+i).innerText.replace('\\n','');" +
                        "       sBuilder += ',';" +
                        "       sBuilder += window.frames[\"ModalTop\"].document.getElementById('DERIVED_REGFRM1_SSR_MTG_LOC_LONG$'+i).innerText.replace('\\n','');" +
                        "       sBuilder += ',';" +
                        "       sBuilder += window.frames[\"ModalTop\"].document.getElementById('DERIVED_REGFRM1_SSR_INSTR_LONG$'+i).innerText.replace('\\n','');" +
                        "       sBuilder += ',';" +
                        "       sBuilder += window.frames[\"ModalTop\"].document.getElementById('SSR_REGFORM_VW_UNT_TAKEN$'+i).innerText.replace('\\n','');" +
                        //"       sBuilder += ',';" +
                        //"       sBuilder += window.frames[\"ModalTop\"].document.getElementById('STDNT_ENRL_SSVW_UNT_TAKEN$' + i).innerText.replace('\\n','');" +
                        "       sBuilder += '|';" +
                        "   };" +
                        "};" +
                        "return sBuilder;" +
                        "})();", allocCart);
            }else{
                JSFunc.returnValues(parentActivity.mWebViewCart, "(function() { return window.frames[\"ModalTop\"].document.getElementById('SSR_REGFORM_VW$scroll$0') != null }) ()", isLoadedCart);
            }
        }
    };

    private ValueCallback<String> isLoadedSemesterSelect = new ValueCallback<String>() {
        @Override
        public void onReceiveValue(String value) {
            if(value.equals("true")) {
                parentActivity.mWebViewCart.evaluateJavascript("window.frames[\"ModalTop\"].document.getElementById('SSR_DUMMY_RECV1$sels$2$$0').checked = true", null);
                JSFunc.modalTopClick(parentActivity.mWebViewCart, "DERIVED_SSS_SCT_SSR_PB_GO", "click");
                JSFunc.returnValues(parentActivity.mWebViewCart, "(function() { return window.frames[\"ModalTop\"].document.getElementById('SSR_REGFORM_VW$scroll$0') != null }) ()", isLoadedCart);
            }else{
                System.out.println("Loading Semester select page");
                JSFunc.returnValues(parentActivity.mWebViewCart, "(function() { return window.frames[\"ModalTop\"].document.getElementById('DERIVED_SSS_SCT_SSR_PB_GO') != null }) ()", isLoadedSemesterSelect);
            }
        }
    };

    private ValueCallback<String> isLoadedPortal = new ValueCallback<String>() {
        @Override
        public void onReceiveValue(String value) {
            if(value.equals("true")) {
                JSFunc.modalTopClick(parentActivity.mWebViewCart, "DERIVED_SSS_SCR_SSS_LINK_ANCHOR3", "click");
                JSFunc.returnValues(parentActivity.mWebViewCart, "(function() { return window.frames[\"ModalTop\"].document.getElementById('DERIVED_SSS_SCT_SSR_PB_GO') != null }) ()", isLoadedSemesterSelect);
            } else {
                System.out.println("Loading portal");
                JSFunc.returnValues(parentActivity.mWebViewCart, "(function() { return window.frames[\"ModalTop\"].document.getElementById('DERIVED_SSS_SCL_SS_WEEKLY_SCHEDULE') != null }) ()", isLoadedPortal);
            }
        }
    };
    public HomeActivity parentActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentActivity = (HomeActivity) getActivity();
        parentActivity.mWebViewCart.loadUrl("https://my.fullerton.edu/Portal/Dashboard/PSoft/StudentCenter/");
        JSFunc.returnValues(parentActivity.mWebViewCart, "(function() { return window.frames[\"ModalTop\"].document.getElementById('DERIVED_SSS_SCL_SS_WEEKLY_SCHEDULE') != null }) ()", isLoadedPortal);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        cart = (ListView) view.findViewById(R.id.cartview);
        return view;
    }
}