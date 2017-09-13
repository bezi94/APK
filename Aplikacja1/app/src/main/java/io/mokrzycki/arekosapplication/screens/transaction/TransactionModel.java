package io.mokrzycki.arekosapplication.screens.transaction;

import android.content.ContentValues;

import io.mokrzycki.arekosapplication.common.Model;
import io.mokrzycki.arekosapplication.databases.tables.Transactions;
import io.mokrzycki.arekosapplication.utils.ContentValuesUtils;

/**
 * Created by michal on 11.09.2017.
 */

public class TransactionModel extends Model {

    public String NAME_SURNAME_CLIENT;
    public String TYPE_SERVICE;
    public String PRICE;
    public String TIMESTAMP;
    public String CITY;
    public String DESCRIPTION;

    public TransactionModel() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public TransactionModel(String NAME_SURNAME_CLIENT, String TYPE_SERVICE, String PRICE, String TIMESTAMP, String CITY, String DESCRIPTION) {
        this.NAME_SURNAME_CLIENT = NAME_SURNAME_CLIENT;
        this.TYPE_SERVICE = TYPE_SERVICE;
        this.PRICE = PRICE;
        this.TIMESTAMP = TIMESTAMP;
        this.CITY = CITY;
        this.DESCRIPTION = DESCRIPTION;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        ContentValuesUtils.safePut(cv, Transactions.NAME_SURNAME_CLIENT, NAME_SURNAME_CLIENT);
        ContentValuesUtils.safePut(cv, Transactions.TYPE_SERVICE, TYPE_SERVICE);
        ContentValuesUtils.safePut(cv, Transactions.PRICE, PRICE);
        ContentValuesUtils.safePut(cv, Transactions.TIMESTAMP, TIMESTAMP);
        ContentValuesUtils.safePut(cv, Transactions.CITY, CITY);
        ContentValuesUtils.safePut(cv, Transactions.DESCRIPTION, DESCRIPTION);
        return cv;
    }
}
