package com.gabrielmorenoibarra.g;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

/**
 * Android Tools.
 * Created by Gabriel Moreno on 2016-11-08.
 */
public class G {

    /**
     * Check for all possible Internet providers.
     * This method requires the following permissions on Manifest:
     * <uses-permission android:name="android.permission.INTERNET"/>
     * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
     * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
     * @param context Related context.
     * @return true if there is an available connection.
     */
    public static boolean isConnectedToInternet(Context context) {
        final String TAG = Thread.currentThread().getStackTrace()[2].getMethodName();
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // If we are upper API 21
            Network[] networks = connectivityManager.getAllNetworks();
            NetworkInfo networkInfo;
            for (Network mNetwork : networks) {
                networkInfo = connectivityManager.getNetworkInfo(mNetwork);
                if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    Log.d(TAG, "Network " + networkInfo.getTypeName() + " connected.");
                    return true;
                }
            }
        } else { // API < 21
            if (connectivityManager != null) {
                // noinspection deprecation
                NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
                if (info != null) {
                    for (NetworkInfo anInfo : info) {
                        if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                            Log.d(TAG, "Network " + anInfo.getTypeName() + " connected.");
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}