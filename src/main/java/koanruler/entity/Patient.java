package koanruler.entity;

import javax.persistence.*;

@Entity
public class Patient {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private Integer userid;
	private Integer userpatientid;
	private Integer padrecordid;
	private String name;
	private String age;
	private String sex;
	private String phone;
	private String birthday;
	private String createdate;
	private String address;
	private String applydoctor;
	private String nurse;
	private String inpatientarea;
	private String outpatientnumber;
	private String bednumber;
	private String department;
	private String hospitalnumber;
	private String symptom;
	private String height;
	private String weight;
	private String level;
	private String category;
	private String inhospitaldate;
	private Integer bindtype;
	private String datahandletime;
	private String zip;
	private Boolean state;

	@Transient
	String username;

	@Transient
	String hospital;

	@Column(name = "handlestate", columnDefinition="int(4) default 0 COMMENT '0:未处理;1:正在处理;2:完成处理;3:更新处理;4:中断处理;7:正在处理，有新数据; 8:完成处理，有新数据;'")
	private Integer handlestate;
	
	@Column(name = "handleuserid", columnDefinition="int(11) default 0 COMMENT '当前处理该病人数据的用户'")
	private Integer handleuserid;
	
	@Column(name = "handlestarttime", columnDefinition="varchar(20) default '0000-00-00 00:00:00' COMMENT '该病人的开始处理时间'")
	private String handlestarttime;
	
	@Column(name = "recordstarttime", columnDefinition="varchar(20) default '0000-00-00 00:00:00' COMMENT '该病人的起始记录时间'")
	private String recordstarttime;
	
	@Column(name = "recordendtime", columnDefinition="varchar(20) default '0000-00-00 00:00:00' COMMENT '该病人的最后记录时间'")
	private String recordendtime;

	// Constructors

	/** default constructor */
	public Patient() {
	}

	/** minimal constructor */
	public Patient(Integer userid, String name, String age, String sex) {
		this.userid = userid;
		this.name = name;
		this.age = age;
		this.sex = sex;
	}

	/** full constructor */
	public Patient(Integer userid, Integer userpatientid, Integer padrecordid,
			String name, String age, String sex, String phone, String birthday,
			String createdate, String address, String applydoctor,
			String nurse, String inpatientarea, String outpatientnumber,
			String bednumber, String department, String hospitalnumber,
			String symptom, String height, String weight, String level,
			String category, String inhospitaldate, Integer bindtype,
			String datahandletime, String zip, Boolean state,
			Integer handlestate, Integer handleuserid, String handlestarttime,
			String recordstarttime, String recordendtime) {
		this.userid = userid;
		this.userpatientid = userpatientid;
		this.padrecordid = padrecordid;
		this.name = name;
		this.age = age;
		this.sex = sex;
		this.phone = phone;
		this.birthday = birthday;
		this.createdate = createdate;
		this.address = address;
		this.applydoctor = applydoctor;
		this.nurse = nurse;
		this.inpatientarea = inpatientarea;
		this.outpatientnumber = outpatientnumber;
		this.bednumber = bednumber;
		this.department = department;
		this.hospitalnumber = hospitalnumber;
		this.symptom = symptom;
		this.height = height;
		this.weight = weight;
		this.level = level;
		this.category = category;
		this.inhospitaldate = inhospitaldate;
		this.bindtype = bindtype;
		this.datahandletime = datahandletime;
		this.zip = zip;
		this.state = state;
		this.handlestate = handlestate;
		this.handleuserid = handleuserid;
		this.handlestarttime = handlestarttime;
		this.recordstarttime = recordstarttime;
		this.recordendtime = recordendtime;
	}
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "userid", nullable = false)
	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	@Column(name = "userpatientid")
	public Integer getUserpatientid() {
		return this.userpatientid;
	}

	public void setUserpatientid(Integer userpatientid) {
		this.userpatientid = userpatientid;
	}

	@Column(name = "padrecordid")
	public Integer getPadrecordid() {
		return this.padrecordid;
	}

	public void setPadrecordid(Integer padrecordid) {
		this.padrecordid = padrecordid;
	}

	@Column(name = "name", nullable = false, length = 32)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "age", nullable = false, length = 8)
	public String getAge() {
		return this.age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	@Column(name = "sex", nullable = false, length = 8)
	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "phone", length = 32)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "birthday", length = 32)
	public String getBirthday() {
		return this.birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	@Column(name = "createdate", length = 20)
	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	@Column(name = "address", length = 250)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "applydoctor", length = 32)
	public String getApplydoctor() {
		return this.applydoctor;
	}

	public void setApplydoctor(String applydoctor) {
		this.applydoctor = applydoctor;
	}

	@Column(name = "nurse", length = 32)
	public String getNurse() {
		return this.nurse;
	}

	public void setNurse(String nurse) {
		this.nurse = nurse;
	}

	@Column(name = "inpatientarea", length = 32)
	public String getInpatientarea() {
		return this.inpatientarea;
	}

	public void setInpatientarea(String inpatientarea) {
		this.inpatientarea = inpatientarea;
	}

	@Column(name = "outpatientnumber", length = 32)
	public String getOutpatientnumber() {
		return this.outpatientnumber;
	}

	public void setOutpatientnumber(String outpatientnumber) {
		this.outpatientnumber = outpatientnumber;
	}

	@Column(name = "bednumber", length = 32)
	public String getBednumber() {
		return this.bednumber;
	}

	public void setBednumber(String bednumber) {
		this.bednumber = bednumber;
	}

	@Column(name = "department", length = 32)
	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@Column(name = "hospitalnumber", length = 32)
	public String getHospitalnumber() {
		return this.hospitalnumber;
	}

	public void setHospitalnumber(String hospitalnumber) {
		this.hospitalnumber = hospitalnumber;
	}

	@Column(name = "symptom", length = 250)
	public String getSymptom() {
		return this.symptom;
	}

	public void setSymptom(String symptom) {
		this.symptom = symptom;
	}

	@Column(name = "height", length = 4)
	public String getHeight() {
		return this.height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	@Column(name = "weight", length = 4)
	public String getWeight() {
		return this.weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	@Column(name = "level", length = 32)
	public String getLevel() {
		return this.level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	@Column(name = "category", length = 32)
	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Column(name = "inhospitaldate", length = 32)
	public String getInhospitaldate() {
		return this.inhospitaldate;
	}

	public void setInhospitaldate(String inhospitaldate) {
		this.inhospitaldate = inhospitaldate;
	}

	@Column(name = "bindtype")
	public Integer getBindtype() {
		return this.bindtype;
	}

	public void setBindtype(Integer bindtype) {
		this.bindtype = bindtype;
	}

	@Column(name = "datahandletime", length = 20)
	public String getDatahandletime() {
		return this.datahandletime;
	}

	public void setDatahandletime(String datahandletime) {
		this.datahandletime = datahandletime;
	}

	@Column(name = "zip", length = 10)
	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	@Column(name = "state")
	public Boolean getState() {
		return this.state;
	}

	public void setState(Boolean state) {
		this.state = state;
	}

	@Column(name = "handlestate", columnDefinition="int(4) default 0 COMMENT '0:未处理;1:正在处理;2:完成处理;3:更新处理;4:中断处理;7:正在处理，有新数据; 8:完成处理，有新数据;'")
	public Integer getHandlestate() {
		return this.handlestate;
	}

	public void setHandlestate(Integer handlestate) {
		this.handlestate = handlestate;
	}

	@Column(name = "handleuserid", columnDefinition="int(11) default 0 COMMENT '当前处理该病人数据的用户'")
	public Integer getHandleuserid() {
		return this.handleuserid;
	}

	public void setHandleuserid(Integer handleuserid) {
		this.handleuserid = handleuserid;
	}

	@Column(name = "handlestarttime", columnDefinition="varchar(20) default '0000-00-00 00:00:00' COMMENT '该病人的开始处理时间'")
	public String getHandlestarttime() {
		return this.handlestarttime;
	}

	public void setHandlestarttime(String handlestarttime) {
		this.handlestarttime = handlestarttime;
	}

	@Column(name = "recordstarttime", columnDefinition="varchar(20) default '0000-00-00 00:00:00' COMMENT '该病人的起始记录时间'")
	public String getRecordstarttime() {
		return recordstarttime;
	}

	public void setRecordstarttime(String recordstarttime) {
		this.recordstarttime = recordstarttime;
	}

	@Column(name = "recordendtime", columnDefinition="varchar(20) default '0000-00-00 00:00:00' COMMENT '该病人的最后记录时间'")
	public String getRecordendtime() {
		return recordendtime;
	}

	public void setRecordendtime(String recordendtime) {
		this.recordendtime = recordendtime;
	}

	public String getUsername() {
		return username;
	}

	public Patient setUsername(String username) {
		this.username = username;
		return this;
	}

	public String getHospital() {
		return hospital;
	}

	public Patient setHospital(String hospital) {
		this.hospital = hospital;
		return this;
	}
}
