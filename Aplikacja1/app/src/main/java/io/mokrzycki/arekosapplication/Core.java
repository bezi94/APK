package io.mokrzycki.arekosapplication;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by michal on 10.09.2017.
 */

public class Core extends Application {

    private static Core coreInstance;//in future case

    @Override
    public void onCreate() {
        super.onCreate();
        coreInstance = this;

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        FirebaseDatabase.getInstance().getReference().child("Transactions").keepSynced(true);
        FirebaseDatabase.getInstance().getReference().child("Clients").keepSynced(true);

        Stetho.initializeWithDefaults(this);
    }
}
