package com.example.android.bookeep;

import java.util.ArrayList;
import java.util.Vector;

public class Developer {
    public String name;
    public String joined;

    public ArrayList<String> technologies;

    public Developer(){}

    public Developer(String name, String joined, ArrayList<String> fields){
        this.name = name;
        this.joined = joined;

        if(fields != null){
            technologies = new ArrayList<String>();
            technologies = (ArrayList) fields.clone();
        }
        else technologies = new ArrayList<String>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setJoined(String joined) {
        this.joined = joined;
    }

    public void setTechnologies(ArrayList<String> technologies) {
        this.technologies = technologies;
    }
}