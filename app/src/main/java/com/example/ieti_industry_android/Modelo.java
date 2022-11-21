package com.example.ieti_industry_android;

import java.util.ArrayList;

public class Modelo {
    ArrayList<Block> blocks = new ArrayList<Block>();

    public Modelo(String s) {loadModel(s);}

    public void loadModel(String string) {

        String[] blockArray = string.split(";];");
        System.out.println(string);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (String blockString : blockArray) {
            blockString += ";";
            String blockName = blockString.substring(0, blockString.indexOf(":"));
            Block block = new Block(blockName);
            //System.out.println(block);
            String[] controlArray = blockString.substring(blockString.indexOf(":") + 2).split(";");
            for (String controlString : controlArray) {
                System.out.println(controlString);
                String controlType = controlString.substring(0, controlString.indexOf(":"));
                String[] attrs = controlString.substring(controlString.indexOf(":[") + 2).split(",");
                int id = 0;
                String label = null;
                switch (controlType) {
                    case "Switch":
                        String stateSwitch = null;
                        for (String attr : attrs) {
                            switch (attr.substring(0, attr.indexOf("="))) {
                                case "state":
                                    System.out.println(attr.substring(attr.indexOf("=" + 1)));
                                    stateSwitch = attr.substring(attr.indexOf("=" + 1));
                                    break;
                                case "id":
                                    id = Integer.parseInt(attr.substring(attr.indexOf("=") + 1));
                                    break;
                                case "label":
                                    label = attr.substring(attr.indexOf("=") + 1);
                                    break;
                            }
                        }
                        block.addSwitch(new ToggleButton(stateSwitch, id, label));
                        break;
                    case "Dropdown":
                        String stateDrop = null;
                        ArrayList<Option> options = new ArrayList<Option>();
                        for (String attr : attrs) {
                            switch (attr.substring(0, attr.indexOf("="))) {
                                case "label":
                                    label = attr.substring(attr.indexOf("=") + 1);
                                    break;
                                case "id":
                                    id = Integer.parseInt(attr.substring(attr.indexOf("=") + 1));
                                    break;
                                case "state":
                                    stateDrop = attr.substring(attr.indexOf("=") + 1);
                                    break;
                                case "options":
                                    String[] optionArray = attr.substring(attr.indexOf("=") + 1).split("\\$");
                                    for (String optionLabel : optionArray) {
                                        String[] optionAttrs = optionLabel.split("&");
                                        for (String optionAttr : optionAttrs) {
                                            String labelOption = null;
                                            String valueOption = null;
                                            switch (optionAttr.substring(0, optionAttr.indexOf("="))) {
                                                case "label":
                                                    labelOption = optionAttr.substring(optionAttr.indexOf("=") + 1);
                                                    break;
                                                case "value":
                                                    valueOption = optionAttr.substring(optionAttr.indexOf("=") + 1);
                                                    break;
                                            }
                                            options.add(new Option(labelOption, valueOption));
                                        }
                                    }
                                    break;
                            }
                        }
                        block.addDropdown(new Dropdown(label, id, stateDrop, options));
                        break;
                    case "Sensor":
                        String units = null;
                        int thresholdLow = 0;
                        int thresholdHigh = 0;
                        for (String attr : attrs) {
                            switch (attr.substring(0, attr.indexOf("="))) {
                                case "id":
                                    id = Integer.parseInt(attr.substring(attr.indexOf("=") + 1));
                                    break;
                                case "units":
                                    units = attr.substring(attr.indexOf("=") + 1);
                                    break;
                                case "label":
                                    label = attr.substring(attr.indexOf("=") + 1);
                                    break;
                                case "thresholdHigh":
                                    thresholdHigh = Integer.parseInt(attr.substring(attr.indexOf("=") + 1));
                                    break;
                                case "thresholdLow":
                                    thresholdLow = Integer.parseInt(attr.substring(attr.indexOf("=") + 1));
                                    break;
                            }
                        }
                        block.addSensor(new Sensor(id, units, thresholdHigh, thresholdLow, label));
                        break;
                    case "Slider":
                        int min = 0;
                        int stateSlider = 0;
                        int step = 0;
                        int max = 0;
                        for (String attr : attrs) {
                            switch (attr.substring(0, attr.indexOf("="))) {
                                case "state":
                                    stateSlider = Integer.parseInt(attr.substring(attr.indexOf("=") + 1));
                                    break;
                                case "id":
                                    id = Integer.parseInt(attr.substring(attr.indexOf("=") + 1));
                                    break;
                                case "label":
                                    label = attr.substring(attr.indexOf("=") + 1);
                                    break;
                                case "min":
                                    min = Integer.parseInt(attr.substring(attr.indexOf("=") + 1));
                                    break;
                                case "max":
                                    max = Integer.parseInt(attr.substring(attr.indexOf("=") + 1));
                                    break;
                                case "step":
                                    step = Integer.parseInt(attr.substring(attr.indexOf("=") + 1));
                                    break;
                            }
                        }
                        block.addSlider(new Slider(id, label, stateSlider, min, max, step));
                        break;
                }
            }
            blocks.add(block);
        }
    }
}
