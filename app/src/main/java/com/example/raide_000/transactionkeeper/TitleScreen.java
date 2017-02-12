package com.example.raide_000.transactionkeeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * titlescreen.java
 *
 * Holds the class related to the title screen of the app.
 *
 * Peter Meglis
 * 12 February 2017
 */
public class TitleScreen extends AppCompatActivity {

    private Button qiButton;
    private Button miButton;
    private Button ctButton;

    private ImageButton settings;

    public void init() {

        qiButton = (Button) findViewById(R.id.qiButton);
        qiButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Graphics.switchTo(TitleScreen.this, QuickInput.class);
            }
        });

        miButton = (Button) findViewById(R.id.miButton);
        miButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Graphics.switchTo(TitleScreen.this, ManualInput.class);
            }
        });

        ctButton = (Button) findViewById(R.id.ctButton);
        ctButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Graphics.switchTo(TitleScreen.this, CurrentTransactions.class);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_screen);
        init();


        settings = (ImageButton) findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Graphics.switchTo(TitleScreen.this, Settings.class);
            }
        });

    }


}
