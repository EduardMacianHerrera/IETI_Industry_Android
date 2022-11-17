package com.example.ieti_industry_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.io.File;

public class ScreenControls extends AppCompatActivity {

    static Modelo modelo;
    static WsClient socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_controls);

        File dataFile = socket.getModel();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}