package io.mokrzycki.arekosapplication.databases.tables;

/**
 * Created by mimok on 2017-01-26.
 */
public class Clients {
    public static final String _NAME = "Clients";
    public static final String _ID = "_id";
    /***/
    public static final String NAME_SURNAME = _NAME + "_" + "NAME";
    public static final String NUMBER_APARTMENT = _NAME + "_" + "NUMBER_APARTMENT";
    public static final String NUMBER_BUILDING = _NAME + "_" + "NUMBER_BUILDING";
    public static final String CITY = _NAME + "_" + "CITY";
    public static final String STREET = _NAME + "_" + "STREET";
    public static final String NUMBER_PHONE = _NAME + "_" + "NUMBER_PHONE";
    public static final String EMAIL = _NAME + "_" + "EMAIL";

    /*CREATE*/
    public static final String _CREATE_TABLE = "" +
            "CREATE TABLE " + _NAME +
            " ( " + _ID + " integer primary key autoincrement" +
            ", " + NAME_SURNAME + " TEXT " +
            ", " + NUMBER_APARTMENT + " INTEGER " +
            ", " + NUMBER_BUILDING + " INTEGER " +
            ", " + STREET + " INTEGER " +
            ", " + CITY + " TEXT " +
            ", " + NUMBER_PHONE + " INTEGER " +
            ", " + EMAIL + " TEXT " +
            " ); ";

    @SuppressWarnings("unused")
    public static final String _DROP_TABLE = "DROP TABLE IF EXISTS " + _NAME;
}
