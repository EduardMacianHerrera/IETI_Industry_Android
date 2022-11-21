package com.example.ieti_industry_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class SensorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
    }

    public TextView createSensor(Sensor s) {
        TextView t = new TextView(this);
        t.setText(s.getThresholdLow() + " " + s.getUnits());
        return t;
    }
}