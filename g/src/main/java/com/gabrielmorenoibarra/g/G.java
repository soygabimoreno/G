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
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.display.DisplayManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

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

    /**
     * Create a custom <code>ProgressDialog</code>.
     * @param context Related context.
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
     * Hide soft keyboard.
     * @param context Related context.
     */
    public static void hideKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        View v = ((Activity) context).getCurrentFocus(); // Check we have some view focused
        if (v == null) return;
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
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
     * Log key hashes for a specific package. It is util for deploy facebook apps for example.
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
     * Format a number of milliseconds to '00:00:00' format.
     * @param milliseconds Number of milliseconds.
     * @return a <code>String</code> formatted.
     */
    public static String toTimeFormat(long milliseconds) {
        return String.format(Locale.getDefault(), "%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(milliseconds),
                TimeUnit.MILLISECONDS.toMinutes(milliseconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliseconds)),
                TimeUnit.MILLISECONDS.toSeconds(milliseconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds)));
    }

    /**
     * Format a String as a phone number.
     * @param number the phone number.
     * @return a <code>String</code> formatted.
     */
    public static String toPhoneFormat(String number) {
        if (number.length() > 7) { // To ensure we are avoiding crash
            java.text.MessageFormat phoneMsgFmt = new java.text.MessageFormat("{0} {1} {2} {3}");
            String[] phoneNumArr = {
                    number.substring(0, 3),
                    number.substring(3, 5),
                    number.substring(5, 7),
                    number.substring(7)};
            return phoneMsgFmt.format(phoneNumArr);
        }
        return number;
    }

    /**
     * Create and return a <code>MediaPlayer</code> for audio playback.
     * @param context Related context.
     * @param assetsFilePath Path to the media resource inside assets folder.
     * @return a prepared <code>MediaPlayer</code> ready to play.
     */
    public static MediaPlayer createMediaPlayer(Context context, String assetsFilePath, int streamType) {
        try {
            AssetManager asset = context.getAssets();
            AssetFileDescriptor afd = asset.openFd(assetsFilePath);
            MediaPlayer player = new MediaPlayer();
            player.setAudioStreamType(streamType);
            player.reset(); // Making sure it is in IDLE state...
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            player.prepare();
            afd.close();
            return player;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Plays a file with MediaPlayer.
     * @param fileName Absolute path name of the file to play.
     * @param looping Indicates if multimedia file has to be played as a loop.
     */
    public static void play(String fileName, boolean looping) {
        MediaPlayer mp = new MediaPlayer();
        FileInputStream fs = null;
        FileDescriptor fd = null;
        try {
            fs = new FileInputStream(new File(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if (fs != null) {
                fd = fs.getFD();
            }
            mp.setDataSource(fd);
            mp.prepare();
            mp.setLooping(looping);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mp.start();
    }

    /**
     * Check if one app is installed from its package name.
     * @param context Related context.
     * @param packageName Package name of the application.
     * @return true if it is installed.
     */
    public static boolean isPackageInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            // Not show exception required
            return false;
        }
    }

    /**
     * Gives opacity to a <code>View</code> when it is pressed and return to full opacity when user release it.
     * @param v View to perform.
     */
    public static void setAlphaSelector(View v) {
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setAlpha(0.5f);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.setAlpha(1);
                }
                return false;
            }
        });
    }

    /**
     * Gives opacity to an <code>View</code> when it is pressed and return to full opacity when user release it, and set invisible View below it.
     * @param v View above.
     * @param vBelow View below the image that the one that is really "clicking".
     */
    public static void setAlphaSelector(View v, final View vBelow) {
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setAlpha(0.5f);
                    vBelow.setAlpha(0); // Invisible when other is clicked
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.setAlpha(1);
                    vBelow.setAlpha(1);
                }
                return false;
            }
        });
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
        }, 3000);
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
     * @param context
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
     * Checks if external storage is available for read and write.
     * @return true if available.
     */
    public static boolean isExternalStorageWritable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * Checks if external storage is available to at least read.
     * @return true if available.
     */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    /**
     * Copy a <code>File</code>.
     * @param dirName Name of the directory to storage on external memory.
     * @param from Source file name. E.g.: context.getFilesDir().getPath() + "/share.wav";
     * @param to Destiny file name. E.g.: dir.getAbsolutePath() + "/share.wav"
     * @return the file size (number of bytes). If it returns -1, it is due to the action failed.
     */
    public static int copyFileToExternalStorage(String dirName, String from, String to) {
        File dir = new File(Environment.getExternalStorageDirectory(), dirName);
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                return GJavaTools.copyFile(from, to);
            }
        }
        return -1;
    }
}