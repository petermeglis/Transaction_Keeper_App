package com.example.raide_000.transactionkeeper;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * quickinput.java
 *
 * This class is used for the quick input screen.
 *
 * Peter Meglis
 * 12 February 2017
 */
public class QuickInput extends AppCompatActivity {

    private Button addButton;
    private LinearLayout layout;

    private ArrayList<Transaction> buttonTransactions;
    private ArrayList<Button> buttons = new ArrayList<Button>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_input);

        addButton = (Button) findViewById(R.id.newQuickInput);
        final GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.parseColor("#641d7b1c"));
        drawable.setStroke(4, Color.parseColor("#000000"));
        drawable.setCornerRadius(6);
        addButton.setBackground(drawable);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Graphics.switchTo(QuickInput.this, NewQuickInput.class);
            }
        });
        addButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    drawable.setColorFilter(Color.parseColor("#64494949"), PorterDuff.Mode.DARKEN);
                else if (event.getAction() == MotionEvent.ACTION_UP)
                    drawable.clearColorFilter();

                return false;
            }
        });

        layout = (LinearLayout) findViewById(R.id.quickInputLayout);
        update();
    }

    public void addButton(final Transaction t) {
        System.out.println("Quick input transaction:" + t);
        System.out.println(t.getName() + t.getAmount());
        final Button button = new Button(MyApplication.getAppContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 225);
        layoutParams.setMargins(0, 0, 0, 20);
        button.setLayoutParams(layoutParams);

        String account = t.getAccount();
        String buttonColor = CurrentTransactions.data.getAccountColor(account);

        final GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.parseColor(buttonColor));
        drawable.setStroke(4, Color.parseColor("#000000"));
        drawable.setCornerRadius(10);
        button.setBackgroundResource(R.drawable.quick_input_button);
        button.setBackground(drawable);

        button.setTextAppearance(MyApplication.getAppContext(), R.style.quick_button_text);
        button.setText(t.getName() + " " + t.getAmount());
        button.setLines(1);

        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    drawable.setColorFilter(Color.parseColor("#64494949"), PorterDuff.Mode.DARKEN);
                else if (event.getAction() == MotionEvent.ACTION_UP)
                    drawable.clearColorFilter();
                return false;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                t.setDate(Data.getDate());
                CurrentTransactions.addTransaction(t);
                Graphics.switchTo(QuickInput.this, TitleScreen.class);
            }
        });

        buttons.add(button);
    }


    public void update() {
        ArrayList<Transaction> list = CurrentTransactions.data.getQuickInputs();
        buttons = new ArrayList<Button>();
        for (Transaction t : list) {
            addButton(t);
        }
        for (Button b : buttons) {
            layout.addView(b);
        }

    }






}




