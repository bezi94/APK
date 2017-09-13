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
 * Przekroj dla odbiornika 1 fazowego
 */
class PrzekrojDlaOdbiornika1FazowegoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final TextView textView0;
    private final TextView textView1;
    private final TextView textView2;
    private final TextView textView3;
    private final EditText editText1;
    private final EditText editText2;
    private final Button button1;

    public PrzekrojDlaOdbiornika1FazowegoHolder(ViewGroup parent) {
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
        textView0.setText("Przekrój dla odbiornika 1 fazowego");
        textView1.setText("Podaj moc znamionową (W):");
        textView2.setText("Podaj napięcie znamionowe (V):");
        button1.setText("Oblicz");

        button1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1: {
                float moc_znamionowa = TextUtils.isEmpty(editText1.getText().toString()) ? 0 : Float.parseFloat(editText1.getText().toString());
                float napiecie = TextUtils.isEmpty(editText2.getText().toString()) ? 0 : Float.parseFloat(editText2.getText().toString());
                float prad = moc_znamionowa / napiecie;
                float prad_pop = FloatUtils.fixLength(prad, 2);

                if (moc_znamionowa > 3680) {
                    textView3.setText("Prąd powyżej 16A, potrzebne zasilanie 3-fazowe");
                } else if (prad_pop > 9.5) {
                    textView3.setText(String.format("%sA. Zastosuj przewod 2,5mm i zab. 16A.", prad_pop));
                } else if (prad_pop < 9.5) {
                    textView3.setText(String.format("%sA. Zastosuj przewod 1,5mm i zab 10A.", prad_pop));
                } else {
                    textView3.setText("Wynik nie obsłużony, 9.5");
                }
            }
            break;
        }
    }
}
