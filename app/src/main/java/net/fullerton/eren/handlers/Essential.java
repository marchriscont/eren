package net.fullerton.eren.handlers;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Essential {
    public static boolean isConnectedToInternet(Activity activity){
        ConnectivityManager connectivity = (ConnectivityManager)activity.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if(info.getDetailedState() == NetworkInfo.DetailedState.CONNECTED)
                return true;
        }
        return false;
    }
}
