package com.example.raide_000.transactionkeeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class NewQuickInput extends AppCompatActivity {

    private EditText name;
    private Spinner account;
    private EditText amount;

    private Button createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_quick_input);

        name = (EditText) findViewById(R.id.newquickinputname);

        account = (Spinner)findViewById(R.id.newquickinputaccount);
        String[] accounts = CurrentTransactions.data.getAccountNames();
        String[] items = new String[accounts.length + 1];
        for (int i = 0; i < accounts.length; i++) {
            items[i] = accounts[i];
        }
        items[items.length - 1] = "None";
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        account.setAdapter(adapter);

        amount = (EditText) findViewById(R.id.amounttext);
        amount.setSelection(1);


        createButton = (Button) findViewById(R.id.newquickinputcreatebutton);
        createButton.setBackgroundResource(R.drawable.create_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CurrentTransactions.data.addQuickInput(new Transaction(name.getText().toString(), account.getSelectedItem().toString(), amount.getText().toString(), " "));
                Graphics.toast("Quick Input Added");
                Graphics.switchTo(NewQuickInput.this, QuickInput.class);
            }
        });
    }
}
