package com.gabrielmorenoibarra.g.java;

import android.util.Base64;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

/**
 * Obtain last modified date (in ms) of a remote file.
 * Created by Gabriel Moreno on 2016-11-28.
 */
public class GLastModified {

    private final Thread thread;
    private String user;
    private String password;
    private boolean isCompleted;
    private long lastModified;

    /**
     * Constructor.
     * @param urlName Address of the resource remote file to get its last modified date.
     */
    public GLastModified(final String urlName) {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                URL url;
                URLConnection conn = null;
                try {
                    url = new URL(urlName);
                    conn = url.openConnection();
                    if (user != null && password != null) {
                        conn.setRequestProperty("Authorization", "Basic " + Base64.encodeToString((user + ":" + password).getBytes(), Base64.NO_WRAP));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (conn != null) lastModified = conn.getLastModified();

                System.out.println("Runnable done.");
                isCompleted = true; // We change the boolean that is read inside the synchronized thread condition
                synchronized (thread) {
                    thread.notify(); // And wakes up it to exit the loop
                }
            }
        });
    }

    /**
     * Constructor.
     * @param urlName Address of the resource remote file to get its last modified date.
     * @param user User authorization if needed to access to the host. It makes a pair with password.
     * @param password Password authorization if needed to the host. It makes a pair with user.
     */
    public GLastModified(final String urlName, String user, String password) {
        this(urlName);
        this.user = user;
        this.password = password;
    }

    /**
     * Obtain the number of ms since 1970 of last modification of the file requested.
     * This method is blocking.
     * @return a long with the date.
     */
    public long getMillis() {
        thread.start();
        synchronized (thread) {
            long timeBefore = new Date().getTime(); // Getting current time
            final int TIME_OUT = 3000;
            while ((new Date().getTime() < timeBefore + TIME_OUT) && !isCompleted) { // While has not passed the time out of the thread and this one has not been completed
                try {
                    System.out.println("Waiting...");
                    thread.wait(TIME_OUT); // We wait
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Thread finished!");
        return lastModified;
    }
}