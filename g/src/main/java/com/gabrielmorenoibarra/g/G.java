package com.gabrielmorenoibarra.g;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AppOpsManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.hardware.display.DisplayManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * Android Tools.
 * Created by Gabriel Moreno on 2016-11-08.
 */
public class G {

    private static final int SECOND = 1000;
    private static final int MINUTE = 60 * SECOND;
    private static final int HOUR = 60 * MINUTE;
    private static final int DAY = 24 * HOUR;
    private static final long MONTH = 30L * DAY;
    private static final long YEAR = 365L * DAY;

    public static final int MAGNITUDE_SECONDS = 0;
    public static final int MAGNITUDE_MINUTES = 1;
    public static final int MAGNITUDE_HOURS = 2;
    public static final int MAGNITUDE_DAYS = 3;
    public static final int MAGNITUDE_MONTHS = 4;
    public static final int MAGNITUDE_YEARS = 5;

    /**
     * Require permissions: INTERNET, ACCESS_WIFI_STATE and ACCESS_NETWORK_STATE.
     * @param context Related context.
     * @return the name of the connected network or null if there in no internet.
     */
    public static String getConnectedNetworkName(Context context) {
        final String TAG = Thread.currentThread().getStackTrace()[2].getMethodName();
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // If we are upper API 21
            Network[] networks = connectivityManager.getAllNetworks();
            NetworkInfo networkInfo;
            for (Network mNetwork : networks) {
                networkInfo = connectivityManager.getNetworkInfo(mNetwork);
                if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    String netWorkName = networkInfo.getTypeName();
                    Log.i(TAG, networkInfo.getTypeName() + " network connected!");
                    return netWorkName;
                }
            }
        } else { // API < 21
            if (connectivityManager != null) {
                // noinspection deprecation
                NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
                if (info != null) {
                    for (NetworkInfo anInfo : info) {
                        if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                            String netWorkName = anInfo.getTypeName();
                            Log.i(TAG, anInfo.getTypeName() + " network connected!");
                            return netWorkName;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Create a custom <code>ProgressDialog</code>.
     * @param context Related context.
     * @param layoutResID Related resource.
     * @param isCancelable Whether it has to be cancelable or not.
     * @return the <code>ProgressDialog</code>.
     */
    public static ProgressDialog createProgressDialog(Context context, int layoutResID, boolean isCancelable) {
        ProgressDialog dialog = new ProgressDialog(context);
        try {
            dialog.show(); // We have to show before the 'setContentView' method
            dialog.hide(); // And hide it for it was transparent to the user
        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }
        dialog.setCancelable(isCancelable);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL); // To improve UX
        }
        dialog.setContentView(layoutResID);
        return dialog;
    }

    /**
     * Remove redundant items in a <code>List</code>.
     * @param list <code>List</code> to clean.
     * @return the cleaned <code>List</code>.
     */
    public static List<String> removeRepetitions(List<String> list) {
        HashSet<String> hs = new HashSet<>();
        hs.addAll(list); // Add the list removing all repeated elements
        list.clear(); // Clear the list before reloading
        list.addAll(hs); // Add the list without repetitions
        return list;
    }

    /**
     * Prepare and open email application.
     * @param context Related context.
     * @param target Address where email will be sent.
     * @param subject Subject of the email.
     * @param body Body of the email.
     */
    public static void prepareEmail(Context context, String target, String subject, String body) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{target});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        context.startActivity(intent);
    }

    /**
     * Prepare phone keypad to make a call.
     * @param phoneNumber Phone number.
     * @return an <code>Intent</code> to make the call.
     */
    public static Intent prepareCall(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber.trim()));
        return intent;
    }

    /**
     * @param context Related context.
     * @return the <code>versionName</code> of the application.
     */
    public static String getVersionName(Context context) {
        String versionName = null;
        try {
            versionName = context.getPackageManager().getPackageInfo(context.getApplicationContext().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * Limit duration of the application.<p>
     * @param context Related context.
     * @param year Year of app expiration.
     * @param month Month of app expiration.
     * @param date Day of app expiration.
     */
    public void limitAppDuration(Context context, int year, int month, int date) {
        final String TAG = Thread.currentThread().getStackTrace()[2].getMethodName();
        // Get current date:
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1; // There is to add 1 to obtain the right month
        int currentDate = calendar.get(Calendar.DATE);

        Log.d(TAG, "Today is '" + currentYear + "-" + currentMonth + "-" + currentDate + "'");
        Log.d(TAG, "The app expires on '" + year + "-" + month + "-" + date + "'");

        // Nested conditions to check that today app has expired:
        if (year <= currentYear) {
            if (month <= currentMonth) {
                if (date <= currentDate) {
                    String text = "The app has expired";
                    Log.w(TAG, text);
                    Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                    System.exit(0); // Forced close and stop here
                }
                if (date - 7 <= currentDate) {
                    String text = "The app expires in less than one week!";
                    Log.w(TAG, text);
                    Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        }
    }

    /**
     * Show overflow menu on all devices.
     * @param context Related context.
     */
    public static void showOverflowMenu(Context context) {
        try {
            ViewConfiguration config = ViewConfiguration.get(context);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param startX Start position in the abscissa.
     * @param endX End position in the abscissa.
     * @param startY Start position in the ordinate.
     * @param endY End position in the ordinate.
     * @return if a touch event has been a click.
     */
    public static boolean isAClick(float startX, float endX, float startY, float endY) {
        float differenceX = Math.abs(startX - endX);
        float differenceY = Math.abs(startY - endY);
        return !(differenceX > 5 || differenceY > 5);
    }

    /**
     * @param context Related context.
     * @return if the device has camera.
     */
    public static boolean hasCamera(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    /**
     * @param context Related context.
     * @return whether the device has a flash available or not.
     */
    public static boolean hasFlash(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    /**
     * Log key hashes for a specific package. It is util for deploy facebook apps for example.
     * @param context Related context.
     * @param packageName Name of the application package.
     */
    public static void logKeyHashes(Context context, String packageName) {
        final String TAG = Thread.currentThread().getStackTrace()[2].getMethodName();
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    packageName,
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d(TAG, "KeyHash: " + Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check if one app is installed from its package name.
     * @param context Related context.
     * @param packageName Package name of the application.
     * @return true if it is installed.
     */
    public static boolean isPackageInstalled(Context context, String packageName) {
        try {
            return context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES) != null;
        } catch (PackageManager.NameNotFoundException e) {
            // Not show exception required
            return false;
        }
    }

    /**
     * Gives opacity to an array of <code>View</code> when they are pressed (separately) and return to full opacity when user unpresses some of them.
     * @param views Related views.
     */
    public static void setAlphaSelector(View... views) {
        for (View view : views) {
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) { // Pressed
                        v.setAlpha(0.5f); // Half visible
                    } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) { // Unpressed
                        v.setAlpha(1); // Full opacity
                    }
                    return false;
                }
            });
        }
    }

    /**
     * Gives opacity to an <code>View</code> when it is pressed and return to full opacity when user release it, and set invisible View below it.
     * @param v View above.
     * @param vBelow View below the image that the one that is really "clicking".
     */
    public static void setAlphaSelectorToViewAndBelowView(View v, final View vBelow) {
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setAlpha(0.5f);
                    vBelow.setAlpha(0); // Invisible when other is clicked
                } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    v.setAlpha(1);
                    vBelow.setAlpha(1);
                }
                return false;
            }
        });
    }

    /**
     * Gives darkness to an array of <code>ImageView</code> when they are pressed (separately) and return to full opacity when user unpresses some of them.
     * @param views Related views.
     */
    public static void setDarkSelector(View... views) {
        for (View view : views) {
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) { // Pressed
                        ((ImageView) v).setColorFilter(Color.rgb(123, 123, 123), PorterDuff.Mode.MULTIPLY);
                    } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) { // Unpressed
                        ((ImageView) v).clearColorFilter();
                    }
                    return false;
                }
            });
        }
    }

    /**
     * Disables any views and enable them again after a delay.
     * It makes more robust events when for example user clicks so fast buttons.
     * @param views Views to perform.
     */
    public static void disableAndEnableAfter(final View... views) {
        for (View view : views) {
            view.setEnabled(false); // We make more robust view. It only can be clicked once until pass the time
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (View view : views) {
                    view.setEnabled(true); // Enable again all of them
                }
            }
        }, 1000);
    }

    /**
     * @param context Related context.
     * @return if application has push notifications enabled.
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static boolean areNotificationsEnabled(Context context) {
        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE); // Application operation tracking
        ApplicationInfo appInfo = context.getApplicationInfo();
        String packageName = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid; // Kernel user id
        Class appOpsClass; // Context.APP_OPS_MANAGER
        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod("checkOpNoThrow", Integer.TYPE, Integer.TYPE, String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField("OP_POST_NOTIFICATION");
            int value = (int) opPostNotificationValue.get(Integer.class);
            return ((int) checkOpNoThrowMethod.invoke(mAppOps, value, uid, packageName) == AppOpsManager.MODE_ALLOWED);
        } catch (ClassNotFoundException | NoSuchMethodException | NoSuchFieldException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Show app settings info Activity.
     * @param context Related context.
     */
    public static void showAppInfoScreen(Context context) {
        try {
            Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + context.getApplicationContext().getPackageName()));
            context.startActivity(intent); // Open the specific App Info page
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS); // Open the generic Apps page
            context.startActivity(intent);
        }
    }

    /**
     * @param context Related context.
     * @return true when screen is on.
     */
    public static boolean isScreenOn(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            DisplayManager dm = (DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE);
            boolean screenOn = false;
            for (Display display : dm.getDisplays()) {
                if (display.getState() != Display.STATE_OFF) {
                    screenOn = true;
                }
            }
            return screenOn;
        } else {
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            //noinspection deprecation
            return pm.isScreenOn();
        }
    }

    /**
     * Simply close and restart an Activity.
     * @param activity Activity to restart.
     */
    public static void refreshActivity(Activity activity) {
        activity.finish();
        activity.startActivity(activity.getIntent());
    }

    //    private static final NavigableMap<Long, String> suffixes = new TreeMap<>();
//
//    static {
//        suffixes.put(1_000L, "k");
//        suffixes.put(1_000_000L, "M");
//        suffixes.put(1_000_000_000L, "G");
//        suffixes.put(1_000_000_000_000L, "T");
//        suffixes.put(1_000_000_000_000_000L, "P");
//        suffixes.put(1_000_000_000_000_000_000L, "E");
//    }

    public static String formatNumber(long value) {
        NavigableMap<Long, String> suffixes = new TreeMap<>();
        suffixes.put(1_000L, "k");
        suffixes.put(1_000_000L, "M");

        //Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
        if (value == Long.MIN_VALUE) return formatNumber(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + formatNumber(-value);
        if (value < 1000) return Long.toString(value); //deal with easy case

        Map.Entry<Long, String> e = suffixes.floorEntry(value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();

        long truncated = value / (divideBy / 10); //the number part of the output times 10
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }

    public static String formatNumberWithSeparator(long number) {
        return NumberFormat.getInstance(Locale.getDefault()).format(number);
    }

    public static String formatTime(long ms, String[] magnitudes) {
        String year = " " + magnitudes[0];
        String years = " " + magnitudes[1];
        String month = " " + magnitudes[2];
        String months = " " + magnitudes[3];
        String day = " " + magnitudes[4];
        String days = " " + magnitudes[5];
        String hour = " " + magnitudes[6];
        String hours = " " + magnitudes[7];
        String minute = " " + magnitudes[8];
        String minutes = " " + magnitudes[9];
        String lessThanAMinuteAgo = " " + magnitudes[10];

        StringBuilder sb = new StringBuilder();
        if (ms >= YEAR) {
            if (ms < YEAR * 2) {
                return sb.append(ms / YEAR).append(year).toString();
            } else {
                return sb.append(ms / YEAR).append(years).toString();
            }
        }
        if (ms >= MONTH) {
            if (ms < MONTH * 2) {
                return sb.append(ms / MONTH).append(month).toString();
            } else {
                return sb.append(ms / MONTH).append(months).toString();
            }
        }
        if (ms >= DAY) {
            if (ms < DAY * 2) {
                return sb.append(ms / DAY).append(day).toString();
            } else {
                return sb.append(ms / DAY).append(days).toString();
            }
        }
        if (ms >= HOUR) {
            if (ms < HOUR * 2) {
                return sb.append(ms / HOUR).append(hour).toString();
            } else {
                return sb.append(ms / HOUR).append(hours).toString();
            }
        }
        if (ms >= MINUTE) {
            if (ms < MINUTE * 2) {
                return sb.append(ms / MINUTE).append(minute).toString();
            } else {
                return sb.append(ms / MINUTE).append(minutes).toString();
            }
        }
        return sb.append(lessThanAMinuteAgo).toString();
    }

    public static void main(String[] args) {
        System.out.println("RES: " + getMillis(MAGNITUDE_YEARS, 1));
    }

    public static long getMillis(int magnitude, int time) {
        switch (magnitude) {
            case MAGNITUDE_SECONDS:
                return time * SECOND;
            case MAGNITUDE_MINUTES:
                return time * MINUTE;
            case MAGNITUDE_HOURS:
                return time * HOUR;
            case MAGNITUDE_DAYS:
                return time * DAY;
            case MAGNITUDE_MONTHS:
                return time * MONTH;
            case MAGNITUDE_YEARS:
                return time * YEAR;
        }
        return -1;
    }

    private static Toast toast;

    /**
     * Show a toast to the user.
     * @param context Related context.
     * @param text Text to show.
     * @param duration Duration whereas the text will be shown.
     */
    public static void showToast(Context context, String text, int duration) {
        if (toast != null) toast.cancel();
        toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}