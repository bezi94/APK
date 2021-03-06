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
class PradZnamionowyOdbiornikaRezystancyjnego3FazowegoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final TextView textView0;
    private final TextView textView1;
    private final TextView textView2;
    private final TextView textView3;
    private final EditText editText1;
    private final EditText editText2;
    private final Button button1;

    public PradZnamionowyOdbiornikaRezystancyjnego3FazowegoHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.calc_activity_calc_2_view_holder, parent, false));

        textView0 = (TextView) itemView.findViewById(R.id.textView0);
        textView1 = (TextView) itemView.findViewById(R.id.textView1);
        textView2 = (TextView) itemView.findViewById(R.id.textView2);
        textView3 = (TextView) itemView.findViewById(R.id.textView3);
        editText1 = (EditText) itemView.findViewById(R.id.editText1);
        editText2 = (EditText) itemView.findViewById(R.id.editText2);
        button1 = (Button) itemView.findViewById(R.id.button1);
    }

    public void bind() {
        textView0.setText("Prąd Znamionowy Odbiornika Rezystancyjnego 3 Fazowego");
        textView1.setText("Podaj moc znamionową (W):");
        textView2.setText("Podaj napięcie międzyprzewodowe (V):");
        button1.setText("Oblicz");

        button1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1: {
                float moc_znamionowa = TextUtils.isEmpty(editText1.getText().toString()) ? 0 : Float.parseFloat(editText1.getText().toString());
                float napiecie3f = TextUtils.isEmpty(editText2.getText().toString()) ? 0 : Float.parseFloat(editText2.getText().toString());
                float prad = (float) (moc_znamionowa / (Math.sqrt(3) * napiecie3f));
                float prad_pop = FloatUtils.fixLength(prad, 2);

                if (prad_pop < 13) {
                    textView3.setText(String.format("%sA. Przewod 1,5mm i zab. 13A.", prad_pop));
                } else if (prad_pop < 16) {
                    textView3.setText(String.format("%sA. Przewod 2,5mm i zab. 16A.", prad_pop));
                } else if (prad_pop < 25) {
                    textView3.setText(String.format("%sA. Przewod 4mm i zab. 25A.", prad_pop));
                } else if (prad_pop < 32) {
                    textView3.setText(String.format("%sA. Przewod 6mm i zab. 32A.", prad_pop));
                } else if (prad_pop < 40) {
                    textView3.setText(String.format("%sA. Przewod 10mm i zab 40A.", prad_pop));
                } else if (prad_pop < 50) {
                    textView3.setText(String.format("%sA. Przewod 16mm i zab. 50A.", prad_pop));
                } else if (prad_pop < 63) {
                    textView3.setText(String.format("%sA. Przewod 25mm i zab. 63A.", prad_pop));
                } else if (prad_pop < 80) {
                    textView3.setText(String.format("%sA. Przewod 35mm i zab. 80A.", prad_pop));
                } else {
                    textView3.setText(String.format("Wynik: %sA. Mam wiedze tylko do 80 A.", prad_pop));
                }
            }
            break;
        }
    }
}
