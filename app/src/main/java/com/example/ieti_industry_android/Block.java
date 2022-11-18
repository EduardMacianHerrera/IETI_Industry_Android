package com.example.ieti_industry_android;



import java.util.ArrayList;

public class Block {
    String name;
    ArrayList<Switch> switches = new ArrayList<Switch>();
    ArrayList<Slider> sliders = new ArrayList<Slider>();
    ArrayList<Dropdown> dropdowns = new ArrayList<Dropdown>();


    public Block(String name) {this.name = name;}

    public void addSwitch(Switch s) {
        switches.add(s);
    }

    public void addSlider(Slider s) {
        sliders.add(s);
    }

    public void addDropdown(Dropdown s) {
        dropdowns.add(s);
    }
}
