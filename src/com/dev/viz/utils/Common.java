package com.dev.viz.utils;

public class Common {
    public static float greatestNumber(float[] datas){
        float greatestNumber = 0;
        for(int i = 0; i < datas.length; i++){
            greatestNumber = datas[i] > greatestNumber ? datas[i] : greatestNumber ;
        }
        return greatestNumber;
    }
}
