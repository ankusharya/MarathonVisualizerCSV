package com.dev.viz;

import com.dev.viz.utils.Common;
import com.dev.viz.utils.FrameSupport;
import processing.core.PApplet;
import processing.data.Table;
import processing.data.TableRow;

import java.util.ArrayList;

import static processing.core.PApplet.println;

public class BarManager {
    PApplet parent;
    private String source;
    private ArrayList<String> columns;

    private ArrayList<Bar> bars;

    public int datasize;

    public int getDatasize() {
        return datasize;
    }

    public BarManager(PApplet p, String source, ArrayList<String> columns) {
        this.source = source;
        this.columns = columns;
        this.parent = p;
        constructBarsIncremental(unwrapDatasource(this.source));
    }

    public Table unwrapDatasource(String source) {
        Table mar = parent.loadTable(source, "header");
        return mar;
    }

    public void constructBars(Table tab) {
        float seperateX = 10;
        float seperateY = 10;
        float width_ = 30;
        float DEFAULT_HEIGHT = 0;

        float[][] datas = new float[this.columns.size()][tab.getRowCount()];
        this.datasize = tab.getRowCount();
        bars = new ArrayList<Bar>(this.columns.size());
        for (int i = 0; i < tab.getRowCount(); i++) {
            TableRow row = tab.getRow(i);
            for (int col = 0; col < this.columns.size(); col++) {
                datas[col][i] = row.getInt(col);
            }
        }

        for (int col = 0; col < this.columns.size(); col++) {
            bars.add(new Bar(parent, col * (width_ + seperateX), seperateY, width_,
                    DEFAULT_HEIGHT, datas[col]));
        }
    }

    public void constructBarsIncremental(Table tab) {
        float seperateX = 10;
        float seperateY = 10;
        float width_ = 30;
        float DEFAULT_HEIGHT = 0;
        int frameRate = 128;

        float[][][] datas = new float[this.columns.size()][tab.getRowCount()][frameRate];
        this.datasize = tab.getRowCount();
        bars = new ArrayList<Bar>(this.columns.size());
        println(tab.getRowCount());
        for (int i = 0; i < tab.getRowCount() - 1; i++) {
            TableRow row = tab.getRow(i);
            TableRow rowNext = tab.getRow(i + 1);
            for (int col = 0; col < this.columns.size(); col++) {
                float first = row.getFloat(col);
                float second = rowNext.getFloat(col);
                float[] stepIncrease = FrameSupport.stepsBetween(first, second, frameRate);
                datas[col][i] = stepIncrease;
                println(stepIncrease);
            }
        }

        //finding the heighest number
        float[] largetNumbers = new float[this.columns.size()];
        for (int col = 0; col < this.columns.size(); col++) {
            println(datas[col][tab.getRowCount() - 2][frameRate - 1]);
            largetNumbers[col] = datas[col][tab.getRowCount() - 3][frameRate - 1];
        }

        float largestNumber = Common.greatestNumber(largetNumbers);

        // reset the numbers according to the window size
        for (int col = 0; col < this.columns.size(); col++) {
            for (int i = 0; i < tab.getRowCount(); i++) {
                for (int f = 0; f < frameRate; f++) {
                    datas[col][i][f] = (int) parent.map(datas[col][i][f], 0, largestNumber, 0, parent.height - 50);
                }
            }
        }

        for (int col = 0; col < this.columns.size(); col++) {
            bars.add(new Bar(parent, col * (width_ + seperateX) + seperateX, seperateY, width_,
                    DEFAULT_HEIGHT, datas[col]));
        }
    }

    public void unwrapBars() {
        int size = this.columns.size();
    }

    public void update(int index) {
        for (int col = 0; col < this.columns.size(); col++) {
            Bar curr = bars.get(col);
            curr.drawWithFrame(index, 32);
            // curr.nextData(index);

        }
        if (index >= this.datasize) {
            parent.noLoop();
        }
    }

}
