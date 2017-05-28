package com.gabrielmorenoibarra.g;

import android.content.Context;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.gabrielmorenoibarra.g.java.GJavaTools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Utilities to deal with files.
 * Created by Gabriel Moreno on 2017-05-28.
 */
public class GFile {

    /**
     * Copy a <code>Uri</code>.
     * @param uri Source uri.
     * @param to Destiny file name.
     * @return the file size in number of bytes. If it returns -1, it is due to the action failed.
     */
    public static int copyUri(Context context, Uri uri, String to) {
        try {
            int byteSum = 0;
            int byteRead;
            InputStream is = context.getContentResolver().openInputStream(uri);
            if (is != null) {
                OutputStream os = new FileOutputStream(to);
                byte[] buffer = new byte[1444];
                while ((byteRead = is.read(buffer)) != -1) {
                    byteSum += byteRead;
                    os.write(buffer, 0, byteRead);
                }
                is.close();
                os.close();
            }
            return byteSum;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Save a <code>String</code> in a text file within internal storage.
     * It does not need any permission.
     * @param context Related context.
     * @param fileName File name.
     * @param line Line to write within text file.
     * @param mode Write mode: Context.MODE_PRIVATE, Context.MODE_APPEND...
     */
    public static void saveFile(Context context, String fileName, String line, int mode) {
        try {
            OutputStreamWriter osw = new OutputStreamWriter(context.openFileOutput(fileName, mode));
            osw.write(line + "\n");
            osw.flush();
            osw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read a text file.
     * @param context Related context.
     * @param fileName File name.
     * @return the text in the file.
     */
    public static String readTextFile(Context context, String fileName) {
        String line;
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(context.openFileInput(fileName)));
            while ((line = br.readLine())!= null) {
                sb.append(line).append("\n");
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * Delete all files within internal storage.
     * @param context Related context.
     * @return a boolean whether operation is success or not.
     */
    public static boolean deleteAllInternalFiles(Context context) {
        for (File file : context.getFilesDir().listFiles()) {
            if (!file.delete()) return false;
        }
        return true;
    }

    /**
     * Delete a specific file within internal storage.
     * @param context Related context.
     * @param fileName File name.
     * @return a boolean whether operation is success or not.
     */
    public static boolean deleteInternalFile(Context context, String fileName) {
        for (File file : context.getFilesDir().listFiles()) {
            if (file.getName().equals(fileName)) {
                if (file.delete()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean deleteFile(String path) {
        final String TAG = Thread.currentThread().getStackTrace()[2].getMethodName();
        if (new File(path).delete()) {
            Log.i(TAG, "File '" + path + "' was deleted successfully.");
            return true;
        } else {
            Log.w(TAG, "File '" + path + "' was not deleted.");
            return false;
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