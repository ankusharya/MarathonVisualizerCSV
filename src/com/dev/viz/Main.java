package com.dev.viz;

import processing.core.PApplet;

import java.util.ArrayList;

public class Main extends PApplet {
    ArrayList<String> cols = new ArrayList(2);
    BarManager barMan = null;

    int initializeLoop = 0;

    public void settings() {
        size(700, 700);
    }

    public void setup() {
        cols.add("apple");
        cols.add("google");
        cols.add("microsoft");
        barMan = new BarManager(this, "data/marathon.csv", cols);
    }

    public void draw() {
        //clear();
        frameRate(32);
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
