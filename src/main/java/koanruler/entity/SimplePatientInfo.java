package koanruler.entity;

public class SimplePatientInfo {
	private int id;
	private String name;
	private String age;
	private String sex;
	private String bednum;
	private String birthday;
	private String outpatientnumber;
	private String hospitalnumber;
	private String hospital;
	private String department;
	private String symptom;
	private String handlebegintime;
	private String begintime;  			//recordbegintime
	private String endtime;				//recordendtime
	private int handleUserID;
	private int handleState;
	private String state;

	public SimplePatientInfo() {
	}

	public SimplePatientInfo(int id, String name, String age, String sex, String bednum, String birthday, String outpatientnumber, String hospitalnumber, String hospital, String department, String symptom, String handlebegintime, String begintime, String endtime, Integer handleUserID, Integer handleState, String state) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.sex = sex;
		this.bednum = bednum;
		this.birthday = birthday;
		this.outpatientnumber = outpatientnumber;
		this.hospitalnumber = hospitalnumber;
		this.hospital = hospital;
		this.department = department;
		this.symptom = symptom;
		this.handlebegintime = handlebegintime;
		this.begintime = begintime;
		this.endtime = endtime;
		this.handleUserID = handleUserID == null ? 0 : handleUserID;
		this.handleState = handleState == null ? 0 : handleState;
		this.state = state;
	}

	public int getHandleUserID() {
		return handleUserID;
	}
	public void setHandleUserID(int handleUserID) {
		this.handleUserID = handleUserID;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBednum() {
		return bednum;
	}
	public void setBednum(String bednum) {
		this.bednum = bednum;
	}
	public String getOutpatientnumber() {
		return outpatientnumber;
	}
	public void setOutpatientnumber(String outpatientnumber) {
		this.outpatientnumber = outpatientnumber;
	}
	public String getHospitalnumber() {
		return hospitalnumber;
	}
	public void setHospitalnumber(String hospitalnumber) {
		this.hospitalnumber = hospitalnumber;
	}
	public String getHospital() {
		return hospital;
	}
	public void setHospital(String hospital) {
		this.hospital = hospital;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	public String getSymptom() {
		return symptom;
	}
	public void setSymptom(String symptom) {
		this.symptom = symptom;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getHandlebegintime() {
		return handlebegintime;
	}
	public void setHandlebegintime(String handlebegintime) {
		this.handlebegintime = handlebegintime;
	}
	public String getBegintime() {
		return begintime;
	}
	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public int getHandleState() {
		return handleState;
	}
	public void setHandleState(int handleState) {
		this.handleState = handleState;
	}	
}
