package com.koanruler.mp.entity;

public class DataSearchCondition {
	public String patientname;
	public String bednum;
	public int hospitalid;
	public int departmentid;
	public String begindate;
	public String enddate;
	public int type;
	public int state;
	public int patientcount;
	public int minseconds;
	public DataSearchCondition(String patientname, String bednum,
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
}
