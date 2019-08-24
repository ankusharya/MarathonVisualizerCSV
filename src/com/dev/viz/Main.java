package com.dev.viz;

import processing.core.PApplet;

import java.util.ArrayList;

public class Main extends PApplet {
    ArrayList<String> cols = new ArrayList(2);
    BarManager barMan = null;

    int initializeLoop = 0;
    int[] data = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 100 + 10, 100 + 20, 100 + 30, 100 + 40, 100 + 50, 100 + 60, 100 + 70, 100 + 80, 100 + 90, 100 + 100};

    public void settings() {
        size(700, 700);
    }

    public void setup() {
        cols.add("apple");
        cols.add("google");
        barMan = new BarManager(this, "data/marathon.csv", cols);
    }

    public void draw() {
        frameRate(48);
        barMan.update(initializeLoop);
        println("Its printing"+ initializeLoop);
        initializeLoop++;
        if (initializeLoop >= barMan.getDatasize()) {
            noLoop();
        }

    }

    public static void main(String[] args) {
        PApplet.main("com.dev.viz.Main");
    }
}
