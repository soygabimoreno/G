package com.gabrielmorenoibarra.g;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Static utilities related with graphic side.
 * Created by Gabriel Moreno on 2017-05-27.
 */
public class GGraphics {

    /**
     * Log display metrics of the device.
     * @param activity Related activity.
     */
    public static void logMetrics(Activity activity) {
        final String TAG = Thread.currentThread().getStackTrace()[2].getMethodName();
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Log.d(TAG, "density: " + metrics.density);
        Log.d(TAG, "scaledDensity: " + metrics.scaledDensity);
        Log.d(TAG, "densityDpi: " + metrics.densityDpi);
        Log.d(TAG, "widthPixels: " + metrics.widthPixels);
        Log.d(TAG, "heightPixels: " + metrics.heightPixels);
        Log.d(TAG, "xdpi: " + metrics.xdpi);
        Log.d(TAG, "ydpi: " + metrics.ydpi);
    }

    /**
     * @param activity Related activity.
     * @return the height of the device screen in pixels.
     */
    public static int getHeightScreen(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    /**
     * @param activity Related activity.
     * @return the width of the device screen in pixels.
     */
    public static int getWidthScreen(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    /**
     * Set a proper layout parameters to a specific view.
     * @param context Related context.
     * @param v View to set.
     * @param videoProportion Ratio of video to play on View. E.g.: The view could be a TextureView.
     */
    public static void setProperLayoutParams(Context context, View v, float videoProportion) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels;
        float screenProportion = (float) screenHeight / (float) screenWidth;

        ViewGroup.LayoutParams lp = v.getLayoutParams();
        if (videoProportion < screenProportion) {
            lp.height = screenHeight;
            lp.width = (int) ((float) screenHeight / videoProportion);
        } else {
            lp.width = screenWidth;
            lp.height = (int) ((float) screenWidth * videoProportion);
        }
        v.setLayoutParams(lp);
    }
}