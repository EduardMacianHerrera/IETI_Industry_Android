package com.example.ieti_industry_android;

import android.widget.Spinner;
import android.widget.Switch;

import com.google.android.material.slider.Slider;

import java.util.ArrayList;

public class Block {
    ArrayList<Switch> switches = new ArrayList<Switch>();
    ArrayList<Slider> sliders = new ArrayList<Slider>();
    ArrayList<Spinner> dropdowns = new ArrayList<Spinner>();

    public Block() {}

    public void addSwitch(Switch s) {
        switches.add(s);
    }

    public void addSlider(Slider s) {
        sliders.add(s);
    }

    public void addDropdowns(Spinner s) {
        dropdowns.add(s);
    }
}
