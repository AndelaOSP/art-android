package com.andela.art.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


import static junit.framework.TestCase.assertEquals;

/**
 * Created by Mugiwara_Munyi on 22/03/2018.
 */

public class NetworkUtilsTest {
    /**
     * Test suite for NetworkUtil class.
     */

    private Context context;
    private ConnectivityManager connectionManager;
    private boolean status;
    private NetworkInfo networkInfo;
    private NetworkUtil networkUtil;

    /**
     * Sets up the test before hand.
     *
     * @throws Exception if an Error occurs
     */

    @Before
    public void before() throws Exception {
        this.status = status;
        this.connectionManager = Mockito.mock(ConnectivityManager.class);
        this.networkInfo = Mockito.mock(NetworkInfo.class);
        this.context = Mockito.mock(Context.class);
        this.networkUtil = Mockito.mock(NetworkUtil.class);
        Mockito.when(context.getSystemService(Context.CONNECTIVITY_SERVICE))
                .thenReturn(connectionManager);
        Mockito.when(connectionManager.getActiveNetworkInfo()).thenReturn(networkInfo);
        Mockito.when(networkInfo.isConnected()).thenReturn(true);
    }

    /**
     * Test if the NetworkUtil.isNetworkAvailable yields true for an established internet connection
     * when given a Context.
     */

    @Test
    public void network_returns_true_if_internet_connection_present() {

        Mockito.when(networkInfo.isConnected()).thenReturn(status);
        assertEquals(status, networkUtil.isNetworkAvailable(context));
    }
}
