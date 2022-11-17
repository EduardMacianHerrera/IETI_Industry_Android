package com.example.ieti_industry_android;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.slider.Slider;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XmlReader {
    public static void cargarConfig(File file, Modelo modelo, Context context) {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList listaBloques = doc.getElementsByTagName("controls");

            for (int i = 0; i < listaBloques.getLength(); i++) {
                Node nodeBloque = listaBloques.item(i);
                NodeList listaControles = nodeBloque.getChildNodes();
                for (int j = 0; j < listaControles.getLength(); j++) {
                    Node control = listaControles.item(j);
                    try {
                        if (control.getNodeType() == Node.ELEMENT_NODE) {
                            if (control.getNodeName().equals("dropdown")) {
                                crearDropDown(control, modelo, context);
                            } else {
                                addControlToModel(control, modelo, context);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("El elemento "+ control.getNodeName()+" no tiene el formato correcto");
                    }

                }
            }

        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            System.out.println("El formato del XML no es correcto");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void addControlToModel(Node control, Modelo modelo, Context context) {
        String tipoControl = control.getNodeName();
        String labelControl = control.getTextContent();
        Element elmControl = (Element) control;
        String id = elmControl.getAttribute("id");
        ControlElement controlTemp;
        try {
            controlTemp = crearControl(id, labelControl, tipoControl, elmControl, context);
            //modelo.getControles().add(controlTemp);
        } catch (Exception e) {
            System.out.println("Error de formato en un control "+control.getNodeName());
        }



    }

    public static ControlElement crearControl(String id, String labelControl, String tipoControl, Element elmControl, Context context) throws Exception {
        if (id.equals("") || labelControl.equals("") || tipoControl.equals("") || elmControl.equals("")) {
            throw new Exception();
        }
        ControlElement controlTemp = new ControlElement();
        controlTemp.setId(id);
        controlTemp.setLabel(labelControl);
        controlTemp.setTipoControl(tipoControl);
        switch (tipoControl) {
            case "switch":
                Switch interruptor = new Switch(context);

                if (elmControl.getAttribute("default").equals("on")) {
                    interruptor.setChecked(true);
                } else {
                    interruptor.setChecked(false);
                }

                controlTemp.setControl(interruptor);
                //modelo.getControles().add(controlTemp);
                break;

            case "slider":
                Slider slider = new Slider(context);
                slider.setValueFrom(Integer.valueOf(elmControl.getAttribute("min"))* 10);
                slider.setValueTo(Integer.valueOf(elmControl.getAttribute("max"))* 10);
                slider.setValue((int)(Double.valueOf(elmControl.getAttribute("default"))* 10));
                // Mirar lo de los tick y multiplicar por 10

                controlTemp.setControl(slider);

                break;


            case "sensor":
                TextView textField = new TextView(context);

                controlTemp.setUnits(elmControl.getAttribute("units"));
                controlTemp.setThresholdLow(Integer.valueOf(elmControl.getAttribute("thresholdlow")));
                controlTemp.setThresholdHigh(Integer.valueOf(elmControl.getAttribute("thresholdhigh")));
                controlTemp.setControl(textField);
                break;

            default:
                break;
        }

        return controlTemp;
    }

    public static void crearDropDown(Node control, Modelo modelo, Context context) {
        NodeList listaOpciones = control.getChildNodes();
        Element elmDropdown = (Element) control;
        Spinner spinner = new Spinner(context);
        ArrayList<String> labelsComboBox = new ArrayList<String>();
        ArrayList<String> valoresComboBox = new ArrayList<String>();

        for (int k = 0; k < listaOpciones.getLength(); k++) {
            Node opcion = listaOpciones.item(k);
            if (opcion.getNodeType() == Node.ELEMENT_NODE) {
                Element elmOpcion = (Element) opcion;
                labelsComboBox.add(opcion.getTextContent());
                valoresComboBox.add(elmOpcion.getAttribute("value"));
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, labelsComboBox);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //  saca el indice y selecciona la label por defecto
        ControlElement controlTemp = new ControlElement();
        controlTemp.setId(elmDropdown.getAttribute("id"));
        controlTemp.setControl(spinner);
        controlTemp.setLabelsComboBox(labelsComboBox);
        controlTemp.setValoresComboBox(valoresComboBox);
        controlTemp.setTipoControl("dropdown");

        //modelo.getControles().add(controlTemp);
    }

}
