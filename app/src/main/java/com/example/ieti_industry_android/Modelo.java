package com.example.ieti_industry_android;

import java.util.ArrayList;

public class Modelo {
    private ArrayList<ControlElement> controles = new ArrayList<ControlElement>();


    public Modelo() {
        super();
    }

    public ArrayList<ControlElement> getControles() {
        return controles;
    }
    public void setControles(ArrayList<ControlElement> controles) {
        this.controles = controles;
    }
}
