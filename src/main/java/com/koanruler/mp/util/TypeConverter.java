package com.koanruler.mp.util;

/**
 * Created by hose on 2017/8/16.
 */
public class TypeConverter {
    public static int objectToInt(Object o){
        if(o != null)
            return Integer.parseInt(o.toString());
        return 0;
    }

    public static String objectToString(Object o){
        if(o != null)
            return o.toString();
        else return "";
    }
}
