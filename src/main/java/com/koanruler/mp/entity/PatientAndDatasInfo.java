package com.koanruler.mp.entity;

import java.util.List;

public class PatientAndDatasInfo {	
	SimplePatientInfo patientinfo;
	//private String state;
	
	public SimplePatientInfo getPatientinfo() {
		return patientinfo;
	}
	public void setPatientinfo(SimplePatientInfo patientinfo) {
		this.patientinfo = patientinfo;
	}
	//private List<DataIDAndFileName> datas;
	//private List<DataState> datastate;
	private List<Data> datas;
	
	
	public List<Data> getDatas() {
		return datas;
	}
	public void setDatas(List<Data> datas) {
		this.datas = datas;
	}
	
}
