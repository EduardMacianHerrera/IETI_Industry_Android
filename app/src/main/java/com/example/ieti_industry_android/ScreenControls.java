package com.example.ieti_industry_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.slider.Slider;

import java.util.ArrayList;

public class ScreenControls extends AppCompatActivity {

    static Modelo modelo;
    static WsClient socket;
    static int blocknum;


    ArrayAdapter<ToggleButton> adapterSwitch;
    ArrayAdapter<Dropdown> adapterSpinner;
    ArrayAdapter<com.example.ieti_industry_android.Slider> adapterSlider;
    ArrayAdapter<Sensor> adapterSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_controls);

        System.out.println("blocks" + modelo.getBlocks().size());


        WsClient.currentActivity = this;

        createSwitchTable();
        createSpinnerTable();
        createSliderTable();
        createSensorTable();


        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("a");
                modelo.blocks.get(0).getToggleButtons().get(0).setState("on");
                adapterSwitch.notifyDataSetChanged();
                System.out.println(modelo.blocks.get(0).getToggleButtons().get(0).getState());
            }
        });

        Button prevButton = findViewById(R.id.prevBlockButton);
        if (blocknum == 0) {
            prevButton.setVisibility(View.GONE);
        } else {
            prevButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ScreenControls.socket = MainActivity.socket;
                    blocknum--;
                    Intent intent = new Intent(ScreenControls.this, ScreenControls.class);
                    startActivity(intent);
                }
            });
        }

        Button nextButton = findViewById(R.id.nextBlockButton);
        if (blocknum == modelo.getBlocks().size() - 1) {
            nextButton.setVisibility(View.GONE);
        } else {
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ScreenControls.socket = MainActivity.socket;
                    blocknum++;
                    Intent intent = new Intent(ScreenControls.this, ScreenControls.class);
                    startActivity(intent);
                }
            });
        }

    }


    public void logout() {
        Intent intent = new Intent(ScreenControls.this, MainActivity.class);
        MainActivity.socket = ScreenControls.socket;
        socket.client.close();
        startActivity(intent);
    }

    public void connectionLost() {
        AlertDialog.Builder popup = new AlertDialog.Builder(ScreenControls.this);
        popup.setTitle("T'has desconectat del servidor. \nRetornant a la pantalla d'inici.");
        popup.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(ScreenControls.this, MainActivity.class);
                MainActivity.socket = ScreenControls.socket;
                startActivity(intent);
            }
        });
        popup.create();
        popup.show();
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
        slider.setScrollBarSize(50);
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

    public void createSwitchTable() {
        ListView list = findViewById(R.id.switchList);
        adapterSwitch = new ArrayAdapter<ToggleButton>(this, R.layout.switch_layout, modelo.getBlocks().get(blocknum).getToggleButtons()) {
            @Override
            public View getView(int pos, View convertView, ViewGroup container) {
                // getView ens construeix el layout i hi "pinta" els valors de l'element en la posició pos
                if (convertView == null) {
                    // inicialitzem l'element la View amb el seu layout
                    convertView = getLayoutInflater().inflate(R.layout.switch_layout, container, false);
                }
                // "Pintem" valors (també quan es refresca)
                ((TextView) convertView.findViewById(R.id.label)).setText(getItem(pos).getLabel());
                Switch s = ((Switch) convertView.findViewById(R.id.toggleButton));
                if (getItem(pos).getState().equalsIgnoreCase("on")) {
                    s.setChecked(true);
                }
                s.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String state = "";
                        if (s.isChecked()) {
                            state = "on";
                        } else {
                            state = "off";
                        }
                        String[] values = {modelo.getBlocks().get(blocknum).getName(), String.valueOf(modelo.getBlocks().get(blocknum).getToggleButtons().get(pos).getId()), "switch", state};
                        socket.change(values);
                    }
                });
                return convertView;
            }
        };
        list.setAdapter(adapterSwitch);

    }

    public void createSpinnerTable() {
        ListView list = findViewById(R.id.spinnerList);
        adapterSpinner = new ArrayAdapter<Dropdown>(this, R.layout.switch_layout, modelo.getBlocks().get(blocknum).getDropdowns()) {
            @Override
            public View getView(int pos, View convertView, ViewGroup container) {
                // getView ens construeix el layout i hi "pinta" els valors de l'element en la posició pos
                if (convertView == null) {
                    // inicialitzem l'element la View amb el seu layout
                    convertView = getLayoutInflater().inflate(R.layout.spinner_layout, container, false);
                }
                // "Pintem" valors (també quan es refresca)
                ((TextView) convertView.findViewById(R.id.label)).setText(getItem(pos).getLabel());
                Spinner s = ((Spinner) convertView.findViewById(R.id.spinner));
                ArrayList<String> listOptions = new ArrayList<String>();
                int index = 0;
                for (int i = 0; i < getItem(pos).getoptions().size(); i++) {
                    Option o = getItem(pos).getoptions().get(i);
                    listOptions.add(o.getLabel());
                    if (o.getValue().equals(getItem(pos).getState())) {
                        index = i;
                    }
                }
                ArrayAdapter<String> adapterOption = new ArrayAdapter<String>(ScreenControls.this,
                        android.R.layout.simple_spinner_item, listOptions);
                s.setAdapter(adapterOption);
                s.setSelection(index);
                s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String state = String.valueOf(s.getSelectedItemPosition());
                        String[] values = {modelo.getBlocks().get(blocknum).getName(), String.valueOf(getItem(pos).getId()), "dropdown", state};
                        socket.change(values);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                return convertView;
            }
        };
        list.setAdapter(adapterSpinner);
    }

    public void createSliderTable() {
        ListView list = findViewById(R.id.sliderList);

        adapterSlider = new ArrayAdapter<com.example.ieti_industry_android.Slider>(this, R.layout.switch_layout, modelo.getBlocks().get(blocknum).getSliders()) {
            @Override
            public View getView(int pos, View convertView, ViewGroup container) {
                // getView ens construeix el layout i hi "pinta" els valors de l'element en la posició pos
                if (convertView == null) {
                    // inicialitzem l'element la View amb el seu layout
                    convertView = getLayoutInflater().inflate(R.layout.slider_layout, container, false);
                }
                // "Pintem" valors (també quan es refresca)
                ((TextView) convertView.findViewById(R.id.label)).setText(getItem(pos).getLabel());
                Slider s = ((Slider) convertView.findViewById(R.id.slider));
                s.setStepSize(getItem(pos).getStep());
                s.setValueFrom(getItem(pos).getMin());
                s.setValueTo(getItem(pos).getMax());
                s.setValue(getItem(pos).getState());
//                s.addOnChangeListener(new Slider.OnChangeListener() {
//                    @Override
//                    public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
//
//                    }
//                });
                s.addOnSliderTouchListener(new Slider.OnSliderTouchListener(){

                    @Override
                    public void onStartTrackingTouch(@NonNull Slider slider) {

                    }

                    @Override
                    public void onStopTrackingTouch(@NonNull Slider slider) {
                        String state = String.valueOf(Math.round(slider.getValue()));
                        String[] values = {modelo.getBlocks().get(blocknum).getName(),String.valueOf(getItem(pos).getId()),"slider",state};
                        socket.change(values);
                    }
                });
                return convertView;
            }
        };
        list.setAdapter(adapterSlider);
    }


    public void createSensorTable() {
        ListView list = findViewById(R.id.sensorList);
        adapterSensor = new ArrayAdapter<Sensor>(this, R.layout.sensor_layout, modelo.getBlocks().get(blocknum).getSensors()) {
            @Override
            public View getView(int pos, View convertView, ViewGroup container) {
                // getView ens construeix el layout i hi "pinta" els valors de l'element en la posició pos
                if (convertView == null) {
                    // inicialitzem l'element la View amb el seu layout
                    convertView = getLayoutInflater().inflate(R.layout.sensor_layout, container, false);
                }
                // "Pintem" valors (també quan es refresca)
                ((TextView) convertView.findViewById(R.id.label)).setText(getItem(pos).getLabel());
                ((TextView) convertView.findViewById(R.id.sensor)).setText(String.valueOf(getItem(pos).getThresholdLow()));
                return convertView;
            }
        };
        list.setAdapter(adapterSensor);
    }

    //[block1,2,slider,2]
    public void updateInterfaz(String[] arrays) {
        Handler handler = new Handler(Looper.getMainLooper());
        System.out.println("Iniciando cambio");
        String nameBlock = arrays[0];
        String id = arrays[1];
        String type = arrays[2];
        String value = arrays[3];
        for (String s : arrays) {
            System.out.println(s);
        }
        for (Block b : modelo.getBlocks()) {
            if (b.getName().equals(nameBlock)) {
                switch (type) {
                    case "switch":
                        for (ToggleButton t : b.getToggleButtons()) {
                            if (id.equals(String.valueOf(t.getId()))) {
                                t.setState(value);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        System.out.println("Notificiacion");
                                        adapterSwitch.notifyDataSetChanged();
                                    }
                                });
                            }
                        }
                        break;

                    case "slider":
                        for (com.example.ieti_industry_android.Slider s : b.getSliders()) {
                            if (id.equals(String.valueOf(s.getId()))) {
                                s.setState(Integer.parseInt(value));
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        System.out.println("Notificiacion");
                                        adapterSlider.notifyDataSetChanged();
                                    }
                                });
                            }
                        }
                        break;

                    case "dropdown":
                        for (Dropdown d : b.getDropdowns()) {
                            if (id.equals(String.valueOf(d.getId()))) {
                                d.setState(value);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        System.out.println("Notificiacion");
                                        adapterSpinner.notifyDataSetChanged();
                                    }
                                });
                            }
                        }
                        break;

                    case "sensor":
                        break;

                    default:
                        break;
                }
            }
        }
    }


}