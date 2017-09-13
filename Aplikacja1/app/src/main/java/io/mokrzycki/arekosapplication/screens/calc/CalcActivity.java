package io.mokrzycki.arekosapplication.screens.calc;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;

import io.mokrzycki.arekosapplication.R;
import io.mokrzycki.arekosapplication.common.ItemOffsetDecoration;

/**
 * Created by michal on 10.09.2017.
 */

public class CalcActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calc_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("KALKULATOR");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new ItemOffsetDecoration((int) (Resources.getSystem().getDisplayMetrics().density * 16)));
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                if (viewType == 0) {
                    return new PradZnamionowyOdbiornikaRezystancyjnego3FazowegoHolder(parent);
                } else if (viewType == 1) {
                    return new PradZnamionowySilnika1FazowegoHolder(parent);
                } else if (viewType == 2) {
                    return new PradZnamionowySilnika3FazowegoHolder(parent);
                } else if (viewType == 3) {
                    return new PrzekrojDlaOdbiornika1FazowegoHolder(parent);
                } else {
                    throw new IllegalArgumentException("viewtype not handled, was " + viewType);
                }
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                if (holder instanceof PradZnamionowyOdbiornikaRezystancyjnego3FazowegoHolder) {
                    ((PradZnamionowyOdbiornikaRezystancyjnego3FazowegoHolder) holder).bind();
                } else if (holder instanceof PradZnamionowySilnika1FazowegoHolder) {
                    ((PradZnamionowySilnika1FazowegoHolder) holder).bind();
                } else if (holder instanceof PradZnamionowySilnika3FazowegoHolder) {
                    ((PradZnamionowySilnika3FazowegoHolder) holder).bind();
                } else if (holder instanceof PrzekrojDlaOdbiornika1FazowegoHolder) {
                    ((PrzekrojDlaOdbiornika1FazowegoHolder) holder).bind();
                } else {
                    throw new IllegalArgumentException("bind not handled, was " + holder);
                }
            }

            @Override
            public int getItemViewType(int position) {
                return position;
            }

            @Override
            public int getItemCount() {
                return 4;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
