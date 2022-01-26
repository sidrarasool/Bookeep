package com.example.bookeep;


public class Payment {
    public String name;
    public int amount;

    public Payment(){
    }

    public Payment(String name, int amount){
        this.name = name;
        this.amount = amount;
    }

    public int UpdatePayment(int amount) {
        this.amount += amount;
        return this.amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }
}

