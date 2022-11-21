package com.example.ieti_industry_android;

import java.util.ArrayList;

public class Dropdown extends Control {
    String state;
    ArrayList<Option> options;

    public Dropdown(String label, int id, String state, ArrayList<Option> options) {
        super(label, id);
        this.state = state;
        this.options = options;
    }

    public String getLabel() {
        return label;
    }

    public void setState(String state) {
        this.state = state;
    }


    public String getState() {
        return state;
    }

    public ArrayList<Option> getoptions() {
        return options;
    }

    public void addOption(Option option) {
        options.add(option);
    }

    public void removeOption(Option option) {
        options.remove(option);
    }
}