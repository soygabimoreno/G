package com.gabrielmorenoibarra.g;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Log utilities
 * Created by Gabriel Moreno on 2017-05-28.
 */
public class GLog {

    public static void sendLogByEmail(Context context, String email) {
        File file = getLogFile(context);
        String subject = "[DEBUG] " + Build.MANUFACTURER + " " + Build.MODEL + " v" + BuildConfig.VERSION_NAME +
                "(" + BuildConfig.VERSION_CODE + ") Android " + Build.VERSION.SDK_INT;
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email));
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        context.startActivity(Intent.createChooser(intent, "Send the Log File"));
    }

    public static File getLogFile(Context context) {
        String fileName = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date()) + ".log";
        File file = new File(context.getExternalCacheDir(), fileName);
        int pid = android.os.Process.myPid();
        try {
            String command = "logcat -d -v threadtime *:*";
            Process process = Runtime.getRuntime().exec(command);

            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                if (currentLine.contains(String.valueOf(pid))) {
                    sb.append(currentLine);
                    sb.append("\n");
                }
            }

            FileWriter fw = new FileWriter(file);
            fw.write(sb.toString());
            fw.close();
        } catch (IOException e) {
            Toast.makeText(context.getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }

        try {
            Runtime.getRuntime().exec("logcat -c"); // Clear the log
        } catch (IOException e) {
            Toast.makeText(context.getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
        return file;
    }
}