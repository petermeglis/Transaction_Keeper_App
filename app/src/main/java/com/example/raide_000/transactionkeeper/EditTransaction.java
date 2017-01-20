package com.example.raide_000.transactionkeeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class EditTransaction extends AppCompatActivity {

    private Transaction transaction;
    private Spinner transactionSpinner;

    private EditText name;
    private EditText account;
    private EditText amount;
    private EditText date;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_transaction);

        transactionSpinner = (Spinner) findViewById(R.id.transactionspinner);
        ArrayList<Transaction> transactions = CurrentTransactions.data.getTransactions();
        String[] items = new String[transactions.size()];
        for (int i = 0; i < items.length; i++) {
            Transaction t = transactions.get(i);
            items[i] = t.toString();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        transactionSpinner.setAdapter(adapter);


        name = (EditText) findViewById(R.id.editnametext);
        account = (EditText) findViewById(R.id.editaccounttext);
        amount = (EditText) findViewById(R.id.editamounttext);
        date = (EditText) findViewById(R.id.editdatetext);
//        updateEditTexts();

        saveButton = (Button) findViewById(R.id.savebutton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction.setName(name.getText().toString());
                transaction.setAccount(account.getText().toString());
                transaction.setAmount(amount.getText().toString());
                transaction.setDate(date.getText().toString());
                Graphics.switchTo(EditTransaction.this, CurrentTransactions.class);
            }
        });


    }


    public void updateEditTexts() {
        name.setText(transaction.getName());
        account.setText(transaction.getAccount());
        amount.setText(transaction.getAmount());
        date.setText(transaction.getDate());
    }

}
