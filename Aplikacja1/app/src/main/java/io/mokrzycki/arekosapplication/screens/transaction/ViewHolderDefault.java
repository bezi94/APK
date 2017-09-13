package io.mokrzycki.arekosapplication.screens.transaction;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import io.mokrzycki.arekosapplication.R;
import io.mokrzycki.arekosapplication.utils.ConnectionUtils;

/**
 * Created by michal on 10.09.2017.
 */
class ViewHolderDefault extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final TextView textView1;
    private final TextView textView2;
    private final TextView textView3;
    private final TextView textView4;
    private final TextView textView5;
    private final TextView textView6;
    private final TextView editText1;
    private final TextView editText2;
    private final TextView editText3;
    private final TextView editText4;
    private final TextView editText5;
    private final TextView editText6;
    private final TextView button1;

    public ViewHolderDefault(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_activity_view_holder, parent, false));

        textView1 = ((TextView) itemView.findViewById(R.id.textView1));
        textView2 = ((TextView) itemView.findViewById(R.id.textView2));
        textView3 = ((TextView) itemView.findViewById(R.id.textView3));
        textView4 = ((TextView) itemView.findViewById(R.id.textView4));
        textView5 = ((TextView) itemView.findViewById(R.id.textView5));
        textView6 = ((TextView) itemView.findViewById(R.id.textView6));

        editText1 = ((TextView) itemView.findViewById(R.id.editText1));
        editText2 = ((TextView) itemView.findViewById(R.id.editText2));
        editText3 = ((TextView) itemView.findViewById(R.id.editText3));
        editText4 = ((TextView) itemView.findViewById(R.id.editText4));
        editText5 = ((TextView) itemView.findViewById(R.id.editText5));
        editText6 = ((TextView) itemView.findViewById(R.id.editText6));

        button1 = ((TextView) itemView.findViewById(R.id.button1));
    }

    public void bind() {
        textView1.setText("Imię Nazwisko klienta");
        textView2.setText("Rodzaj usług");
        textView3.setText("Kwota");
        textView4.setText("Data");
        textView5.setText("Miasto");
        textView6.setText("Opis transakcji");

        editText1.setHint("Imię Nazwisko klienta...");
        editText2.setHint("Podaj usługę...");
        editText3.setHint("Podaj kwotę...");
        editText4.setHint("Data transakcji...");
        editText5.setHint("Miasto...");
        editText6.setHint("Opis transakcji...");

        button1.setText("Dodaj");
        button1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                insertChild();
                break;
        }
    }

    private void insertChild() {
        FirebaseDatabase.getInstance().getReference()
                .child("Transactions")
                .push()
                .setValue(
                        new TransactionModel(
                                editText1.getText().toString(),
                                editText2.getText().toString(),
                                editText3.getText().toString(),
                                editText4.getText().toString(),
                                editText5.getText().toString(),
                                editText6.getText().toString()
                        )
                )
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        makeToast("Dodano wpis.");
                        ((AddTransactionActivity) itemView.getContext()).finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        makeToast("Niepowodzenie, wpis nie został dodany. Spróbuj ponownie.");
                    }
                });

        if (!ConnectionUtils.isConnected(itemView.getContext())) {
            makeToast("Zakolejkowano. Wpis zostanie dodany przy kolejnym dostępie do internetu.");
            ((AddTransactionActivity) itemView.getContext()).finish();
        }
    }

    private void makeToast(String text) {
        Toast.makeText(itemView.getContext(), text, Toast.LENGTH_SHORT).show();
    }
}
