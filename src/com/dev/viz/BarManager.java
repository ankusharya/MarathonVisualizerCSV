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
        int seperateX = 10;
        int seperateY = 10;
        int width_ = 30;
        int DEFAULT_HEIGHT = 0;

        int[][] datas = new int[this.columns.size()][tab.getRowCount()];
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
        int seperateX = 10;
        int seperateY = 10;
        int width_ = 30;
        int DEFAULT_HEIGHT = 0;
        int frameRate = 32;

        int[][][] datas = new int[this.columns.size()][tab.getRowCount()][frameRate];
        this.datasize = tab.getRowCount();
        bars = new ArrayList<Bar>(this.columns.size());
        for (int i = 0; i < tab.getRowCount() - 1; i++) {
            TableRow row = tab.getRow(i);
            TableRow rowNext = tab.getRow(i + 1);
            for (int col = 0; col < this.columns.size(); col++) {
                int first = row.getInt(col);
                int second = rowNext.getInt(col);
                int[] stepIncrease = FrameSupport.stepsBetween(first, second, frameRate);
                datas[col][i] = stepIncrease;
            }
        }

        //finding the heighest number
        int[] largetNumbers = new int[this.columns.size()];
        for (int col = 0; col < this.columns.size(); col++) {
            println(datas[col][tab.getRowCount() - 1][0]);
            largetNumbers[col] = datas[col][tab.getRowCount() - 3][frameRate - 1];
        }

        int largestNumber = Common.greatestNumber(largetNumbers);
        println(largetNumbers);

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
