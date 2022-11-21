package com.example.ieti_industry_android;



import java.util.ArrayList;

public class Block {
    String name;
    ArrayList<ToggleButton> toggleButtons = new ArrayList<ToggleButton>();
    ArrayList<Slider> sliders = new ArrayList<Slider>();
    ArrayList<Dropdown> dropdowns = new ArrayList<Dropdown>();
    ArrayList<Sensor> sensors = new ArrayList<Sensor>();



    public Block(String name) {this.name = name;}

    public void addSwitch(ToggleButton s) {
        toggleButtons.add(s);
    }

    public void addSlider(Slider s) {
        sliders.add(s);
    }

    public void addDropdown(Dropdown s) {
        dropdowns.add(s);
    }

    public void addSensor(Sensor s) { sensors.add(s); }

    public String getName() {
        return name;
    }

    public ArrayList<ToggleButton> getToggleButtons() {
        return toggleButtons;
    }

    public ArrayList<Slider> getSliders() {
        return sliders;
    }

    public ArrayList<Dropdown> getDropdowns() {
        return dropdowns;
    }

    public ArrayList<Sensor> getSensors() {
        return sensors;
    }
}
