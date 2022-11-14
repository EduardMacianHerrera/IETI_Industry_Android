package com.example.ieti_industry_android;

import java.util.ArrayList;

public class ControlElement {

	// Atributos generales
	private Object control;
	private String id;
	private String label;
	private String tipoControl;
	
	// Atributos para los dropdowns
	private ArrayList<String> labelsComboBox;
	private ArrayList<String> valoresComboBox;
	
	// Atributos para los sensores
	private String units;
	private int thresholdLow;
	private int thresholdHigh;

	public ControlElement() {
		super();
	}

	public ControlElement(String id, String label, String tipoControl, Object control) {
		super();
		this.id = id;
		this.label = label;
		this.tipoControl = tipoControl;
		this.control = control;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Object getControl() {
		return control;
	}

	public void setControl(Object control) {
		this.control = control;
	}

	public String getTipoControl() {
		return tipoControl;
	}

	public void setTipoControl(String tipoControl) {
		this.tipoControl = tipoControl;
	}

	public ArrayList<String> getLabelsComboBox() {
		return labelsComboBox;
	}

	public void setLabelsComboBox(ArrayList<String> labelsComboBox) {
		this.labelsComboBox = labelsComboBox;
	}

	public ArrayList<String> getValoresComboBox() {
		return valoresComboBox;
	}

	public void setValoresComboBox(ArrayList<String> valoresComboBox) {
		this.valoresComboBox = valoresComboBox;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public int getThresholdLow() {
		return thresholdLow;
	}

	public void setThresholdLow(int thresholdLow) {
		this.thresholdLow = thresholdLow;
	}

	public int getThresholdHigh() {
		return thresholdHigh;
	}

	public void setThresholdHigh(int thresholdHigh) {
		this.thresholdHigh = thresholdHigh;
	}

	
	

}
