package com.gabrielmorenoibarra.g.java;

/**
 * Static methods to resample arrays.
 * Created by Gabriel Moreno on 2017-06-27.
 */
public class GResampling {

    /**
     * Make a decimation to a byte array.
     * @param m Decimation factor.
     * @param x Array to make the decimation.
     * @return a byte array result of decimation of the input array.
     */
    public static byte[] decimate(int m, byte[] x) {
        byte[] y = new byte[x.length / m];
        int j = 0;
        for (int i = 0; i < y.length; i++) {
            y[i] = x[j];
            j = j + m;
        }
        return y;
    }

    /**
     * Make a decimation to a short array.
     * @param m Decimation factor.
     * @param x Array to make the decimation.
     * @return a short array result of decimation of the input array.
     */
    public static short[] decimate(int m, short[] x) {
        short[] y = new short[x.length / m];
        int j = 0;
        for (int i = 0; i < y.length; i++) {
            y[i] = x[j];
            j = j + m;
        }
        return y;
    }

    /**
     * Make a decimation to a float array.
     * @param m Decimation factor.
     * @param x Array to make the decimation.
     * @return a float array result of decimation of the input array.
     */
    public static float[] decimate(int m, float[] x) {
        float[] y = new float[x.length / m];
        int j = 0;
        for (int i = 0; i < y.length; i++) {
            y[i] = x[j];
            j = j + m;
        }
        return y;
    }

    /**
     * Make a decimation to a double array.
     * @param m Decimation factor.
     * @param x Array to make the decimation.
     * @return a double array result of decimation of the input array.
     */
    public static double[] decimate(int m, double[] x) {
        double[] y = new double[x.length / m];
        int j = 0;
        for (int i = 0; i < y.length; i++) {
            y[i] = x[j];
            j = j + m;
        }
        return y;
    }

    /**
     * Make a interpolation to a byte array.
     * @param l Interpolation factor.
     * @param x Array to make the interpolation.
     * @return a byte array result of interpolation of the input array.
     */
    public static byte[] interpolate(int l, byte[] x) {
        byte[] y = new byte[x.length * l];
        int j = 0;
        for (int i = 0; i < y.length; i = i + l) {
            y[i] = x[j];
            j++;
        }
        return y;
    }

    /**
     * Make a interpolation to a short array.
     * @param l Interpolation factor.
     * @param x Array to make the interpolation.
     * @return a short array result of interpolation of the input array.
     */
    public static short[] interpolate(int l, short[] x) {
        short[] y = new short[x.length * l];
        int j = 0;
        for (int i = 0; i < y.length; i = i + l) {
            y[i] = x[j];
            j++;
        }
        return y;
    }

    /**
     * Make a interpolation to a float array.
     * @param l Interpolation factor.
     * @param x Array to make the interpolation.
     * @return a float array result of interpolation of the input array.
     */
    public static float[] interpolate(int l, float[] x) {
        float[] y = new float[x.length * l];
        int j = 0;
        for (int i = 0; i < y.length; i = i + l) {
            y[i] = x[j];
            j++;
        }
        return y;
    }

    /**
     * Make a interpolation to a double array.
     * @param l Interpolation factor.
     * @param x Array to make the interpolation.
     * @return a double array result of interpolation of the input array.
     */
    public static double[] interpolate(int l, double[] x) {
        double[] y = new double[x.length * l];
        int j = 0;
        for (int i = 0; i < y.length; i = i + l) {
            y[i] = x[j];
            j++;
        }
        return y;
    }
}