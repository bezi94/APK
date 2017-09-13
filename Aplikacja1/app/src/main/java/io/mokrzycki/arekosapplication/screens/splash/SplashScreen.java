package io.mokrzycki.arekosapplication.screens.splash;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import io.mokrzycki.arekosapplication.R;

/**
 * Created by michal on 11.09.2017.
 */

public class SplashScreen extends AppCompatActivity {

    private static final String TAG = "SplashScreen";
    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        setResult(Activity.RESULT_OK);
        handler = new Handler();
    }

    @Override
    protected void onResume() {
        super.onResume();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacksAndMessages(null);
    }
}