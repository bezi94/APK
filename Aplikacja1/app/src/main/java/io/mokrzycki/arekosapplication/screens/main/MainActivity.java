package io.mokrzycki.arekosapplication.screens.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import io.mokrzycki.arekosapplication.R;
import io.mokrzycki.arekosapplication.screens.splash.SplashScreen;

public class MainActivity extends AppCompatActivity {

    private boolean splashShowed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            splashShowed = savedInstanceState.getBoolean("splashShowed");
        }

        setContentView(R.layout.main_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        if (!splashShowed) {
            startActivityForResult(new Intent(this, SplashScreen.class), 1002);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1002) {
            splashShowed = true;
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("splashShowed", splashShowed);
    }
}
