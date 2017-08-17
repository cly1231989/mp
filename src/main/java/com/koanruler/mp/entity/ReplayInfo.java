package com.koanruler.mp.entity;


public class ReplayInfo {
	public Data data = new Data();
	public Patient patient = new Patient();
	
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
}
