package com.example.ieti_industry_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.material.slider.Slider;

import java.io.File;
import java.util.ArrayList;

public class ScreenControls extends AppCompatActivity {

    static Modelo modelo;
    static WsClient socket;
    static AlertDialog.Builder popup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_controls);

        WsClient.currentActivity = this;

        // CREATE TABLE
        TableLayout tableLayout = new TableLayout(this);

        for(int x = 0 ; x < 3 ; x ++){

            // CREATE TABLE ROW
            TableRow tableRow = new TableRow(this);

            // CREATE PARAM FOR MARGINING
            TableRow.LayoutParams aParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            aParams.topMargin = 2;
            aParams.rightMargin = 2;

            TableRow.LayoutParams bParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            bParams.topMargin = 2;
            bParams.rightMargin = 2;

            // SET THE SPAN IF x == 2
            bParams.span = x==2 ? 2 : 1;

            TableRow.LayoutParams cParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            cParams.topMargin = 2;
            cParams.rightMargin = 2;

            TableRow.LayoutParams dParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            dParams.topMargin = 2;
            dParams.rightMargin = 2;


            // CREATE TEXTVIEW

            TextView a = new TextView(this);
            TextView b = new TextView(this);
            TextView c = new TextView(this);
            TextView d = new TextView(this);

            // SET PARAMS

            a.setLayoutParams(aParams);
            b.setLayoutParams(bParams);
            c.setLayoutParams(cParams);
            d.setLayoutParams(dParams);

            // SET BACKGROUND COLOR

            a.setBackgroundColor(Color.WHITE);
            b.setBackgroundColor(Color.WHITE);
            c.setBackgroundColor(Color.WHITE);
            d.setBackgroundColor(Color.WHITE);

            // SET PADDING

            a.setPadding(20, 20, 20, 20);
            b.setPadding(20, 20, 20, 20);
            c.setPadding(20, 20, 20, 20);
            d.setPadding(20, 20, 20, 20);

            // SET TEXTVIEW TEXT

            a.setText("A"+ (x+1));
            b.setText("B"+ (x+1));
            c.setText("C"+ (x+1));
            d.setText("D"+ (x+1));

            // ADD TEXTVIEW TO TABLEROW

            tableRow.addView(a);
            tableRow.addView(b);
            tableRow.addView(c);
            tableRow.addView(d);

            // ADD TABLEROW TO TABLELAYOUT

            tableLayout.addView(tableRow);
        }

        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });


    }

    public void logout() {
        Intent intent = new Intent(ScreenControls.this, MainActivity.class);
        MainActivity.socket = ScreenControls.socket;
        socket.client.close();
        startActivity(intent);
    }

    public void connectionLost() {
        Intent intent = new Intent(ScreenControls.this, MainActivity.class);
        MainActivity.socket = ScreenControls.socket;
        startActivity(intent);
    }

    public Switch createSwitch(ToggleButton t) {
        Switch s = new Switch(this);
        if (t.getState().equals("on")) {
            s.setChecked(true);
        }
        return s;
    }

    public Slider createSlider(com.example.ieti_industry_android.Slider s) {
        Slider slider = new Slider(this);
        slider.setValueFrom(s.getMin());
        slider.setValueFrom(s.getMax());
        slider.setValue(s.getState());
        slider.setStepSize(s.getStep());
        return slider;
    }

    public Spinner createSpinner(Dropdown d) {
        Spinner spinner = new Spinner(this);
        ArrayList<String> listOptions = new ArrayList<String>();
        int index = 0;
        for (int i = 0; i < d.getoptions().size(); i++) {
            Option o = d.getoptions().get(i);
            listOptions.add(o.getLabel());
            if (o.getValue().equals(d.getState())) {
                index = i;
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listOptions);
        spinner.setAdapter(adapter);
        spinner.setSelection(index);
        return spinner;
    }
}