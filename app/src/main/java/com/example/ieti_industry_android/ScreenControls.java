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
    static boolean flag = false;



    ArrayAdapter<ToggleButton> adapterSwitch;
    ArrayAdapter<Dropdown> adapterSpinner;
    ArrayAdapter<com.example.ieti_industry_android.Slider> adapterSlider;
    ArrayAdapter<Sensor> adapterSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_controls);

        TextView titol = findViewById(R.id.blockName);
        titol.setText(modelo.getBlocks().get(blocknum).getName());
        titol.setAllCaps(true);

        WsClient.currentActivity = this;

        createSwitchTable();
        createSpinnerTable();
        createSliderTable();
        createSensorTable();


        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
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
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
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
        });
    }

    public void createSwitchTable() {
        ListView list = findViewById(R.id.switchList);
        adapterSwitch = new ArrayAdapter<ToggleButton>(this, R.layout.switch_layout, modelo.getBlocks().get(blocknum).getToggleButtons()) {
            @Override
            public View getView(int pos, View convertView, ViewGroup container) {
                // getView ens construeix el layout i hi "pinta" els valors de l'element en la posici?? pos
                if (convertView == null) {
                    // inicialitzem l'element la View amb el seu layout
                    convertView = getLayoutInflater().inflate(R.layout.switch_layout, container, false);
                }
                // "Pintem" valors (tamb?? quan es refresca)
                ((TextView) convertView.findViewById(R.id.label)).setText(getItem(pos).getLabel());
                Switch s = ((Switch) convertView.findViewById(R.id.toggleButton));
                if (getItem(pos).getState().equalsIgnoreCase("on")) {
                    s.setChecked(true);
                } else {
                    s.setChecked(false);
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
                // getView ens construeix el layout i hi "pinta" els valors de l'element en la posici?? pos
                if (convertView == null) {
                    // inicialitzem l'element la View amb el seu layout
                    convertView = getLayoutInflater().inflate(R.layout.spinner_layout, container, false);
                }
                // "Pintem" valors (tamb?? quan es refresca)
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
                        if (flag){
                            String state = String.valueOf(s.getSelectedItemPosition());
                            String[] values = {modelo.getBlocks().get(blocknum).getName(), String.valueOf(getItem(pos).getId()), "dropdown", state};
                            socket.change(values);
                            flag = false;
                        } else {
                            flag = true;
                        }
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
                // getView ens construeix el layout i hi "pinta" els valors de l'element en la posici?? pos
                if (convertView == null) {
                    // inicialitzem l'element la View amb el seu layout
                    convertView = getLayoutInflater().inflate(R.layout.slider_layout, container, false);
                }
                // "Pintem" valors (tamb?? quan es refresca)
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
                // getView ens construeix el layout i hi "pinta" els valors de l'element en la posici?? pos
                if (convertView == null) {
                    // inicialitzem l'element la View amb el seu layout
                    convertView = getLayoutInflater().inflate(R.layout.sensor_layout, container, false);
                }
                // "Pintem" valors (tamb?? quan es refresca)
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
                                        adapterSlider.notifyDataSetChanged();
                                    }
                                });
                            }
                        }
                        break;

                    case "dropdown":
                        for (Dropdown d : b.getDropdowns()) {
                            if (id.equals(String.valueOf(d.getId()))) {
                                if (flag){
                                    d.setState(value);
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            adapterSpinner.notifyDataSetChanged();
                                        }
                                    });
                                    flag = false;
                                } else {
                                    flag = true;
                                }
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