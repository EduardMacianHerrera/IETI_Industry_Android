package com.example.ieti_industry_android;

public class Slider extends Control{
    int state;
    int min;
    int max;
    int step;

    public Slider(int id, String label, int state, int min, int max, int step) {
        super(label, id);
        this.state = state;
        this.min = min;
        this.max = max;
        this.step = step;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

}
