package com.example.raide_000.transactionkeeper;

import android.content.Context;
import android.util.Log;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;

/**
 * data.java
 *
 * Stores all of the data for the app in text files (accounts, current transactions, and quick inputs).
 *
 * Created by Peter Meglis on 10/27/2016.
 */
public class Data {

    private String transactionsFile;
    private String accountsFile;
    private String quickInputsFile;

    private ArrayList<Transaction> currentTransactions;
    private LinkedHashMap<String, String> accounts;
    private ArrayList<Transaction> quickInputs;

    // Account colors
    public ArrayList<String> colors = new ArrayList<String>() {{
        add("#C88B8B8B");
        add("#C8B71C1C");
        add("#C8DE5F00");
        add("#C8CF9F00");
        add("#C8205823");
        add("#C83078FE");
        add("#C8001CCF");
        add("#C8791893");
    }};

    private final String splitter1 = "&";
    private final String splitter2 = ">";



    public Data() {
        this.transactionsFile = "currentTransactions.txt";
        this.accountsFile = "accounts.txt";
        this.quickInputsFile = "quickInputs.txt";

        this.currentTransactions = new ArrayList<Transaction>();
        this.accounts = new LinkedHashMap<String, String>();
        this.quickInputs = new ArrayList<Transaction>();

        read("currentTransactions.txt");
        read("accounts.txt");
        read("quickInputs.txt");
    }


    // Accounts

    /*
     * Adds an account (name and account color) to the accounts file.
     */
    public void addAccount(String name, String color) {
        accounts.put(name, color);
        writeAccount(name);
        Graphics.toast("Account Added");
    }

    /*
     * Removes an account from the accounts file.
     */
    public void removeAccount(String account) {
        read(this.accountsFile);
        accounts.remove(account);
        // Resets just the file.
        clearFile(this.accountsFile);
        // Rewrites the accounts.
        writeAccounts();
    }

    /*
     * Gets the account names.
     */
    public String[] getAccountNames() {
        read(this.accountsFile);
        String[] strings = new String[accounts.size()];
        Object[] keys = accounts.keySet().toArray();
        for (int i = 0; i < keys.length; i++) {
            strings[i] = keys[i].toString();
        }
        return strings;
    }

    /*
     * Gets the colors from the accounts.
     */
    public String getAccountColor(String account) {
        if (account.equals("None") || account.equals(""))
            return colors.get(0);
        return accounts.get(account);
    }

    /*
     * Sets an account color.
     */
    public void setAccountColor(String account, String color) {
        accounts.put(account, color);
        writeAccounts();
    }

    /*
     * Writes all the accounts to the file.
     */
    public void writeAccounts() {
        for (Object str : accounts.keySet()) {
            writeAccount(str.toString());
        }
    }

    /*
     * Helper - writes just one account to the file.
     */
    public void writeAccount(String name) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(MyApplication.getAppContext().openFileOutput(this.accountsFile, Context.MODE_APPEND | Context.MODE_PRIVATE));
            outputStreamWriter.write(name + splitter2 + accounts.get(name) + splitter1 + "\n");
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
            Graphics.toast("Error in Saving Data");
        }
    }








    // Current Transactions

    /*
     * Adds a transaction to the file.
     */
    public void addTransaction(Transaction t) {
        writeTransaction(t);
        Graphics.toast("Transaction Added");
    }

    /*
     * Removes a transaction from the file.
     */
    public void removeTransaction(Transaction t) {
        read(this.transactionsFile);
        for (Transaction tr : currentTransactions) {
            if (t.toString().equals(tr.toString())) {
                currentTransactions.remove(tr);
                break;
            }
        }
        // Clears just the file
        clearFile(this.transactionsFile);
        // Rewrites to the file
        writeTransactions();
        Graphics.toast("Transaction Removed");
    }

    /*
     * Completely clears both the file and the data.
     */
    public void clearTransactions() {
        currentTransactions = new ArrayList<Transaction>();
        clearFile(this.transactionsFile);
    }

    /*
     * Gets the transactions from the file.
     */
    public ArrayList<Transaction> getTransactions() {
        read(this.transactionsFile);
        return currentTransactions;
    }

    /*
     * Writes all the transactions to the file.
     */
    public void writeTransactions() {
        for (Transaction t : currentTransactions) {
            writeTransaction(t);
        }
    }

    /*
     * Helper - writes a transaction to the transaction file.
     */
    public void writeTransaction(Transaction t) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(MyApplication.getAppContext().openFileOutput(this.transactionsFile, Context.MODE_APPEND | Context.MODE_PRIVATE));
            outputStreamWriter.write(t.toString() + splitter1 + "\n");
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
            Graphics.toast("Error in Saving Data");
        }
    }

    /*
     * Sorts the transactions by accounts.
     */
    public void sortByAccount() {
        for (int i = 0; i < currentTransactions.size(); i++) {
            int j = 0;
            while (j < i && currentTransactions.get(i).getAccount().compareTo(currentTransactions.get(j).getAccount()) > 0) {
                j++;
            }
            currentTransactions.add(j, currentTransactions.remove(i));
        }
        clearFile(this.transactionsFile);
        writeTransactions();
    }

    /*
     * Sorts the transactions by date.
     */
    public void sortByDate() {
        for (int i = 0; i < currentTransactions.size(); i++) {
            int j = 0;
            while (j < i && currentTransactions.get(i).getDate().compareTo(currentTransactions.get(j).getDate()) > 0) {
                j++;
            }
            currentTransactions.add(j, currentTransactions.remove(i));
        }
        clearFile(this.transactionsFile);
        writeTransactions();
    }






    // Quick Inputs

    /*
     * Adds a quick input to the file.
     */
    public void addQuickInput(Transaction t) {
        writeQuickInput(t);
        Graphics.toast("Quick Input Added");
    }

    /*
     * Removes a quick input from the file.
     */
    public void removeQuickInput(String t) {
        read(this.quickInputsFile);
        for (Transaction tr : quickInputs) {
            if (t.equals(tr.getName())) {
                quickInputs.remove(tr);
                break;
            }
        }
        clearFile(this.quickInputsFile);
        writeQuickInputs();
        Graphics.toast("Quick Input Removed");
    }

    /*
     * Gets the number of quick inputs.
     */
    public int getNumQIs() {
        read(this.quickInputsFile);
        return quickInputs.size();
    }

    /*
     * Completely clears the quick inputs from both the file and data.
     */
    public void clearQuickInputs() {
        currentTransactions = new ArrayList<Transaction>();
        clearFile(this.quickInputsFile);
    }

    /*
     * Gets the quick inputs.
     */
    public ArrayList<Transaction> getQuickInputs() {
        read(this.quickInputsFile);
        return quickInputs;
    }

    /*
     * Writes all the quick inputs to the file.
     */
    public void writeQuickInputs() {
        for (Transaction t : quickInputs) {
            writeQuickInput(t);
        }
    }

    /*
     * Helper - writes just one quick input to the file.
     */
    public void writeQuickInput(Transaction t) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(MyApplication.getAppContext().openFileOutput(this.quickInputsFile, Context.MODE_APPEND | Context.MODE_PRIVATE));
            outputStreamWriter.write(t.toString() + splitter1 + "\n");
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
            Graphics.toast("Error in Saving Data");
        }
    }




    // All

    /*
     * Clears a file.
     */
    public void clearFile(String file) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(MyApplication.getAppContext().openFileOutput(file, Context.MODE_PRIVATE));
            outputStreamWriter.write("");
            outputStreamWriter.close();
        }
        catch (FileNotFoundException e) {        }
        catch (IOException e) { }
    }

    /*
     * Reads a file, setting the data.
     */
    public void read(String file) {
        String ret = "";

        try {
            InputStream inputStream = MyApplication.getAppContext().openFileInput(file);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();

                if (file.equals(this.transactionsFile)) {
                    currentTransactions.clear();
                    String[] strings = ret.split("&");
                    for (String s : strings) {
                        if (s.length() != 0)
                            currentTransactions.add(Transaction.toTransaction(s));
                    }
                }
                else if (file.equals(this.accountsFile)) {
                    accounts.clear();
                    String[] each = ret.split(splitter1);
                    for (String s : each) {
                        if (s.length() != 0) {
                            String[] accountColor = s.split(splitter2);
                            accounts.put(accountColor[0], accountColor[1]);
                        }
                    }
                }
                else if (file.equals(this.quickInputsFile)) {
                    quickInputs.clear();
                    String[] strings = ret.split(splitter1);
                    for (String s : strings) {
                        if (s.length() != 0)
                            quickInputs.add(Transaction.toTransaction(s));
                    }
                }
                else {
                    System.out.println("ERROR READING FILE");
                }
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

    }

    /*
     * Gets the current date.
     */
    public static String getDate() {
        return getDay() + getTime();
    }

    /*
     * Gets the current day.
     */
    public static String getDay() {
        Calendar c = Calendar.getInstance();
        return (c.get(Calendar.MONTH) + 1) + "/"
                + c.get(Calendar.DATE) + "/"
                + c.get(Calendar.YEAR) + " ";
    }

    /*
     * Gets the current time.
     */
    public static String getTime() {
        Calendar c = Calendar.getInstance();
        String ampm = (c.get(Calendar.AM_PM) == 0) ? "AM" : "PM";
        int hour = (c.get(Calendar.HOUR) == 0) ? (12) : c.get(Calendar.HOUR);
        int minute = c.get(Calendar.MINUTE);
        String min = "";
        if (minute >= 0 && minute < 10) {
            min = "0" + minute;
        }
        else {
            min = "" + minute;
        }
        return hour + ":"
                + min + " "
                + ampm;
    }
}
