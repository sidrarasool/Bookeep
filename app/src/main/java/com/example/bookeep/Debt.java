package com.example.bookeep;

public class Debt {
    public String from = "";
    public String to = "";
    public String amount = "";
    public String created = "";

    public Debt(){}

    public Debt(String from, String to, String amount, String created){
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.created = created;
    }

    public int updateDebt(int amount){
        int x = Integer.parseInt(this.amount) + amount;

        this.amount = Integer.toString(x);

        return x;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getAmount() {
        return amount;
    }

    public String getCreated() {
        return created;
    }
}