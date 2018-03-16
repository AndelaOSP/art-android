package com.andela.art.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;
import com.andela.art.R;

/**
 * Created by Mugiwara_Munyi on 16/03/2018.
 */

public class NetworkUtil {

  public void isNetworkAvailable(Context context) {
    ConnectivityManager connectivityManager
        = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

    if(activeNetworkInfo == null || !activeNetworkInfo.isConnected()){

      Toast.makeText(context, context.getResources().getString(R.string.network_util_error_message), Toast.LENGTH_SHORT).show();
    }

  }

}
