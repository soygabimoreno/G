package com.gabrielmorenoibarra.g;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.List;

/**
 * Camera preview management.
 * Created by Gabriel Moreno on 2017-05-28.
 */
public class GCameraPreview extends SurfaceView implements SurfaceHolder.Callback {

    public static final String TAG = GCameraPreview.class.getSimpleName();

    private SurfaceHolder holder;
    private Camera camera;
    private Camera.Size size;
    private boolean cameraFront;
    private boolean opened;
    private int orientationHint;

    public GCameraPreview(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, Thread.currentThread().getStackTrace()[2].getMethodName() + " " + hashCode());
        try {
            if (camera != null) {
                camera.setPreviewDisplay(holder);
                camera.startPreview();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG, Thread.currentThread().getStackTrace()[2].getMethodName() + " " + hashCode());
        refreshCamera();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, Thread.currentThread().getStackTrace()[2].getMethodName() + " " + hashCode());
        // Camera has been released yet within onPause() within the Activity
    }

    public void refreshCamera() {
        if (holder.getSurface() == null) {
            return;
        }
        try {
            camera.stopPreview(); // Stop preview before making changes
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Lock the camera object for later use.
     */
    public void lock() {
        try {
            camera.lock();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean unlock() {
        try { // This is due to unlock can make a crash
            camera.unlock();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Switch camera, from the front and the back and vice versa.
     */
    public void switchCamera(int cameraFacing) {
        Log.d(TAG, Thread.currentThread().getStackTrace()[2].getMethodName() + " " + hashCode());
        try {
            releaseCamera();
            List<Camera.Size> sizes = null;
            int cameraId = findCamera(cameraFacing);
            if (cameraId >= 0) {
                camera = Camera.open(cameraId);
                Camera.Parameters parameters = camera.getParameters();
                List<Camera.Size> supportedVideoSizes = parameters.getSupportedVideoSizes();
                if (supportedVideoSizes != null) {
                    size = supportedVideoSizes.get(0);
                    sizes = supportedVideoSizes;
                } else {
                    List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
                    if (supportedPreviewSizes != null) {
                        size = supportedPreviewSizes.get(0);
                        sizes = supportedPreviewSizes;
                    }
                }

                if (cameraFacing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    orientationHint = 90;
                    camera.setDisplayOrientation(90); // Use vertical video
                } else if (cameraFacing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    Camera.CameraInfo info = new Camera.CameraInfo();
                    Camera.getCameraInfo(cameraId, info);

                    int degrees = ((Activity) getContext()).getWindowManager().getDefaultDisplay().getRotation();
                    int result;
                    if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                        result = (info.orientation + degrees) % 360;
                        result = (360 - result) % 360;  // compensate the mirror
                    } else {  // back-facing
                        result = (info.orientation - degrees + 360) % 360;
                    }
                    Log.i(TAG, "degrees: " + degrees + ", result: " + result + ", info.orientation: " + info.orientation);

                    orientationHint = info.orientation;
                    camera.setDisplayOrientation(result);

                    parameters.setPreviewSize(size.width, size.height);
                    try { // To avoid crashes
                        camera.setParameters(parameters);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                opened = true;
            }
            if (sizes != null) {
                for (int i = 0; i < sizes.size(); i++) {
                    if (sizes.get(i).width > size.width) { // Get the maximum resolution
                        size = sizes.get(i);
                    }
                }
            }
            refreshCamera();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int findCamera(int cameraFacing) {
        Log.d(TAG, Thread.currentThread().getStackTrace()[2].getMethodName() + " " + hashCode());
        int cameraId = -1;
        for (int i = 0; i < Camera.getNumberOfCameras(); i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (cameraFacing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    cameraId = i;
                    cameraFront = true;
                    break;
                }
            } else if (cameraFacing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    cameraId = i;
                    cameraFront = false;
                    break;
                }
            }
        }
        return cameraId;
    }

    public void releaseCamera() {
        if (camera != null) {
            camera.release();
            camera = null;
            opened = false;
        }
    }

    public boolean isCameraFront() {
        return cameraFront;
    }

    public Camera.Parameters getParameters() {
        try {
            return camera != null ? camera.getParameters() : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setParameters(Camera.Parameters parameters) {
        camera.setParameters(parameters);
    }

    public Camera getCamera() {
        return camera;
    }

    public Camera.Size getSize() {
        return size;
    }

    public boolean isOpened() {
        return opened;
    }

    public int getOrientationHint() {
        return orientationHint;
    }
}