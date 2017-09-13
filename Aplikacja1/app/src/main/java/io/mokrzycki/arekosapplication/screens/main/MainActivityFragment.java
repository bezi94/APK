package io.mokrzycki.arekosapplication.screens.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.mokrzycki.arekosapplication.R;
import io.mokrzycki.arekosapplication.screens.calc.CalcActivity;
import io.mokrzycki.arekosapplication.screens.client.AddClientActivity;
import io.mokrzycki.arekosapplication.screens.database.DatabaseActivity;
import io.mokrzycki.arekosapplication.screens.info.InfoDialog;
import io.mokrzycki.arekosapplication.screens.transaction.AddTransactionActivity;

public class MainActivityFragment extends Fragment implements View.OnClickListener {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.button1).setOnClickListener(this);
        view.findViewById(R.id.button2).setOnClickListener(this);
        view.findViewById(R.id.button3).setOnClickListener(this);
        view.findViewById(R.id.button4).setOnClickListener(this);
        view.findViewById(R.id.button5).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                startActivity(new Intent(v.getContext(), DatabaseActivity.class));
                break;
            case R.id.button2:
                startActivity(new Intent(v.getContext(), CalcActivity.class));
                break;
            case R.id.button3:
                startActivity(new Intent(v.getContext(), AddTransactionActivity.class));
                break;
            case R.id.button4:
                startActivity(new Intent(v.getContext(), AddClientActivity.class));
                break;
            case R.id.button5:
                new InfoDialog(v.getContext()).show();
                break;
        }
    }
}
