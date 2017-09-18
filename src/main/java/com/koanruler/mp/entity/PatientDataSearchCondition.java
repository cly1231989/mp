package com.koanruler.mp.entity;

public class PatientDataSearchCondition {
	public String patientname = "";
	public String bednum = "";
	public int hospitalid = 0;
	public int departmentid = 0;
	public String begindate = "";
	public String enddate = "";
	public int type;   //数据类型，搜索时不考虑它，如果没有相应类型的数据，在界面上提示用户，无数据。
	public int state;	//处理状态
	public int patientcount;	//病人数量
	public int minseconds;

	public PatientDataSearchCondition() {
	}

	public PatientDataSearchCondition(String patientname, String bednum,
									  int hospitalid, int departmentid, String begindate, String enddate,
									  int type, int state, int patientcount, int minseconds) {
		super();
		this.patientname = patientname;
		this.bednum = bednum;
		this.hospitalid = hospitalid;
		this.departmentid = departmentid;
		this.begindate = begindate;
		this.enddate = enddate;
		this.type = type;
		this.state = state;
		this.patientcount = patientcount;
		this.minseconds = minseconds;
	}

	public String getPatientname() {
		return patientname;
	}

	public void setPatientname(String patientname) {
		this.patientname = patientname;
	}

	public String getBednum() {
		return bednum;
	}

	public void setBednum(String bednum) {
		this.bednum = bednum;
	}

	public int getHospitalid() {
		return hospitalid;
	}

	public void setHospitalid(int hospitalid) {
		this.hospitalid = hospitalid;
	}

	public int getDepartmentid() {
		return departmentid;
	}

	public void setDepartmentid(int departmentid) {
		this.departmentid = departmentid;
	}

	public String getBegindate() {
		return begindate;
	}

	public void setBegindate(String begindate) {
		this.begindate = begindate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getPatientcount() {
		return patientcount;
	}

	public void setPatientcount(int patientcount) {
		this.patientcount = patientcount;
	}

	public int getMinseconds() {
		return minseconds;
	}

	public void setMinseconds(int minseconds) {
		this.minseconds = minseconds;
	}
}
