package io.mokrzycki.arekosapplication.screens.calc;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import io.mokrzycki.arekosapplication.R;
import io.mokrzycki.arekosapplication.utils.FloatUtils;

/**
 * Created by michal on 10.09.2017.
 */
class PradZnamionowySilnika1FazowegoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final TextView textView0;
    private final TextView textView1;
    private final TextView textView2;
    private final TextView textView3;
    private final TextView textView4;
    private final TextView textView5;
    private final EditText editText1;
    private final EditText editText2;
    private final EditText editText3;
    private final EditText editText4;
    private final Button button1;

    public PradZnamionowySilnika1FazowegoHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.calc_activity_calc_4_view_holder, parent, false));

        textView0 = (TextView) itemView.findViewById(R.id.textView0);
        textView1 = (TextView) itemView.findViewById(R.id.textView1);
        textView2 = (TextView) itemView.findViewById(R.id.textView2);
        textView3 = (TextView) itemView.findViewById(R.id.textView3);
        textView4 = (TextView) itemView.findViewById(R.id.textView4);
        textView5 = (TextView) itemView.findViewById(R.id.textView5);
        editText1 = (EditText) itemView.findViewById(R.id.editText1);
        editText2 = (EditText) itemView.findViewById(R.id.editText2);
        editText3 = (EditText) itemView.findViewById(R.id.editText3);
        editText4 = (EditText) itemView.findViewById(R.id.editText4);
        button1 = (Button) itemView.findViewById(R.id.button1);
    }

    public void bind() {
        textView0.setText("Prąd Znamionowy Silnika 1 Fazowego");
        textView1.setText("Podaj moc znamionową (W):");
        textView2.setText("Podaj napięcie znamionowe (V):");
        textView3.setText("Podaj sprawność znamionową silnika:");
        textView4.setText("Podaj współczynnik mocy znamionowej:");
        button1.setText("Oblicz");

        button1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1: {
                float moc_znamionowa = TextUtils.isEmpty(editText1.getText().toString()) ? 0 : Float.parseFloat(editText1.getText().toString());
                float napiecie = TextUtils.isEmpty(editText2.getText().toString()) ? 0 : Float.parseFloat(editText2.getText().toString());
                float sprawnosc = TextUtils.isEmpty(editText3.getText().toString()) ? 0 : Float.parseFloat(editText3.getText().toString());
                float wspolczynnik = TextUtils.isEmpty(editText4.getText().toString()) ? 0 : Float.parseFloat(editText4.getText().toString());
                float prad = moc_znamionowa / (napiecie * sprawnosc * wspolczynnik);
                float prad_pop = FloatUtils.fixLength(prad, 2);

                if (moc_znamionowa > 3680) {
                    textView5.setText("Prąd powyżej 16A, potrzebne zasilanie 3-fazowe");
                } else if (prad_pop > 9.5) {
                    textView5.setText(String.format("%sA. Zastosuj przewod 2,5mm i zab. 16A.", prad_pop));
                } else if (prad_pop < 9.5) {
                    textView5.setText(String.format("%sA. Zastosuj przewod 1,5mm i zab 10A.", prad_pop));
                } else {
                    textView5.setText("Wynik nie obsłużony, 9.5");
                }
            }
            break;
        }
    }
}
