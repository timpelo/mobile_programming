package com.tiko.tamk.secondapp;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Jani on 9.3.2016.
 */
public class Debug {
    static int debugLevel = -1;

    public static void loadDebug(Context context) {
        try {
            String tmp = context.getResources().getString(R.string.debuglevel);
            try {
                debugLevel = Integer.parseInt(tmp);
            } catch (NumberFormatException e) {
                Log.w("WARNING", "debug level in xml is not integer");
            }
        } catch(NullPointerException e) {
            Log.w("WARNING", "debug level is not in resource xml");
        }
    }

    public static void print(String className, String methodName, String msg, int level) {

        if(level <= debugLevel && BuildConfig.DEBUG == true) {
            String TAG = className;
            msg = methodName + " message:" + msg;
            Log.d(TAG, msg);
        }
    }
}
