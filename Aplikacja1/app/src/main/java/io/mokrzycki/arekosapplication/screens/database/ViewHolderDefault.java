package io.mokrzycki.arekosapplication.screens.database;

import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import io.mokrzycki.arekosapplication.R;
import io.mokrzycki.arekosapplication.databases.tables.Clients;
import io.mokrzycki.arekosapplication.databases.tables.Transactions;

/**
 * Created by michal on 10.09.2017.
 */
class ViewHolderDefault extends RecyclerView.ViewHolder {

    private final TextView textView;

    public ViewHolderDefault(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.database_activity_view_holder, parent, false));
        textView = ((TextView) itemView.findViewById(R.id.textView1));
    }

    public void bind(Cursor cursor) {
        textView.setText(buildStaticCard(cursor));
    }

    private SpannableStringBuilder buildStaticCard(Cursor cursor) {
        StringBuilder sb = new StringBuilder();

        sb.append("TRANSAKCJA").append("\n");

        sb.append("ID klienta: " + (getString(cursor, Transactions.NAME_SURNAME_CLIENT, "nie podano")));
        sb.append("Rodzaj usług: " + (getString(cursor, Transactions.TYPE_SERVICE, "nie podano")));
        sb.append("Kwota: " + (getString(cursor, Transactions.PRICE, "nie podano")));
        sb.append("Data transakcji: " + (getString(cursor, Transactions.TIMESTAMP, "nie podano")));
        sb.append("Miasto transakcji: " + (getString(cursor, Transactions.CITY, "nie podano")));
        sb.append("Opis transakcji: " + (getString(cursor, Transactions.DESCRIPTION, "nie podano")));

        sb.append("\n");//new line

        sb.append("KLIENT").append("\n");
        sb.append("Imię Nazwisko klienta: " + (getString(cursor, Clients.NAME_SURNAME, "nie podano")));
        sb.append("Ulica: " + (getString(cursor, Clients.STREET, "nie podano")));
        sb.append("Numer lokalu: " + (getString(cursor, Clients.NUMBER_APARTMENT, "nie podano")));
        sb.append("Numer budynku: " + (getString(cursor, Clients.NUMBER_BUILDING, "nie podano")));
        sb.append("Miasto klienta: " + (getString(cursor, Clients.CITY, "nie podano")));
        sb.append("Telefon klienta: " + (getString(cursor, Clients.NUMBER_PHONE, "nie podano")));
        sb.append("Email klienta: " + (getString(cursor, Clients.EMAIL, "nie podano")));

        SpannableStringBuilder ssb = new SpannableStringBuilder(sb.toString());
        styleSpan(ssb, sb.toString(), "TRANSAKCJA");
        styleSpan(ssb, sb.toString(), "KLIENT");

        return ssb;
    }

    private void styleSpan(SpannableStringBuilder ssb, String source, String text) {
        int i = source.indexOf(text);
        if (i != -1) {
            ssb.setSpan(new StyleSpan(Typeface.BOLD), i, i + text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    private String getString(Cursor cursor, String nameSurnameClient, String alternativeWhenError) {
        if (cursor.getColumnIndex(nameSurnameClient) != -1) {
            String v = cursor.getString(cursor.getColumnIndex(nameSurnameClient));
            return (TextUtils.isEmpty(v) ? alternativeWhenError : v) + "\n";
        } else {
            return alternativeWhenError + "\n";
        }
    }

    private String mapValues(String key, String value) {
        switch (key) {

            case Transactions.NAME_SURNAME_CLIENT:
                return "ID klienta: " + value + "\n";
            case Transactions.TYPE_SERVICE:
                return "Rodzaj usług: " + value + "\n";
            case Transactions.PRICE:
                return "Kwota: " + value + "\n";
            case Transactions.TIMESTAMP:
                return "Data transakcji: " + value + "\n";
            case Transactions.CITY:
                return "Miasto transakcji: " + value + "\n";
            case Transactions.DESCRIPTION:
                return "Opis transakcji: " + value + "\n";

            case Clients.NAME_SURNAME:
                return "Imię Nazwisko klienta: " + value + "\n";
            case Clients.STREET:
                return "Ulica: " + value + "\n";
            case Clients.NUMBER_APARTMENT:
                return "Numer lokalu: " + value + "\n";
            case Clients.NUMBER_BUILDING:
                return "Numer budynku: " + value + "\n";
            case Clients.CITY:
                return "Miasto klienta: " + value + "\n";
            case Clients.NUMBER_PHONE:
                return "Telefon klienta: " + value + "\n";
            case Clients.EMAIL:
                return "Email klienta: " + value + "\n";

            default:
                return "---" + "\n";
        }
    }
}
