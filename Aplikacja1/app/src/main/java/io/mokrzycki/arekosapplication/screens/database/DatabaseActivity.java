package io.mokrzycki.arekosapplication.screens.database;

import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.WeakReference;
import java.util.concurrent.CountDownLatch;

import io.mokrzycki.arekosapplication.R;
import io.mokrzycki.arekosapplication.common.CursorRecyclerViewAdapter;
import io.mokrzycki.arekosapplication.common.ItemOffsetDecoration;
import io.mokrzycki.arekosapplication.common.Model;
import io.mokrzycki.arekosapplication.databases.AppProvider;
import io.mokrzycki.arekosapplication.screens.client.ClientModel;
import io.mokrzycki.arekosapplication.screens.transaction.TransactionModel;
import io.mokrzycki.arekosapplication.widgets.RecyclerViewWithEmptyView;

/**
 * Created by michal on 10.09.2017.
 */

public class DatabaseActivity extends AppCompatActivity {

    private static final String TAG = "DatabaseActivity";
    private CountDownLatch countDownLatch;
    private WeakReference<RecyclerViewWithEmptyView> weakRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.database_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("BAZA DANYCH");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerViewWithEmptyView recyclerView = (RecyclerViewWithEmptyView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setEmptyView(findViewById(R.id.empty_view));
        recyclerView.addItemDecoration(new ItemOffsetDecoration((int) (Resources.getSystem().getDisplayMetrics().density * 16)));
        recyclerView.setAdapter(new CursorRecyclerViewAdapter(null) {
            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, Cursor cursor) {
                ((ViewHolderDefault) viewHolder).bind(cursor);
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new ViewHolderDefault(parent);
            }
        });

        weakRecyclerView = new WeakReference<>(recyclerView);

        countDownLatch = new CountDownLatch(2);
        handleTable(AppProvider.urlForTransactions(), "Transactions", TransactionModel.class);
        handleTable(AppProvider.urlForClients(), "Clients", ClientModel.class);
        handleQuery();
    }

    private void handleTable(final Uri uri, final String reference, final Class modelClass) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                getContentResolver().delete(uri, null, null);
                FirebaseDatabase.getInstance().getReference().child(reference).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot hisSnapshot : dataSnapshot.getChildren()) {
                            Model model = (Model) hisSnapshot.getValue(modelClass);
                            getContentResolver().insert(uri, model.toContentValues());
                        }
                        Log.d(TAG, "handleTable:run:onDataChange: stuff for " + modelClass + " ready");
                        countDownLatch.countDown();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        countDownLatch.countDown();
                    }
                });
            }
        }).start();
    }

    private void handleQuery() {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Log.d(TAG, "handleQuery:run: executed");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (weakRecyclerView != null) {
                            if (weakRecyclerView.get() != null) {
                                RecyclerViewWithEmptyView recyclerViewWithEmptyView = weakRecyclerView.get();
                                CursorRecyclerViewAdapter adapter = (CursorRecyclerViewAdapter) recyclerViewWithEmptyView.getAdapter();
                                adapter.swapCursor(
                                        getContentResolver().query(
                                                AppProvider.urlForCustomQueryJoinTransactionsClients(),
                                                null, null, null, null)
                                );
                            }
                        }
                    }
                });
            }
        }).start();
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
