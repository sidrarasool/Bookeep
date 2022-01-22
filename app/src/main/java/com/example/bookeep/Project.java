package com.example.bookeep;


import java.util.ArrayList;

public class  Project {
    public String name;
    public String description;
    public String cost;
    public String clientName;
    public String startedOn;
    public String completedOn = "";
    public String currency;

    public ArrayList<String> developers;

    public Project(){
    }

    public Project(String name, String description, String cost, String clientName, String startedOn, String completedOn, ArrayList<String> developers, String currency){
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.clientName = clientName;
        this.startedOn = startedOn;
        this.completedOn = completedOn;
        this.developers = developers;
        this.currency = currency;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setStartedOn(String startedOn) {
        this.startedOn = startedOn;
    }

    public void setCompletedOn(String completedOn) {
        this.completedOn = completedOn;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCost() {
        return cost;
    }

    public String getClientName() {
        return clientName;
    }

    public String getStartedOn() {
        return startedOn;
    }

    public String getCompletedOn() {
        return completedOn;
    }

    public ArrayList<String> getDevelopers() {
        return developers;
    }

    public void setDevelopers(ArrayList<String> developers) {
        this.developers = developers;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}

