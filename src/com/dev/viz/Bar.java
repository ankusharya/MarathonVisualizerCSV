package com.dev.viz;

import processing.core.PApplet;

public class Bar{
    public float x;
    public float y;
    public float width;
    public float height;
    public float[] data;
    public float[][] dataInc;
    PApplet parent;


    public Bar(PApplet p, float x, float y, float width, float height, float[] data){
        this.parent = p;

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.data = data;
    }

    public Bar(PApplet p, float x, float y, float width, float height, float[][] dataInc){
        this.parent = p;

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.dataInc = dataInc;
    }

    public void updateHeight(float h){
        this.height = h;
    }

    public void updateWidth(float w){
        this.width = w;
    }

    public void incrementalHeight(float dh){
        this.height = this.height + dh;
    }

    public void incrementalWidth(float dw){
        this.width = this.width + dw;
    }

    public void nextData(int index) {
        updateHeight(data[index]);
    }

    public void draw(){
        parent.rect(this.x, this.y, this.width, this.height);
    }

    public void drawWithFrame(int index, float frameRate){
        for(int i = 0; i < frameRate; i++){
            updateHeight(dataInc[index][i]);
            parent.rect(this.x, this.y, this.width, this.height);
        }

    }
}
