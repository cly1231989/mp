package koanruler.entity;

import java.util.LinkedList;
import java.util.List;

public class PatientDataInfo {
	public SimplePatientInfo patientinfo;
	public List<DataIDAndFileName> datas;
	
	public SimplePatientInfo getPatientinfo() {
		return patientinfo;
	}
	
	public List<DataIDAndFileName> getDatas() {
		return datas;
	}
	
	public PatientDataInfo() {
		super();
		this.patientinfo = new SimplePatientInfo();
		this.datas = new LinkedList<DataIDAndFileName>();
	}
}
