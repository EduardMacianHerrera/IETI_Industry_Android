package com.example.ieti_industry_android;

public class Option {
    String label;
    String value;

    public Option(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toString() {
        return "label=" + label + ",value=" + value;
    }

}

