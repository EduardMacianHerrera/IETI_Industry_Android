package com.example.ieti_industry_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SliderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);
    }

    public com.google.android.material.slider.Slider createSlider(com.example.ieti_industry_android.Slider s) {
        com.google.android.material.slider.Slider slider = new com.google.android.material.slider.Slider(this);
        slider.setValueFrom(s.getMin());
        slider.setValueFrom(s.getMax());
        slider.setValue(s.getState());
        slider.setStepSize(s.getStep());
        return slider;
    }
}