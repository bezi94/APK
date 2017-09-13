package io.mokrzycki.arekosapplication.databases;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;

import java.text.MessageFormat;
import java.util.ArrayList;

import io.mokrzycki.arekosapplication.BuildConfig;
import io.mokrzycki.arekosapplication.databases.tables.Clients;
import io.mokrzycki.arekosapplication.databases.tables.Transactions;

public class AppProvider extends ContentProvider {

    public static final String AUTHORITY = BuildConfig.APPLICATION_ID + ".db";
    public static final String CONTENT = "content://";
    @SuppressWarnings("unused")
    private static final String TAG = "AppProvider";
    private static final UriMatcher sUriMatcher;
    /**/
    private static final int TRANSACTION = 1;
    private static final int TRANSACTIONS = 2;
    private static final int CLIENT = 3;
    private static final int CLIENTS = 4;
    //
    private static final int CUSTOM_QUERY_JOIN_TRANSACTIONS_CLIENTS = 5;
    /**/
    private static final Object lockApplyBatch = new Object();
    private static final Object lockBulkInsert = new Object();
    /**/
    private static final String ILLEGAL_ARGUMENT_EXCEPTION_STRING = "uri not handled";

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(AUTHORITY, "transactions", TRANSACTIONS);
        sUriMatcher.addURI(AUTHORITY, "transactions/#", TRANSACTION);
        sUriMatcher.addURI(AUTHORITY, "clients", CLIENTS);
        sUriMatcher.addURI(AUTHORITY, "clients/#", CLIENT);
        //custom
        sUriMatcher.addURI(AUTHORITY, "custom/query/join/transactions/clients", CUSTOM_QUERY_JOIN_TRANSACTIONS_CLIENTS);

    }

    private SQLiteOpenHelper mSqliteOpenHelper;
    private boolean mInTransaction = false;

    public static Uri urlForTransactions() {
        return Uri.parse(MessageFormat.format("{0}{1}/transactions", CONTENT, AUTHORITY));
    }

    public static Uri urlForTransaction(long id) {
        return Uri.parse(MessageFormat.format("{0}{1}/transactions/{2}", CONTENT, AUTHORITY, String.valueOf(id)));
    }

    public static Uri urlForClients() {
        return Uri.parse(MessageFormat.format("{0}{1}/clients", CONTENT, AUTHORITY));
    }

    public static Uri urlForClient(long id) {
        return Uri.parse(MessageFormat.format("{0}{1}/clients/{2}", CONTENT, AUTHORITY, String.valueOf(id)));
    }

    public static Uri urlForCustomQueryJoinTransactionsClients() {
        return Uri.parse(MessageFormat.format("{0}{1}/custom/query/join/transactions/clients", CONTENT, AUTHORITY));
    }

    @Override
    public boolean onCreate() {
        mSqliteOpenHelper = new AppSQLiteOpenHelper(getContext());
        return true;
    }

    @Override
    public ContentProviderResult[] applyBatch(@NonNull ArrayList<ContentProviderOperation> operations) throws OperationApplicationException {
        synchronized (lockApplyBatch) {
            SQLiteDatabase db = mSqliteOpenHelper.getWritableDatabase();

            try {
                mInTransaction = true;
                db.beginTransaction();
                db.setTransactionSuccessful();
                return super.applyBatch(operations);
            } finally {
                mInTransaction = false;
                db.endTransaction();
            }
        }
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        synchronized (lockBulkInsert) {
            SQLiteDatabase db = mSqliteOpenHelper.getWritableDatabase();

            try {
                mInTransaction = true;
                db.beginTransaction();
                int result = super.bulkInsert(uri, values);
                db.setTransactionSuccessful();
                return result;
            } finally {
                mInTransaction = false;
                db.endTransaction();
                getContext().getContentResolver().notifyChange(uri, null);
            }
        }
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = mSqliteOpenHelper.getReadableDatabase();
        String table;
        String where = null;

        switch (sUriMatcher.match(uri)) {
            case TRANSACTIONS: {
                table = Transactions._NAME;
                break;
            }
            case TRANSACTION: {
                table = Transactions._NAME;
                where = Transactions._ID + " = " + uri.getLastPathSegment();
                break;
            }
            case CLIENTS: {
                table = Clients._NAME;
                break;
            }
            case CLIENT: {
                table = Clients._NAME;
                where = Clients._ID + " = " + uri.getLastPathSegment();
                break;
            }
            case CUSTOM_QUERY_JOIN_TRANSACTIONS_CLIENTS:
                String ccc = "c." + Clients.NAME_SURNAME;
                String ttt = "t." + Transactions.NAME_SURNAME_CLIENT;
                table = "transactions t JOIN clients c ON " + ttt + " = " + ccc;
                break;

            default: {
                throw new IllegalArgumentException(ILLEGAL_ARGUMENT_EXCEPTION_STRING + uri);
            }
        }

        if (selection != null) {
            if (where == null) {
                where = selection;
            } else {
                where = "(" + selection + ") AND (" + where + ")";
            }
        }

        Cursor query = db.query(table, // The database to query
                projection, // The columns to return from the query
                where, // The columns for the where clause
                selectionArgs, // The values for the where clause
                null, // don't group the rows
                null, // don't filter by row groups
                sortOrder // The sort order
        );

        query.setNotificationUri(getContext().getContentResolver(), uri);

        return query;

    }

    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        String table;

        switch (sUriMatcher.match(uri)) {
            case TRANSACTIONS: {
                table = Transactions._NAME;
                break;
            }
            case CLIENTS: {
                table = Clients._NAME;
                break;
            }

            default: {
                throw new IllegalArgumentException(ILLEGAL_ARGUMENT_EXCEPTION_STRING + uri);
            }
        }

        long rowId = mSqliteOpenHelper.getWritableDatabase().insertWithOnConflict(table, null, values, SQLiteDatabase.CONFLICT_IGNORE);

        if (!mInTransaction) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        if (rowId != -1) {
            return ContentUris.withAppendedId(uri, rowId);
        }

        throw new SQLException("Failed to insert row");
    }

    @SuppressWarnings({"PMD.AvoidDuplicateLiterals", "PMD.UseVarargs", "PMD.MissingBreakInSwitch"})
    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        String table;
        String where = null;

        switch (sUriMatcher.match(uri)) {
            case TRANSACTIONS: {
                table = Transactions._NAME;
                break;
            }
            case TRANSACTION: {
                table = Transactions._NAME;
                where = Transactions._ID + " = " + uri.getLastPathSegment();
                break;
            }
            case CLIENTS: {
                table = Clients._NAME;
                break;
            }
            case CLIENT: {
                table = Clients._NAME;
                where = Clients._ID + " = " + uri.getLastPathSegment();
                break;
            }

            default: {
                throw new IllegalArgumentException(ILLEGAL_ARGUMENT_EXCEPTION_STRING + uri);
            }
        }

        if (selection != null) {
            if (where == null) {
                where = "(" + selection + ")";
            } else {
                where = "(" + selection + ") AND (" + where + ")";
            }
        }

        int result = mSqliteOpenHelper.getWritableDatabase().delete(table, where, selectionArgs);

        if (!mInTransaction) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return result;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        String table;
        String where = null;

        switch (sUriMatcher.match(uri)) {
            case TRANSACTIONS: {
                table = Transactions._NAME;
                break;
            }
            case TRANSACTION: {
                table = Transactions._NAME;
                where = Transactions._ID + " = " + uri.getLastPathSegment();
                break;
            }
            case CLIENTS: {
                table = Clients._NAME;
                break;
            }
            case CLIENT: {
                table = Clients._NAME;
                where = Clients._ID + " = " + uri.getLastPathSegment();
                break;
            }

            default: {
                throw new IllegalArgumentException(ILLEGAL_ARGUMENT_EXCEPTION_STRING + uri);
            }
        }

        if (selection != null) {
            if (where == null) {
                where = "(" + selection + ")";
            } else {
                where = "(" + selection + ") AND (" + where + ")";
            }
        }

        int result = mSqliteOpenHelper.getWritableDatabase().update(table, values, where, selectionArgs);

        if (!mInTransaction) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return result;
    }


}
