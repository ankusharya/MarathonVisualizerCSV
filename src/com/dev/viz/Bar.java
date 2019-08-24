package com.dev.viz;

import processing.core.PApplet;

import static processing.core.PApplet.println;

public class Bar{
    public int x;
    public int y;
    public int width;
    public int height;
    public int[] data;
    public int[][] dataInc;
    PApplet parent;


    public Bar(PApplet p, int x, int y, int width, int height, int[] data){
        this.parent = p;

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.data = data;
    }

    public Bar(PApplet p, int x, int y, int width, int height, int[][] dataInc){
        this.parent = p;

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.dataInc = dataInc;
    }

    public void updateHeight(int h){
        this.height = h;
    }

    public void updateWidth(int w){
        this.width = w;
    }

    public void incrementalHeight(int dh){
        this.height = this.height + dh;
    }

    public void incrementalWidth(int dw){
        this.width = this.width + dw;
    }

    public void nextData(int index) {
        updateHeight(data[index]);
    }

    public void draw(){
        parent.rect(this.x, this.y, this.width, this.height);
    }

    public void drawWithFrame(int index, int frameRate){
        for(int i = 0; i < frameRate; i++){
            updateHeight(dataInc[index][i]);
            parent.rect(this.x, this.y, this.width, this.height);
        }

    }
}
