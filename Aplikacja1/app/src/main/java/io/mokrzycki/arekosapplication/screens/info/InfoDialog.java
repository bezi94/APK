package io.mokrzycki.arekosapplication.screens.info;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by michal on 10.09.2017.
 */

public class InfoDialog extends AlertDialog {

    public InfoDialog(Context context) {
        super(context);
        setTitle("Info");
        setMessage("Aplikacja użytkowa, w fazie testów.");
        setButton(BUTTON_POSITIVE, "OK", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

    }
}
