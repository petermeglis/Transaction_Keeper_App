package com.example.raide_000.transactionkeeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * recentlydeleted.java
 *
 * This class is used for the recently deleted screen. This
 * screen shows the last five recently deleted transactions.
 *
 * Peter Meglis
 * 12 February 2017
 */
public class RecentlyDeleted extends AppCompatActivity {

    public static ArrayList<TextView> deleted = new ArrayList<TextView>();
    public static LinearLayout deletedLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recently_deleted);

        deletedLayout = (LinearLayout) findViewById(R.id.deletedLayout);
        update();
    }

    /*
     * Adds a transaction to the list of recently deleted
     */
    public static void addTransaction(TextView textView) {
        textView.setBackgroundResource(R.drawable.deleted_transaction);
        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });

        deleted.add(0, textView);
        if (deleted.size() == 5) {
            deleted.remove(4);
        }
    }

    /*
     * Updates the screen by removing then re-adding all the transactions.
     */
    public void update() {
        for (TextView t : deleted) {
            ((LinearLayout)t.getParent()).removeView(t);
            deletedLayout.addView(t);
            t.setVisibility(View.VISIBLE);
        }
    }
}
