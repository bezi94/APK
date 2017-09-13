package io.mokrzycki.arekosapplication.utils;

import android.content.ContentValues;

/**
 * Created by michal on 12.09.2017.
 */

public class ContentValuesUtils {
    public static void safePut(ContentValues cv, String key, String value) {
        if (value == null) {
            cv.putNull(key);
        } else {
            cv.put(key, value);
        }
    }
}
