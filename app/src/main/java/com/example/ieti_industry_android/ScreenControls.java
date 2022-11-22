package com.example.ieti_industry_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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

        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
        Switch s = findViewById(R.id.switchprueba);


        WsClient.currentActivity = this;

        // CREATE TABLE
        TableLayout tableLayout = tableLayout();

        /*for(int x = 0 ; x < 3 ; x ++){

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
        }*/

//        ScrollView contentView = new ScrollView(this);
//        contentView.setBackgroundColor(Color.LTGRAY);
//
//        // THIS IS OUR MAIN LAYOUT
//        LinearLayout mainLayout = new LinearLayout(this);
//        mainLayout.setOrientation(LinearLayout.VERTICAL);
//
//        // ADD MAINLAYOUT TO SCROLLVIEW (contentView)
//
//        contentView.addView(mainLayout);
//
//        // SET CONTENT VIEW
//
//        setContentView(contentView);
//
//        TableLayout tableLayoutProgrammatically = tableLayout;
//
//        mainLayout.addView(tableLayoutProgrammatically);

        View contentView = tableLayout;
        setContentView(contentView);
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
        s.setId(t.getId());
        if (t.getState().equals("on")) {
            s.setChecked(true);
        }
        return s;
    }

    public Slider createSlider(com.example.ieti_industry_android.Slider s) {
        Slider slider = new Slider(this);
        slider.setValueFrom(s.getMin());
        slider.setValueTo(s.getMax());
        slider.setValue(s.getState());
        slider.setStepSize(s.getStep());
        slider.setId(s.getId());
        return slider;
    }

    public Spinner createSpinner(Dropdown d) {
        Spinner spinner = new Spinner(this);
        ArrayList<String> listOptions = new ArrayList<String>();
        int index = 0;
        for (int i = 0; i < d.getoptions().size(); i++) {
            Option o = d.getoptions().get(i);
            listOptions.add(o.getLabel());
            System.out.println("a" + o.getValue());
            System.out.println("b" + d.getState());
            if (o.getValue().equals(d.getState())) {
                index = i;
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listOptions);
        spinner.setAdapter(adapter);
        spinner.setSelection(index);
        spinner.setId(d.getId());
        return spinner;
    }

    public TextView createSensor(Sensor s) {
        TextView t = new TextView(this);
        t.setText(s.getThresholdLow() + " " + s.getUnits());
        return t;
    }

    public TableLayout createSwitchTable() {
        TableRow.LayoutParams params1 = new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 1.0f);
        TableRow.LayoutParams params2 = new TableRow.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        TableLayout table = new TableLayout(this);
        System.out.println("Cantidad de bloques: "+modelo.blocks.size());
        for (ToggleButton t : modelo.blocks.get(0).getToggleButtons()) {

            TableRow row = new TableRow(this);
            TextView textLabel = new TextView(this);
            Switch s = createSwitch(t);

            s.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String state = "";
                    if (s.isChecked()){
                        state = "on";
                    } else {
                        state = "off";
                    }
                    String[] values = {"block1",String.valueOf(s.getId()),"switch",state};
                    socket.change(values);
                }
            });

            textLabel.setText(t.getLabel());
            textLabel.setLayoutParams(params1);
            s.setLayoutParams(params1);

            row.addView(textLabel);
            row.addView(s);
            row.setLayoutParams(params2);
            table.addView(row);
        }
        return table;
    }

    public TableLayout createSpinnerTable() {
        TableRow.LayoutParams params1 = new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 1.0f);
        TableRow.LayoutParams params2 = new TableRow.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        TableLayout table = new TableLayout(this);
        System.out.println("Cantidad de bloques: "+modelo.blocks.size());
        for (Dropdown d : modelo.blocks.get(0).getDropdowns()) {

            TableRow row = new TableRow(this);
            TextView textLabel = new TextView(this);
            Spinner s = createSpinner(d);

            s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String state = String.valueOf(s.getSelectedItemPosition());
                    String[] values = {"block1",String.valueOf(s.getId()),"dropdown",state};
                    socket.change(values);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            textLabel.setText(d.getLabel());
            textLabel.setLayoutParams(params1);
            s.setLayoutParams(params1);

            row.addView(textLabel);
            row.addView(s);
            row.setLayoutParams(params2);
            table.addView(row);
        }
        return table;
    }

    public TableLayout createSliderTable() {
        TableRow.LayoutParams params1 = new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 1.0f);
        TableRow.LayoutParams params2 = new TableRow.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        TableLayout table = new TableLayout(this);
        System.out.println("Cantidad de bloques: "+modelo.blocks.size());
        for (com.example.ieti_industry_android.Slider s : modelo.blocks.get(0).getSliders()) {
            TableRow row = new TableRow(this);
            TextView textLabel = new TextView(this);
            Slider slider = createSlider(s);

            slider.addOnChangeListener(new Slider.OnChangeListener() {
                @Override
                public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                    String state = String.valueOf(Math.round(slider.getValue()));
                    String[] values = {"block1",String.valueOf(slider.getId()),"slider",state};
                    socket.change(values);
                }
            });

            textLabel.setText(s.getLabel());
            textLabel.setLayoutParams(params1);
            slider.setLayoutParams(params1);

            row.addView(textLabel);
            row.addView(slider);
            row.setLayoutParams(params2);
            table.addView(row);
        }
        return table;
    }

    public TableLayout createSensorTable() {
        TableRow.LayoutParams params1 = new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 1.0f);
        TableRow.LayoutParams params2 = new TableRow.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        TableLayout table = new TableLayout(this);
        System.out.println("Cantidad de bloques: "+modelo.blocks.size());
        for (Sensor s : modelo.blocks.get(0).getSensors()) {
            TableRow row = new TableRow(this);
            TextView textLabel = new TextView(this);
            TextView sensor = createSensor(s);

            textLabel.setText(s.getLabel());
            textLabel.setLayoutParams(params1);
            sensor.setLayoutParams(params1);

            row.addView(textLabel);
            row.addView(sensor);
            row.setLayoutParams(params2);
            table.addView(row);
        }
        return table;
    }

    public TableLayout tableLayout(){

        // CREATE TABLE
        TableLayout tableLayout = new TableLayout(this);

        // CREATE TABLE ROW
        TableRow tableRow = new TableRow(this);

        // CREATE PARAM FOR MARGINING
        TableRow.LayoutParams aParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT);
        aParams.topMargin = 2;
        aParams.rightMargin = 2;
        // ADD TABLEROW TO TABLELAYOUT

        tableLayout.addView(tableRow);

        tableRow.addView(createSwitchTable());
        tableRow.addView(createSpinnerTable());

        // CREATE TABLE ROW
        tableRow = new TableRow(this);
        // ADD TABLEROW TO TABLELAYOUT
        tableRow.addView(createSliderTable());
        tableRow.addView(createSensorTable());

        tableLayout.addView(tableRow);

        return tableLayout;
    }
}