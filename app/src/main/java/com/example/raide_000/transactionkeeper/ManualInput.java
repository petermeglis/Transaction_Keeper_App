package com.example.raide_000.transactionkeeper;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;

public class ManualInput extends AppCompatActivity {

    private EditText name;
    private Spinner account;
    private EditText amount;
    private TextView date;
    private Button changeDateButton;
    private Button createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_input);

        name = (EditText) findViewById(R.id.nametext);

        account = (Spinner)findViewById(R.id.accounttext);
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

        date = (TextView) findViewById(R.id.datetext);
        date.setText(Data.getDay());


        class mDateSetListener implements DatePickerDialog.OnDateSetListener {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                int mYear = year;
                int mMonth = monthOfYear;
                int mDay = dayOfMonth;
                date.setText(new StringBuilder()
                        .append(mMonth + 1).append("/").append(mDay).append("/")
                        .append(mYear).append(""));
            }
        }

        changeDateButton = (Button) findViewById(R.id.changeDateButton);
        changeDateButton.setTextSize(15);
        changeDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("POPUP DATE");
                Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                System.out.println("the selected " + mDay);
                DatePickerDialog dialog = new DatePickerDialog(ManualInput.this,
                        new mDateSetListener(), mYear, mMonth, mDay);
                dialog.show();
            }
        });

        createButton = (Button) findViewById(R.id.createbutton);
        createButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Transaction t = new Transaction(name.getText().toString(),
                        account.getSelectedItem().toString(),
                        amount.getText().toString(),
                        date.getText().toString());
                t.print();

                CurrentTransactions.addTransaction(t);
                Graphics.switchTo(ManualInput.this, TitleScreen.class);
            }
        });



    }






}
