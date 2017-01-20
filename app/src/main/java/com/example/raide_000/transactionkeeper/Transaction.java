package com.example.raide_000.transactionkeeper;

import android.widget.TextView;

import java.io.Serializable;

/**
 * Created by Raide_000 on 10/12/2016.
 */
public class Transaction {

    private String name;
    private String account;
    private String amount;
    private String date;


    public Transaction(String name, String account, String amount, String date) {
        this.name = fixName(name);
        this.account = fixAccount(account);
        this.amount = fixAmount(amount);
        this.date = fixDate(date);
    }


    public static Transaction toTransaction(String s) {
        System.out.println(s);
        if (s.length() == 0)
            return null;
        String[] strings = s.split("~");
        if (strings.length == 3) {
            return new Transaction(strings[0], strings[1], strings[2], "");
        }
        return new Transaction(strings[0], strings[1], strings[2], strings[3]);
    }

    private String fixName(String name) {
        if (name.length() == 0)
            return "No Name";
        else
            return name;
    }

    private String fixAccount(String account) {
        if (account.equals("None"))
            return "";
        else
            return account;
    }

    private String fixAmount(String amount) {
        if (amount.length() == 0)
            return "$0.00";
        else if (amount.length() == 1 && amount.charAt(0) == '$')
            return "No Amount";
        else if (amount.indexOf('$') == -1)
            return "$" + amount;
        else if (amount.indexOf('$') == amount.length() - 1)
            return "$" + amount.substring(0, amount.length() - 1);
        else if (amount.indexOf('$') != 0)
            return "ERROR: (" + amount + ")";
        else if (!amount.contains("."))
            return amount + ".00";
        else
            return amount;
    }

    private String fixDate(String date) {
        if (date.equals(Data.getDay()))
            return date + Data.getTime();
        return date;
    }

    public String getRealDate() {
        String[] temp = this.date.split(" ");
        String dateTemp = temp[0];
        String date = dateTemp.substring(dateTemp.length() - 4);
        date += dateTemp.substring(dateTemp.indexOf("/") + 1, dateTemp.lastIndexOf("/"));
        date += dateTemp.substring(0, dateTemp.indexOf("/"));
        return date;
    }





    public String getName() {
        return name;
    }

    public String getAccount() {
        return account;
    }

    public String getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String getJustDate() {
        return this.date.substring(0, this.date.lastIndexOf("/") + 5);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setAmount(String amount) { this.amount = amount; }

    public void setDate(String date) { this.date = date; }



    public String toString() {
        return this.name + "~" + this.account + "~" + this.amount + "~" + this.date;
    }

    public void print() {
        System.out.println("Name: " + this.name);
        System.out.println("Account: " + this.account);
        System.out.println("Amount: " + this.amount);
        System.out.println("Date: " + this.date);
    }

}
