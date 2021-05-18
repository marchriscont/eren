package net.fullerton.eren;

import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.widget.ListView;

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

    private ValueCallback<String> isLoadedSemesterSelect = new ValueCallback<String>() {
        @Override
        public void onReceiveValue(String value) {
            if(value.equals("true")) {
                parentActivity.mWebViewCart.evaluateJavascript("window.frames[\"ModalTop\"].document.getElementById('SSR_DUMMY_RECV1$sels$2$$0').checked = true", null);
                JSFunc.modalTopClick(parentActivity.mWebViewCart, "DERIVED_SSS_SCT_SSR_PB_GO", "click");
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