package com.example.raide_000.transactionkeeper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * settings.java
 *
 * Settings class for the Transaction Keeper app. Available settings are:
 * Adding a new account
 * Changing an account color
 * Removing an account
 * Removing a quick input
 *
 * Peter Meglis
 * February 2017
 */
public class Settings extends AppCompatActivity {

    private EditText addAccountName;
    private Button addButton;

    private Spinner changeAccountColor;
    private SeekBar colorSeekBar;
    private Button setButton;

    private Spinner removeAccountName;
    private Button removeAccountButton;

    private Spinner removeQuickInputName;
    private Button removeQuickInputButton;

    private GradientDrawable drawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setBackgroundDrawableResource(R.mipmap.background);

        addAccountName = (EditText) findViewById(R.id.addAccountText);

        // Adds account
        addButton = (Button) findViewById(R.id.addAccountButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!addAccountName.getText().toString().equals("")) {
                    CurrentTransactions.data.addAccount(addAccountName.getText().toString(), "#641d7b1c");
                    Graphics.toast("Account Added");
                    finish();
                    startActivity(getIntent());
                }
            }
        });

        // Sets color
        setButton = (Button) findViewById(R.id.setColorButton);
        setButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String account = changeAccountColor.getSelectedItem().toString();
                String color = CurrentTransactions.data.colors.get(colorSeekBar.getProgress());
                CurrentTransactions.data.setAccountColor(account, color);
                Graphics.toast("Color Set");
            }
        });

        // Removes account
        removeAccountButton = (Button) findViewById(R.id.removeAccountButton);
        removeAccountButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                CurrentTransactions.data.removeAccount(removeAccountName.getSelectedItem().toString());
                                Graphics.toast("Account Removed");
                                refreshSpinners();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

            }
        });

        // Removes quick input
        removeQuickInputButton = (Button) findViewById(R.id.removeQuickInputButton);
        removeQuickInputButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                CurrentTransactions.data.removeQuickInput(removeQuickInputName.getSelectedItem().toString());
                                Graphics.toast("Quick Input Removed");
                                refreshSpinners();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

            }
        });

        // Gets the input from the user
        changeAccountColor = (Spinner) findViewById(R.id.changeAccountColorSpinner);
        removeAccountName = (Spinner) findViewById(R.id.removeAccountSpinner);
        removeQuickInputName = (Spinner) findViewById(R.id.removeQuickInputSpinner);

        String[] accounts = CurrentTransactions.data.getAccountNames();
        ArrayAdapter<String> accountsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, accounts);

        String[] qis = new String[CurrentTransactions.data.getNumQIs()];
        ArrayList<Transaction> quickInputs = CurrentTransactions.data.getQuickInputs();
        for (int i = 0; i < qis.length; i++) {
            qis[i] = quickInputs.get(i).getName();
        }
        ArrayAdapter<String> quickInputAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, qis);


        changeAccountColor.setAdapter(accountsAdapter);
        removeAccountName.setAdapter(accountsAdapter);
        removeQuickInputName.setAdapter(quickInputAdapter);

        changeAccountColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setSeekBarProgress(changeAccountColor.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        // Color bar from data.java colors
        colorSeekBar = (SeekBar) findViewById(R.id.colorSeekBar);
        colorSeekBar.setMax(CurrentTransactions.data.colors.size() - 1);

        drawable = new GradientDrawable();
        if (changeAccountColor.getSelectedItem() != null) {
            setSeekBarProgress(changeAccountColor.getSelectedItem().toString());
            drawable.setColor(Color.parseColor(CurrentTransactions.data.getAccountColor(changeAccountColor.getSelectedItem().toString())));
        }
        else {
            colorSeekBar.setProgress(0);
            drawable.setColor(Color.parseColor(CurrentTransactions.data.colors.get(0)));
        }
        drawable.setStroke(2, Color.parseColor("#000000"));
        drawable.setCornerRadius(25);
        changeAccountColor.setBackground(drawable);
        changeAccountColor.setPadding(10, 0, 0, 0);

        colorSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                String newColor = CurrentTransactions.data.colors.get(progressValue);
                drawable.setColor(Color.parseColor(newColor));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    }

    /*
     * Refreshes the info for the spinners
     */
    private void refreshSpinners() {
        String[] items = CurrentTransactions.data.getAccountNames();
        ArrayAdapter<String> accountAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);

        String[] qis = new String[CurrentTransactions.data.getNumQIs()];
        ArrayList<Transaction> quickInputs = CurrentTransactions.data.getQuickInputs();
        for (int i = 0; i < qis.length; i++) {
            qis[i] = quickInputs.get(i).getName();
        }
        ArrayAdapter<String> quickInputAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, qis);

        changeAccountColor.setAdapter(accountAdapter);
        removeAccountName.setAdapter(accountAdapter);
        removeQuickInputName.setAdapter(quickInputAdapter);
    }

    /*
     * Sets the default seekbar progress based on the current account chosen for the color picker.
     */
    private void setSeekBarProgress(String account) {
        String color = CurrentTransactions.data.getAccountColor(account);
        int index = CurrentTransactions.data.colors.indexOf(color);

        if (index == -1) {
            colorSeekBar.setProgress(0);
        }
        else {
            colorSeekBar.setProgress(index);
        }
    }



}
