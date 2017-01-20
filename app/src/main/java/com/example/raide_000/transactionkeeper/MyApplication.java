package com.example.raide_000.transactionkeeper;

import android.app.Application;
import android.content.Context;

/**
 * Created by Raide_000 on 10/27/2016.
 */
public class MyApplication extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}
