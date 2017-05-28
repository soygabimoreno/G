package com.gabrielmorenoibarra.g;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class GFile {

    /**
     * Save a <code>String</code> in a text file within internal storage.
     * It does not need any permission.
     * @param context Related context.
     * @param fileName File name.
     * @param line Line to write within text file.
     * @param mode Write mode: Context.MODE_PRIVATE, Context.MODE_APPEND...
     */
    public static void save(Context context, String fileName, String line, int mode) {
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
}