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
 * Require permission: CAMERA.
 * Created by Gabriel Moreno on 2017-05-28.
 */
public class GCameraPreview extends SurfaceView implements SurfaceHolder.Callback {

    public static final String TAG = GCameraPreview.class.getSimpleName();

    private SurfaceHolder holder;
    private Camera camera;
    private Camera.Size previewSize;
    private Camera.Size videoSize;
    private boolean cameraFront;
    private boolean opened;
    private int orientationHint;
    private int cameraId = -1;

    private Listener listener;

    public interface Listener {
        void onCameraOpened(int cameraId);
    }

    public GCameraPreview(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        GLog.d(TAG, Thread.currentThread().getStackTrace()[2].getMethodName() + " " + hashCode());
        try {
            if (camera != null) {
                camera.setPreviewDisplay(holder);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        GLog.d(TAG, Thread.currentThread().getStackTrace()[2].getMethodName() + " " + hashCode());
        refreshCamera();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        GLog.d(TAG, Thread.currentThread().getStackTrace()[2].getMethodName() + " " + hashCode());
        // Camera has been released yet within onPause() within the Activity
    }

    public void refreshCamera() {
        if (holder.getSurface() == null) {
            return;
        }
        try {
//            ensureCamera(); // ERASE
            camera.stopPreview(); // Stop preview before making changes
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            camera.setPreviewDisplay(holder);
            Camera.Parameters parameters = camera.getParameters();
            previewSize = getMaximumPreviewSize();
            videoSize = getMaximumVideoSize();

            parameters.setPreviewSize(previewSize.width, previewSize.height);
            camera.setParameters(parameters);
            camera.startPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Lock the camera object for later use.
     */
    public void lock() {
        ensureCamera();
        try {
            camera.lock();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean unlock() {
        ensureCamera();
        try { // This is due to unlock can make a crash
            camera.unlock();
            return true;
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return false;
    }

    private void ensureCamera() {
        if (camera == null) {
            if (cameraFront) {
                switchCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);
            } else {
                switchCamera(Camera.CameraInfo.CAMERA_FACING_BACK);
            }
        }
    }

    /**
     * Switch camera, from the front and the back and vice versa.
     * @param cameraFacing Indicate if it is front camera: CAMERA_FACING_FRONT or rear camera: CAMERA_FACING_BACK.
     */
    public void switchCamera(int cameraFacing) {
        GLog.d(TAG, Thread.currentThread().getStackTrace()[2].getMethodName() + " " + hashCode());
        try {
            releaseCamera();
            findCamera(cameraFacing);
            if (cameraId >= 0) {
                camera = Camera.open(cameraId);
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
                    GLog.i(TAG, "degrees: " + degrees + ", result: " + result + ", info.orientation: " + info.orientation);

                    orientationHint = info.orientation;
                    camera.setDisplayOrientation(result);
                }
                opened = true;
            }
            refreshCamera();
            if (listener != null) {
                listener.onCameraOpened(cameraId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int findCamera(int cameraFacing) {
        GLog.d(TAG, Thread.currentThread().getStackTrace()[2].getMethodName() + " " + hashCode());
        cameraId = -1;
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

    private Camera.Size getMaximumPreviewSize() {
        return getMaximumSize(camera.getParameters().getSupportedPreviewSizes());
    }

    private Camera.Size getMaximumVideoSize() {
        return getMaximumSize(camera.getParameters().getSupportedVideoSizes());
    }

    private Camera.Size getMaximumSize(List<Camera.Size> sizes) {
        Camera.Size size = sizes.get(0);
        for (int i = 0; i < sizes.size(); i++) {
            if (sizes.get(i).width > size.width) { // Get the maximum resolution
                size = sizes.get(i);
            }
        }
        return size;
    }

    public Camera.Size getPreviewSize() {
        return previewSize;
    }

    public Camera.Size getVideoSize() {
        return videoSize != null ? videoSize : previewSize;
    }

    public boolean isOpened() {
        return opened;
    }

    public int getOrientationHint() {
        return orientationHint;
    }

    public int getCameraId() {
        return cameraId;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}