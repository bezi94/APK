package io.mokrzycki.arekosapplication.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import io.mokrzycki.arekosapplication.BuildConfig;
import io.mokrzycki.arekosapplication.databases.tables.Clients;
import io.mokrzycki.arekosapplication.databases.tables.Transactions;

public class AppSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String TAG = "SQLiteOpenHelper";

    public AppSQLiteOpenHelper(Context context) {
        super(context, BuildConfig.APPLICATION_ID + ".db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Transactions._CREATE_TABLE);
        db.execSQL(Clients._CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        int upgradeTo = oldVersion + 1;
        while (upgradeTo <= newVersion) {
            switch (upgradeTo) {
                case 2:
                    db.execSQL("ALTER TABLE " + Clients._NAME + " ADD COLUMN " + Clients.STREET + " TEXT");
                    break;
            }
            upgradeTo++;
        }
    }
}
