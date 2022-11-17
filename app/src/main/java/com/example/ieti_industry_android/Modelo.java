package com.example.ieti_industry_android;

import java.util.ArrayList;

public class Modelo {
    static String a = "block1:[Switch:[state=on,id=0];Slider:[label=Label,id=1,state=4.5,min=0.0,step=0.5];" +
            "Dropdown:[label=Etiqueta,id=2,state=3,options=[label=Label 2,value=2;label=Label 3,value=3;];" +
            "Sensor:[id=3,units=ºC,thresholdHigh=10,thresholdLow=5,label=Label];];block2:[];";
    
    private ArrayList<Block> controles = new ArrayList<Block>();

    public static void main(String[] args) {
        
    }

    public Modelo() {
        super();
    }

    public void loadModel(String string) {
        String[] modelArray = a.split(";");
        for (String s : modelArray) {
            System.out.println(s);
        }
    }

}
