package com.example.ieti_industry_android;

public class Sensor extends Control {
    String units;
    int thresholdHigh;
    int thresholdLow;

    public Sensor(int id, String units, int thresholdHigh, int thresholdLow, String label) {
        super(label, id);
        this.id = id;
        this.units = units;
        this.thresholdHigh = thresholdHigh;
        this.thresholdLow = thresholdLow;
        this.label = label;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public int getThresholdHigh() {
        return thresholdHigh;
    }

    public void setThresholdHigh(int thresholdHigh) {
        this.thresholdHigh = thresholdHigh;
    }

    public int getThresholdLow() {
        return thresholdLow;
    }

    public void setThresholdLow(int thresholdLow) {
        this.thresholdLow = thresholdLow;
    }
}
