package com.gabrielmorenoibarra.g;

import android.util.Log;

/**
 * Static Log Utilities.
 * Created by Gabriel Moreno on 2017-05-28.
 */
public class GLog {

    public static boolean debug;

    public static void init(boolean debug) {
        GLog.debug = debug;
    }

    public static void d(String tag, String msg) {
        if (debug) Log.d(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (debug) Log.i(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (debug) Log.w(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (debug) Log.e(tag, msg);
    }

    public static String getMethodName(String msg) {
        return debug ? msg : null;
    }
}