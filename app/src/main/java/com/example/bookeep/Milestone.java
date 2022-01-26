package com.example.bookeep;

public class Milestone {
    String amount;
    String exchangeRate;
    String receivedOn;
    String description;

    public Milestone(){
    }

    public Milestone( String amount, String exchangeRate, String receivedOn, String description){
        this.amount = amount;
        this.exchangeRate = exchangeRate;
        this.receivedOn = receivedOn;
        this.description = description;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public void setReceivedOn(String receivedOn) {
        this.receivedOn = receivedOn;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmount() {
        return amount;
    }

    public String getExchangeRate() {
        return exchangeRate;
    }

    public String getReceivedOn() {
        return receivedOn;
    }

    public String getDescription() {
        return description;
    }
}

