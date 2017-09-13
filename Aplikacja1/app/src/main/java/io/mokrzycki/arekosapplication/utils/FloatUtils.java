package io.mokrzycki.arekosapplication.utils;

/**
 * Created by michal on 10.09.2017.
 */

public class FloatUtils {

    public static float fixLength(float f, int i) {
        String fs = f + "";

        int index = fs.indexOf(".");
        if (index > 0 && !fs.contains("E")) {//no power, fix width
            fs = fs + "00";
            return Float.parseFloat(fs.substring(0, index + 3));
        } else {
            return f;
        }
    }
}
