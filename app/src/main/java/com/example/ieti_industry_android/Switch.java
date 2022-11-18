package com.example.ieti_industry_android;

public class Switch extends Control{
    String state;

    public Switch(String state, int id, String label) {
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

