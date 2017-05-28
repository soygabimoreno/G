package com.gabrielmorenoibarra.g;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Static Media Utilities.
 * Created by Gabriel Moreno on 2017-05-28.
 */
public class GMedia {

    /**
     * Get duration from a specific <code>Uri</code>.
     * @param context Related context.
     * @param uri File <code>Uri</code> to find out its duration.
     * @return the duration in ms.
     */
    public static long getDurationByRetriever(Context context, Uri uri) {
        if (uri == null) return -1;
        String time;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(context, uri);
            time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        } catch (Exception e) {
            String fileName = getRealPathFromUri(context, uri);
            return fileName != null ? getDurationByMediaPlayer(fileName) : -1;
        }
        return time != null ? Long.parseLong(time) : -1;
    }

    /**
     * @param fileName Absolute path name of the audio or video resource.
     * @return the duration in milliseconds of an audio or video file.
     */
    public static long getDurationByRetriever(String fileName) {
        String time;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(fileName);
            time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        } catch (Exception e) {
            return getDurationByMediaPlayer(fileName);
        }
        return time != null ? Long.parseLong(time) : -1;
    }

    /**
     * @param fileName Absolute path name of the audio or video resource.
     * @return the duration in milliseconds of an audio or video file.
     */
    public static int getDurationByMediaPlayer(String fileName) {
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
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        int length = mp.getDuration();
        mp.release();
        return length;
    }

    public static String getRealPathFromUri(Context context, Uri uri) {
        Cursor cursor = null;
        try {
            String[] strings = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(uri, strings, null, null, null);
            int columnIndex;
            if (cursor != null && cursor.getCount() > 0) {
                columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                return cursor.getString(columnIndex);
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return null;
    }

    /**
     * Create and return a <code>MediaPlayer</code> for audio playback.
     * @param context Related context.
     * @param assetsFilePath Path to the media resource inside assets folder.
     * @param streamType the audio stream type.
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
}