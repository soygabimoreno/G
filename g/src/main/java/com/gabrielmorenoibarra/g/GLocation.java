package com.gabrielmorenoibarra.g;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

/**
 * Location management.
 * Require permission: ACCESS_COARSE_LOCATION or ACCESS_FINE_LOCATION.
 * Created by Gabriel Moreno on 2017-05-28.
 */
public class GLocation implements LocationListener {

    public static final String TAG = GLocation.class.getSimpleName();
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES_IN_METERS = 10;
    private static final long MIN_TIME_BW_UPDATES_IN_MS = 1000;

    private Context context;
    private LocationManager locationManager;
    private Location location;

    public GLocation(Context context) {
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i(TAG, Thread.currentThread().getStackTrace()[2].getMethodName() + " " + hashCode()
                + " latitude: " + location.getLatitude() + ", longitude: " + location.getLongitude());
        this.location = location;
        stopLocationUpdates();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.i(TAG, Thread.currentThread().getStackTrace()[2].getMethodName() + " " + hashCode() + " provider: " + provider);
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.i(TAG, Thread.currentThread().getStackTrace()[2].getMethodName() + " " + hashCode() + " provider: " + provider);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.i(TAG, Thread.currentThread().getStackTrace()[2].getMethodName() + " " + hashCode() + " provider: " + provider + ", extras: " + extras.toString());
    }

    public void stopLocationUpdates() {
        Log.i(TAG, Thread.currentThread().getStackTrace()[2].getMethodName() + " " + hashCode());
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            locationManager.removeUpdates(this);
        }
    }

    /**
     * @return the location with the available provider.
     */
    public Location getAvailableLocation() {
        Log.i(TAG, Thread.currentThread().getStackTrace()[2].getMethodName() + " " + hashCode());
        Location passiveLocation = getLocation(LocationManager.PASSIVE_PROVIDER);
        if (passiveLocation != null) {
            return passiveLocation;
        } else {
            Location netWorkLocation = getLocation(LocationManager.NETWORK_PROVIDER);
            if (netWorkLocation != null) {
                return netWorkLocation;
            } else {
                return getLocation(LocationManager.GPS_PROVIDER);
            }
        }
    }

    private Location getLocation(String provider) {
        if (locationManager.isProviderEnabled(provider)) {
            Log.i(TAG, "Locating by " + provider + "...");
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return null;
            }
            locationManager.requestLocationUpdates(
                    provider,
                    MIN_TIME_BW_UPDATES_IN_MS,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES_IN_METERS, this);
            location = locationManager.getLastKnownLocation(provider);
            if (location != null) {
                Log.i(TAG, "Located by " + provider + "! latitude: " + location.getLatitude() + ", longitude: " + location.getLongitude());
            }
        }
        return location;
    }
}