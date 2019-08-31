package com.dev.viz.utils;

public class FrameSupport {

    private double frameRate;

    public static float[] stepsBetween(float a, float b, int frameRate) {
        float sub = b - a;
        float step = sub / frameRate;
        float[] values = new float[frameRate];

        for (int i = 0; i < frameRate; i++) {
            values[i] = a + step;
            a = a + step;
        }
        return values;
    }
}
