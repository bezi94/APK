package io.mokrzycki.arekosapplication.databases.tables;

/**
 * Created by mimok on 2017-01-26.
 */
public class Transactions {
    public static final String _NAME = "Transactions";
    public static final String _ID = "_id";
    /***/
    public static final String NAME_SURNAME_CLIENT = _NAME + "_" + "NAME_SURNAME_CLIENT";
    public static final String TYPE_SERVICE = _NAME + "_" + "TYPE_SERVICE";
    public static final String PRICE = _NAME + "_" + "PRICE";
    public static final String TIMESTAMP = _NAME + "_" + "TIMESTAMP";
    public static final String CITY = _NAME + "_" + "CITY";
    public static final String DESCRIPTION = _NAME + "_" + "DESCRIPTION";

    /*CREATE*/
    public static final String _CREATE_TABLE = "" +
            "CREATE TABLE " + _NAME +
            " ( " + _ID + " integer primary key autoincrement" +
            ", " + NAME_SURNAME_CLIENT + " TEXT " +
            ", " + TYPE_SERVICE + " TEXT " +
            ", " + PRICE + " INTEGER " +
            ", " + TIMESTAMP + " INTEGER " +
            ", " + CITY + " TEXT " +
            ", " + DESCRIPTION + " TEXT " +
            " ); ";

    @SuppressWarnings("unused")
    public static final String _DROP_TABLE = "DROP TABLE IF EXISTS " + _NAME;
}
