package com.gabrielmorenoibarra.g.java;

/**
 * Static methods to work with arrays.
 * Created by Gabriel Moreno on 2016-11-30.
 */
public class GArray {

    /**
     * Concatenate two byte arrays.
     * @param a First array.
     * @param b Second array.
     * @return a byte array result of concatenations of input arrays.
     */
    public static byte[] concatenate(byte[] a, byte[] b) {
        byte[] res = new byte[a.length + b.length];
        System.arraycopy(a, 0, res, 0, a.length);
        System.arraycopy(b, 0, res, a.length, b.length);
        return res;
    }

    /**
     * Concatenate two short arrays.
     * @param a First array.
     * @param b Second array.
     * @return a short array result of concatenations of input arrays.
     */
    public static short[] concatenate(short[] a, short[] b) {
        short[] res = new short[a.length + b.length];
        System.arraycopy(a, 0, res, 0, a.length);
        System.arraycopy(b, 0, res, a.length, b.length);
        return res;
    }

    /**
     * Concatenate two float arrays.
     * @param a First array.
     * @param b Second array.
     * @return a float array result of concatenations of input arrays.
     */
    public static float[] concatenate(float[] a, float[] b) {
        float[] res = new float[a.length + b.length];
        System.arraycopy(a, 0, res, 0, a.length);
        System.arraycopy(b, 0, res, a.length, b.length);
        return res;
    }

    /**
     * Concatenate two double arrays.
     * @param a First array.
     * @param b Second array.
     * @return a double array result of concatenations of input arrays.
     */
    public static double[] concatenate(double[] a, double[] b) {
        double[] res = new double[a.length + b.length];
        System.arraycopy(a, 0, res, 0, a.length);
        System.arraycopy(b, 0, res, a.length, b.length);
        return res;
    }
}