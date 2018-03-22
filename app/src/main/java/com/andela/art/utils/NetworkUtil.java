package com.andela.art.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Mugiwara_Munyi on 16/03/2018.
 */

public class NetworkUtil {

    /**
     * Empty Constructor for Network Util class.
     */

    public NetworkUtil() {
        //This is empty on purpose, nothing to see here.
    }

    /**
     * Checks for a device's internet connection status.
     *
     * @param context - Context
     * @return boolean
     */

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo.isConnected();
    }
}
