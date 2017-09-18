package com.koanruler.mp.entity;

public class TerminalUseInfo {
	private int userid;
	private String username;   //用户名，分层显示，比如中心1/医院1/科室1
	private String patientname;
	private String patientage;
	private String patientsex;
	private String terminalNum;
	private String bindtime;

	public TerminalUseInfo(int userid, String username, String patientname, String patientage, String patientsex, String terminalNum, String bindtime) {
		this.userid = userid;
		this.username = username;
		this.patientname = patientname;
		this.patientage = patientage;
		this.patientsex = patientsex;
		this.terminalNum = terminalNum;
		this.bindtime = bindtime;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getPatientage() {
		return patientage;
	}
	public void setPatientage(String patientage) {
		this.patientage = patientage;
	}
	
	public String getPatientname() {
		return patientname;
	}
	public void setPatientname(String patientname) {
		this.patientname = patientname;
	}
	public String getPatientsex() {
		return patientsex;
	}
	public void setPatientsex(String patientsex) {
		this.patientsex = patientsex;
	}
	public String getTerminalNum() {
		return terminalNum;
	}
	public void setTerminalNum(String terminalNum) {
		this.terminalNum = terminalNum;
	}
	public String getBindtime() {
		return bindtime;
	}
	public void setBindtime(String bindtime) {
		this.bindtime = bindtime;
	}
}
