package com.dev.viz.utils;

public class FrameSupport {

    private double frameRate;

    public static int[] stepsBetween(int a, int b, int frameRate) {
        int sub = b - a;
        int step = sub / frameRate;
        int[] values = new int[frameRate];

        for (int i = 0; i < frameRate; i++) {
            values[i] = a + step;
            a = a + step;
        }
        return values;
    }
}
