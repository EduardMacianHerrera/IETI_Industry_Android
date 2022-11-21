package com.example.ieti_industry_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Switch;

public class SwitchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch);
    }

    public Switch createSwitch(ToggleButton t) {
        Switch s = new Switch(this);
        if (t.getState().equals("on")) {
            s.setChecked(true);
        }
        return s;
    }
}