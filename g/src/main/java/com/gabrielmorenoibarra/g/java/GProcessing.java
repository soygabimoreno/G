package com.gabrielmorenoibarra.g.java;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Audio Processing Tools.
 * Created by Gabriel Moreno on 2016-11-27.
 */
public class GProcessing {

    public static float linearTodB(float linearValue, int range) {
        return (float) (20 * Math.log10(linearValue / range));
    }

    public static float linearTodB(float linearValue) {
        return (float) (20 * Math.log10(linearValue));
    }

    public static float dBToLinear(float dBValue, int range) {
        return (float) (range * Math.pow(10, dBValue / 20));
    }

    public static float dBToLinear(float dBValue) {
        return (float) (Math.pow(10, dBValue / 20));
    }

    public static short amplify(short x, float scale) {
        return scale > 1 ? (short) ((float) x * scale) : x;
    }

    public static short limiter(short x, float maxAmplitude, float gain) {
        short y;
        x = (short) ((float) x * gain);
        if (x > 0) {
            y = (short) Math.min(-x, maxAmplitude);
        } else {
            y = (short) Math.max(x, -maxAmplitude);
        }
        return y;
    }

    public static short noiseGate(short x, float minAmplitude, float gain) {
        short y;
        if (x > 0 && x < minAmplitude) {
            y = (short) ((float) x * gain);
        } else if (x < 0 && x > -minAmplitude) {
            y = (short) ((float) x * gain);
        } else {
            return x;
        }
        return y;
    }

    public static float calculteRms(byte[] data, int range) {
        float sum = 0;
        for (int i = 0; i < data.length - 1; i = i + 2) {
            ByteBuffer bb = ByteBuffer.allocate(2);
            bb.order(ByteOrder.LITTLE_ENDIAN);
            bb.put(data[i]);
            bb.put(data[i + 1]);
            short amplitude = bb.getShort(0);
            sum += Math.pow((float) amplitude / range, 2);
        }
        float rms = (float) Math.sqrt(sum / data.length); // Linear
        return Math.round(20 * Math.log10(rms)); // dB
    }

    public static byte[] fade(byte[] data, int mode, int nBuffers, int buffersCount) {
        final int FADE_IN = 0;
        final int FADE_OUT = 1;
        int bufferSize = data.length;
        byte[] out = new byte[bufferSize];
        for (int i = 0; i < bufferSize - 1; i = i + 2) {
            ByteBuffer bb = ByteBuffer.allocate(2);
            bb.order(ByteOrder.LITTLE_ENDIAN);
            bb.put(data[i]);
            bb.put(data[i + 1]);
            short amplitude = bb.getShort(0);
            if (mode == FADE_IN) {
                amplitude = (short) (amplitude * (buffersCount + (float) i / bufferSize) / nBuffers);
            } else if (mode == FADE_OUT) {
                amplitude = (short) (amplitude * (1 - ((float) (i + buffersCount * bufferSize) / (nBuffers * bufferSize))));
            }
            out[i] = (byte) amplitude;
            out[i + 1] = (byte) (amplitude >> 8);
        }
        return out;
    }
}