package io.mokrzycki.arekosapplication.screens.client;

import android.content.ContentValues;
import android.text.TextUtils;

import io.mokrzycki.arekosapplication.common.Model;
import io.mokrzycki.arekosapplication.databases.tables.Clients;
import io.mokrzycki.arekosapplication.utils.ContentValuesUtils;

/**
 * Created by michal on 11.09.2017.
 */

public class ClientModel extends Model {

    public String NAME_SURNAME;
    public String NUMBER_APARTMENT;
    public String NUMBER_BUILDING;
    public String CITY;
    public String STREET;
    public String NUMBER_PHONE;
    public String EMAIL;

    public ClientModel() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public ClientModel(String NAME_SURNAME, String NUMBER_APARTMENT, String NUMBER_BUILDING, String CITY, String STREET, String NUMBER_PHONE, String EMAIL) {
        this.NAME_SURNAME = NAME_SURNAME;
        this.NUMBER_APARTMENT = NUMBER_APARTMENT;
        this.NUMBER_BUILDING = NUMBER_BUILDING;
        this.CITY = CITY;
        this.NUMBER_PHONE = NUMBER_PHONE;
        this.EMAIL = EMAIL;
        this.STREET = STREET;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        ContentValuesUtils.safePut(cv, Clients.NAME_SURNAME, NAME_SURNAME);
        ContentValuesUtils.safePut(cv, Clients.NUMBER_APARTMENT, NUMBER_APARTMENT);
        ContentValuesUtils.safePut(cv, Clients.NUMBER_BUILDING, NUMBER_BUILDING);
        ContentValuesUtils.safePut(cv, Clients.STREET, STREET);
        ContentValuesUtils.safePut(cv, Clients.CITY, CITY);
        ContentValuesUtils.safePut(cv, Clients.NUMBER_PHONE, NUMBER_PHONE);
        ContentValuesUtils.safePut(cv, Clients.EMAIL, EMAIL);
        return cv;
    }
}
