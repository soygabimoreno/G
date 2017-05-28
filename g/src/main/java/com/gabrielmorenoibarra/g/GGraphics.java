package com.gabrielmorenoibarra.g;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Static utilities related with graphic side.
 * Created by Gabriel Moreno on 2017-05-27.
 */
public class GGraphics {

    /**
     * Calculate size in Density Independent Pixel (dp or dip) depending on device screen density.
     * @param context Related context.
     * @param px Size to convert.
     * @return size in dp.
     */
    public static int pxToDp(Context context, int px) {
        return (int) Math.floor(px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));

    }

    /**
     * Calculate size in pixels depending on device screen density.
     * @param context Related context.
     * @param dp Independent ize to convert.
     * @return size in px.
     */
    public static int dpToPx(Context context, int dp) {
        return (int) Math.floor(dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }

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

    /**
     * Calculate the optimal preview size among several sizes.
     * @param sizes List screen sizes.
     * @param width Width of the device screen.
     * @param height Height of the device screen.
     * @return the proper size.
     */
    public static Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int width, int height) {
        final double ASPECT_TOLERANCE = 0.05;
        double targetRatio = (double) width / height;
        if (sizes == null) return null;
        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;
        for (Camera.Size size : sizes) { // Find size
            double ratio = (double) size.height / size.width;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
            if (Math.abs(size.height - height) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - height);
            }
        }

        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - height) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - height);
                }
            }
        }
        return optimalSize;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void hideSystemUI(Activity activity) {
        activity.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void showSystemUI(Activity activity) {
        activity.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    public static void addMarginInsetTop(int topMargin, View v) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
        params.topMargin += topMargin;
        v.setLayoutParams(params);
    }

    public static void addMarginInsetBottom(int bottomMargin, View v) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
        params.bottomMargin += bottomMargin;
        v.setLayoutParams(params);
    }
}