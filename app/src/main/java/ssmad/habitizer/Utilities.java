/*
 *  Class Name: Utilities
 *  Version: 1.0
 *  Date: December 6th, 2017
 *  Copyright (c) TEAM SSMAD, CMPUT 301, University of Alberta - All Rights Reserved.
 *  You may use, distribute, or modify this code under terms and conditions of the
 *  Code of Students Behaviour at University of Alberta
 *
 */

package ssmad.habitizer;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * Checkf for server connection
 * @author Sadman
 * @version 1.0
 * @see ElasticsearchController
 * @since 1.0
 */
public class Utilities {
    //https://stackoverflow.com/questions/30343011/how-to-check-if-an-android-device-is-online
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager manager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            // Network is present and connected
            isAvailable = true;
        }
        return isAvailable;
    }
}
