package com.example.raide_000.transactionkeeper;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Raide_000 on 10/27/2016.
 */
public class Graphics {



    public static void switchTo(Context from, Class to) {
        Intent myIntent = new Intent(from, to);
        from.startActivity(myIntent);
    }


    public static void toast(String s) {
        Toast toast = Toast.makeText(MyApplication.getAppContext(), s, Toast.LENGTH_SHORT);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}
