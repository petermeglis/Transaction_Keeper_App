package com.example.raide_000.transactionkeeper;

import android.content.Context;
import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;

/**
 * Created by Raide_000 on 10/27/2016.
 */
public class Data {

    private File transactionsFile;
    private File accountsFile;
    private File quickInputsFile;

    private ArrayList<Transaction> currentTransactions;
    private LinkedHashMap<String, String> accounts;
    private ArrayList<Transaction> quickInputs;

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
        this.transactionsFile = new File("currentTransactions.txt");
        this.transactionsFile = new File("accounts.txt");
        this.transactionsFile = new File("quickInputs.txt");

        this.currentTransactions = new ArrayList<Transaction>();
        this.accounts = new LinkedHashMap<String, String>();
        this.quickInputs = new ArrayList<Transaction>();


        System.out.println("Transactions:");
        read("currentTransactions.txt");
        System.out.println("Accounts:");
        read("accounts.txt");
        System.out.println("Quick Inputs:");
        read("quickInputs.txt");

//        this.clearFile("quickInputs.txt");
//
//        addQuickInput(new Transaction("Lunch", "Meal Plan", "$3.85", ""));
//        addQuickInput(new Transaction("Dinner", "Meal Plan", "$5.00", ""));
//        addQuickInput(new Transaction("Laundry Wash", "Lion Cash+", "$1.50", ""));
//        addQuickInput(new Transaction("Laundry Dry", "Lion Cash+", "$0.50", ""));
//
//        this.clearFile("accounts.txt");
//
        System.out.println("--------------");
        read("accounts.txt");
//
//        addAccount("Meal Plan", "#DC304FFE");
//        addAccount("Lion Cash+", "#DCD50000");

//        clearQuickInputs();
//        addQuickInput(new Transaction("Lunch", "Meal Plan", "$3.85", ""));
//        addQuickInput(new Transaction("Dinner", "Meal Plan", "$5.00", ""));
//        addQuickInput(new Transaction("Laundry Wash", "Lion Cash+", "$1.50", ""));
//        addQuickInput(new Transaction("Laundry Dry", "Lion Cash+", "$0.50", ""));





    }


    // Accounts

    public void addAccount(String name, String color) {
        accounts.put(name, color);
        writeAccount(name);
    }

    public void removeAccount(String account) {
        System.out.println("Removing account: " + account);
        read("accounts.txt");
        accounts.remove(account);
        clearFile("accounts.txt");
        writeAccounts();
    }

    public String[] getAccountNames() {
        read("accounts.txt");
        String[] strings = new String[accounts.size()];
        Object[] keys = accounts.keySet().toArray();
        for (int i = 0; i < keys.length; i++) {
            strings[i] = keys[i].toString();
        }
        return strings;
    }

    public String getAccountColor(String account) {
        if (account.equals("None") || account.equals(""))
            return colors.get(0);
        return accounts.get(account);
    }

    public void setAccountColor(String account, String color) {
        accounts.put(account, color);
        writeAccounts();
    }

    public void writeAccounts() {
        System.out.println("Writing to file");
        for (Object str : accounts.keySet()) {
            writeAccount(str.toString());
        }
        System.out.println("Success");
    }

    public void writeAccount(String name) {
        System.out.println("Writing account: " + name + accounts.get(name) + " to the file");
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(MyApplication.getAppContext().openFileOutput("accounts.txt", Context.MODE_APPEND | Context.MODE_PRIVATE));
            outputStreamWriter.write(name + splitter2 + accounts.get(name) + splitter1 + "\n");
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
            Graphics.toast("Error in Saving Data");
        }
    }








    // Current Transactions

    public void addTransaction(Transaction t) {
        System.out.println("Adding transaction: " + t);
        writeTransaction(t);
        Graphics.toast("Transaction Added");
    }

    public void removeTransaction(Transaction t) {
        System.out.println("Removing transaction: " + t);
        read("currentTransactions.txt");
        for (Transaction tr : currentTransactions) {
            System.out.println(t.toString());
            System.out.println(tr.toString());
            if (t.toString().equals(tr.toString())) {
                System.out.println("True");
                currentTransactions.remove(tr);
                break;
            }
        }
        clearFile("currentTransactions.txt");
        writeTransactions();
    }

    public void clearTransactions() {
        currentTransactions = new ArrayList<Transaction>();
        clearFile("currentTransactions.txt");
    }


    public ArrayList<Transaction> getTransactions() {
        read("currentTransactions.txt");
        System.out.println("Getting Transactions");
        return currentTransactions;
    }

    public void writeTransactions() {
        System.out.println("Writing to file");
        for (Transaction t : currentTransactions) {
            writeTransaction(t);
        }
        System.out.println("Success");
    }

    public void writeTransaction(Transaction t) {
        System.out.println("Writing " + t + " to the file");
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(MyApplication.getAppContext().openFileOutput("currentTransactions.txt", Context.MODE_APPEND | Context.MODE_PRIVATE));
            outputStreamWriter.write(t.toString() + splitter1 + "\n");
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
            Graphics.toast("Error in Saving Data");
        }
    }

    public void sortByAccount() {
        for (int i = 0; i < currentTransactions.size(); i++) {
            int j = 0;
            while (j < i && currentTransactions.get(i).getAccount().compareTo(currentTransactions.get(j).getAccount()) > 0) {
                j++;
            }
            currentTransactions.add(j, currentTransactions.remove(i));
        }
        clearFile("currentTransactions.txt");
        writeTransactions();
    }

    public void sortByDate() {
        for (int i = 0; i < currentTransactions.size(); i++) {
            int j = 0;
            while (j < i && currentTransactions.get(i).getDate().compareTo(currentTransactions.get(j).getDate()) > 0) {
                j++;
            }
            currentTransactions.add(j, currentTransactions.remove(i));
        }
        clearFile("currentTransactions.txt");
        writeTransactions();
    }






    // Quick Inputs

    public void addQuickInput(Transaction t) {
        System.out.println("Adding quick input: " + t);
        writeQuickInput(t);
    }

    public void removeQuickInput(String t) {
        System.out.println("Removing quick input: " + t);
        read("quickInputs.txt");
        for (Transaction tr : quickInputs) {
            if (t.equals(tr.getName())) {
                System.out.println("True");
                quickInputs.remove(tr);
                break;
            }
        }
        clearFile("quickInputs.txt");
        writeQuickInputs();
        Graphics.toast("Quick Input Removed");
    }

    public int getNumQIs() {
        read("quickInputs.txt");
        return quickInputs.size();
    }

    public void clearQuickInputs() {
        currentTransactions = new ArrayList<Transaction>();
        clearFile("quickInputs.txt");
    }


    public ArrayList<Transaction> getQuickInputs() {
        read("quickInputs.txt");
        System.out.println("Getting quick inputs");
        return quickInputs;
    }

    public void writeQuickInputs() {
        System.out.println("Writing to file");
        for (Transaction t : quickInputs) {
            writeQuickInput(t);
        }
        System.out.println("Success");
    }

    public void writeQuickInput(Transaction t) {
        System.out.println("Writing " + t + " to the file");
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(MyApplication.getAppContext().openFileOutput("quickInputs.txt", Context.MODE_APPEND | Context.MODE_PRIVATE));
            outputStreamWriter.write(t.toString() + splitter1 + "\n");
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
            Graphics.toast("Error in Saving Data");
        }
    }




    // All

    public void clearFile(String file) {

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(MyApplication.getAppContext().openFileOutput(file, Context.MODE_PRIVATE));
            outputStreamWriter.write("");
            outputStreamWriter.close();
            System.out.println("Successfully cleared data");
        }
        catch (FileNotFoundException e) {        }
        catch (IOException e) { }
    }


    public void read(String file) {
        System.out.println("Reading data");
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
                System.out.println("RET: " + ret);

                if (file.equals("currentTransactions.txt")) {
                    currentTransactions.clear();
                    String[] strings = ret.split("&");
                    for (String s : strings) {
                        if (s.length() != 0)
                            currentTransactions.add(Transaction.toTransaction(s));
                    }
                }
                else if (file.equals("accounts.txt")) {
                    accounts.clear();
                    String[] each = ret.split(splitter1);
                    for (String s : each) {
                        if (s.length() != 0) {
                            String[] accountColor = s.split(splitter2);
                            accounts.put(accountColor[0], accountColor[1]);
                        }
                    }
                }
                else if (file.equals("quickInputs.txt")) {
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



                System.out.println("Successfully read");
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

    }




    public static String getDate() {
        return getDay() + getTime();
    }

    public static String getDay() {
        Calendar c = Calendar.getInstance();
        return (c.get(Calendar.MONTH) + 1) + "/"
                + c.get(Calendar.DATE) + "/"
                + c.get(Calendar.YEAR) + " ";
    }

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
