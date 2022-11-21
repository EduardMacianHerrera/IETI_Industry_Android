package com.example.ieti_industry_android;

public class ToggleButton extends Control{
    String state;

    public ToggleButton(String state, int id, String label) {
        super(label, id);
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

