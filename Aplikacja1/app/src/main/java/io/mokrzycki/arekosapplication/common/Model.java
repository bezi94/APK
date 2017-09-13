package io.mokrzycki.arekosapplication.common;

import android.content.ContentValues;

import java.io.Serializable;

/**
 * Created by michal on 11.09.2017.
 */

public abstract class Model implements Serializable {

    public Model() {
    }

    public abstract ContentValues toContentValues();
}
