package com.example.raide_000.transactionkeeper;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * graphics.java
 *
 * Helper class simply used for switching activities and creating toasts.
 *
 * Created by Peter Meglis on 10/27/2016.
 */
public class Graphics {

    /*
     * Switches activities from "from" to "to."
     * ex. Graphics.switchTo(CurrentTransactions.this, RecentlyDeleted.class);
     */
    public static void switchTo(Context from, Class to) {
        Intent myIntent = new Intent(from, to);
        from.startActivity(myIntent);
    }

    /*
     * Creates a short length toast on the screen with string "s".
     */
    public static void toast(String s) {
        Toast toast = Toast.makeText(MyApplication.getAppContext(), s, Toast.LENGTH_SHORT);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}
