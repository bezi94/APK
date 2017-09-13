package io.mokrzycki.arekosapplication.screens.client;

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
    private final TextView textView3;
    private final TextView textView4;
    private final TextView textView5;
    private final TextView textView6;
    private final TextView textView7;
    private final TextView textView8;
    private final TextView editText1;
    private final TextView editText3;
    private final TextView editText4;
    private final TextView editText5;
    private final TextView editText6;
    private final TextView editText7;
    private final TextView editText8;
    private final TextView button1;

    public ViewHolderDefault(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.add_client_activity_view_holder, parent, false));

        textView1 = ((TextView) itemView.findViewById(R.id.textView1));
        textView3 = ((TextView) itemView.findViewById(R.id.textView3));
        textView4 = ((TextView) itemView.findViewById(R.id.textView4));
        textView5 = ((TextView) itemView.findViewById(R.id.textView5));
        textView6 = ((TextView) itemView.findViewById(R.id.textView6));
        textView7 = ((TextView) itemView.findViewById(R.id.textView7));
        textView8 = ((TextView) itemView.findViewById(R.id.textView8));

        editText1 = ((TextView) itemView.findViewById(R.id.editText1));
        editText3 = ((TextView) itemView.findViewById(R.id.editText3));
        editText4 = ((TextView) itemView.findViewById(R.id.editText4));
        editText5 = ((TextView) itemView.findViewById(R.id.editText5));
        editText6 = ((TextView) itemView.findViewById(R.id.editText6));
        editText7 = ((TextView) itemView.findViewById(R.id.editText7));
        editText8 = ((TextView) itemView.findViewById(R.id.editText8));

        button1 = ((TextView) itemView.findViewById(R.id.button1));
    }

    public void bind() {
        textView1.setText("Imię Nazwisko");
        textView3.setText("Numer lokalu");
        textView4.setText("Numer budynku");
        textView5.setText("Miasto");
        textView6.setText("Ulica");
        textView7.setText("Telefon");
        textView8.setText("Email");

        editText1.setHint("Imię Nazwisko...");
        editText3.setHint("Numer lokalu...");
        editText4.setHint("Numer budynku...");
        editText5.setHint("Miasto...");
        editText6.setHint("Ulica...");
        editText7.setHint("Telefon...");
        editText8.setHint("Email...");

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
                .child("Clients")
                .push()
                .setValue(
                        new ClientModel(
                                editText1.getText().toString(),
                                editText3.getText().toString(),
                                editText4.getText().toString(),
                                editText5.getText().toString(),
                                editText6.getText().toString(),
                                editText7.getText().toString(),
                                editText8.getText().toString()
                        )
                )
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        makeToast("Dodano wpis.");
                        ((AddClientActivity) itemView.getContext()).finish();
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
            ((AddClientActivity) itemView.getContext()).finish();
        }
    }

    private void makeToast(String text) {
        Toast.makeText(itemView.getContext(), text, Toast.LENGTH_SHORT).show();
    }
}
