package com.example.ieti_industry_android;

import java.util.ArrayList;

public class Modelo {
    static String a = "block1:[" +
            "Switch:[state=on,id=0,label=label];" +
            "Slider:[label=Label,id=1,state=4,min=0,step=0];" +
            "Dropdown:[label=Etiqueta,id=2,state=3,options=[label=Label 2&value=2$label=Label 3&value=3&];" +
            "Sensor:[id=3,units=ºC,thresholdHigh=10,thresholdLow=5,label=Label];];" +
            "block2:[" +
            "Switch:[state=on,id=0];" +
            "Slider:[label=Label,id=1,state=4,min=0,step=0];" +
            "Dropdown:[label=Etiqueta,id=2,state=3,options=[label=Label 2&value=2$label=Label 3&value=3&];" +
            "Sensor:[id=3,units=ºC,thresholdHigh=10,thresholdLow=5,label=Label];];";

    public static void main(String[] args) {
        loadModel(a);
    }

    public static void loadModel(String string) {

        String[] blockArray = string.split(";];");
        for (String blockString : blockArray) {
            blockString += ";";
            String blockName = blockString.substring(0, blockString.indexOf(":"));
            Block block = new Block(blockName);
            //System.out.println(block);
            String[] controlArray = blockString.substring(blockString.indexOf(":") + 2).split(";");
            for (String controlString : controlArray) {
                //System.out.println(control);
                String controlType = controlString.substring(0, controlString.indexOf(":"));
                String[] attrs = controlString.substring(controlString.indexOf(":[") + 2).split(",");
                switch (controlType) {
                    String state;
                    int id;
                    String label;
                    case "Switch":
                        state = null;
                        id = 0;
                        label = null;
                        for (String attr : attrs) {
                            switch (attr.substring(0, attr.indexOf("="))) {
                                case "state":
                                    state = attr.substring(attr.indexOf("="+ 1));
                                case "id":
                                    id = Integer.parseInt(attr.substring(attr.indexOf("=")+1));
                                case "label":
                                    label = attr.substring(attr.indexOf("=")+1);
                            }
                        }
                        block.addSwitch(new Switch(state, id, label));
                        break;
                    case "Dropdown":
                        for (String attr : attrs) {
                            state = null;
                            id = 0;
                            label = null;
                            if (attr.substring(0, attr.indexOf("=")).equals("label")) {
                                label = attr.substring(attr.indexOf("=") + 1);
                            } else if (attr.substring(0, attr.indexOf("=")).equals("id")) {
                                id = Integer.parseInt(attr.substring(attr.indexOf("=") + 1));
                            } else if (attr.substring(0, attr.indexOf("=")).equals("state")) {
                                state = attr.substring(attr.indexOf("=") + 1);
                            } else if (attr.substring(0, attr.indexOf("=")).equals("options")) {
                                String[] optionArray = attr.substring(attr.indexOf("=") + 1).split("\\$");
                                for (String optionLabel : optionArray) {
                                    String[] optionAttrs = optionLabel.split("&");
                                    for (String optionAttr : optionAttrs) {
                                        String labelOption = null;
                                        String valueOption = null;
                                        switch (optionAttr.substring(0, optionAttr.indexOf("="))) {
                                            case "label":

                                        }
                                    }
                                }

                            }
                        }
                        break;
                    case "Sensor":
                        for (String attr : attrs) {

                        }
                        break;
                    case "Slider":
                        System.out.println(4);
                        break;
                }
            }
        }
    }
}
