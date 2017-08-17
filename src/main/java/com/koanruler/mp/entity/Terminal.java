package com.koanruler.mp.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Terminal {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private String terminalnumber;
	private Integer userid;
	private Integer patientid;
	private String bindtime;
	private Integer heartrateuplimit = 200;
	private Integer heartratedownlimit = 40;
	private Integer breatheuplimit = 50;
	private Integer breathedownlimit = 10;
	private Integer oxygenuplimit = 100;
	private Integer oxygendownlimit = 85;
	private Integer pulseuplimit = 200;
	private Integer pulsedownlimit = 40;
	private Integer diastolicpreuplimit = 180;
	private Integer diastolicpredownlimit = 35;
	private Integer systolicpreuplimit = 240;
	private Integer systolicpredownlimit = 65;
	private Integer daytestcycle = 30;
	private Integer nighttestcycle = 60;
	private Integer temperatureuplimit = 400;
	private Integer temperaturedownlimit = 320;
	private Boolean electrocardioon = true;
	private Boolean breathon = true;
	private Boolean oxygenon = true;
	private Boolean bloodpreon = true;
	private Boolean temperatureon = true;
	private Boolean autotest = false;
	private Boolean issync = true;
	private Boolean isbindinfosync = true;
	private Boolean deleteflag = false;
	private Boolean newflag = true;
	private Boolean online;

	// Constructors

	/** default constructor */
	public Terminal() {
	}

	/** minimal constructor */
	public Terminal(String terminalnumber, Integer userid, Integer patientid,
			String bindtime, Boolean deleteflag, Boolean newflag) {
		this.terminalnumber = terminalnumber;
		this.userid = userid;
		this.patientid = patientid;
		this.bindtime = bindtime;
		this.deleteflag = deleteflag;
		this.newflag = newflag;
	}

	/** full constructor */
	public Terminal(String terminalnumber, Integer userid, Integer patientid,
			String bindtime, Integer heartrateuplimit,
			Integer heartratedownlimit, Integer breatheuplimit,
			Integer breathedownlimit, Integer oxygenuplimit,
			Integer oxygendownlimit, Integer pulseuplimit,
			Integer pulsedownlimit, Integer diastolicpreuplimit,
			Integer diastolicpredownlimit, Integer systolicpreuplimit,
			Integer systolicpredownlimit, Integer daytestcycle,
			Integer nighttestcycle, Integer temperatureuplimit,
			Integer temperaturedownlimit, Boolean electrocardioon,
			Boolean breathon, Boolean oxygenon, Boolean bloodpreon,
			Boolean temperatureon, Boolean autotest, Boolean issync,
			Boolean isbindinfosync, Boolean deleteflag, Boolean newflag,
			Boolean online) {
		this.terminalnumber = terminalnumber;
		this.userid = userid;
		this.patientid = patientid;
		this.bindtime = bindtime;
		this.heartrateuplimit = heartrateuplimit;
		this.heartratedownlimit = heartratedownlimit;
		this.breatheuplimit = breatheuplimit;
		this.breathedownlimit = breathedownlimit;
		this.oxygenuplimit = oxygenuplimit;
		this.oxygendownlimit = oxygendownlimit;
		this.pulseuplimit = pulseuplimit;
		this.pulsedownlimit = pulsedownlimit;
		this.diastolicpreuplimit = diastolicpreuplimit;
		this.diastolicpredownlimit = diastolicpredownlimit;
		this.systolicpreuplimit = systolicpreuplimit;
		this.systolicpredownlimit = systolicpredownlimit;
		this.daytestcycle = daytestcycle;
		this.nighttestcycle = nighttestcycle;
		this.temperatureuplimit = temperatureuplimit;
		this.temperaturedownlimit = temperaturedownlimit;
		this.electrocardioon = electrocardioon;
		this.breathon = breathon;
		this.oxygenon = oxygenon;
		this.bloodpreon = bloodpreon;
		this.temperatureon = temperatureon;
		this.autotest = autotest;
		this.issync = issync;
		this.isbindinfosync = isbindinfosync;
		this.deleteflag = deleteflag;
		this.newflag = newflag;
		this.online = online;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTerminalnumber() {
		return this.terminalnumber;
	}

	public void setTerminalnumber(String terminalnumber) {
		this.terminalnumber = terminalnumber;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getPatientid() {
		return this.patientid;
	}

	public void setPatientid(Integer patientid) {
		this.patientid = patientid;
	}

	public String getBindtime() {
		return this.bindtime;
	}

	public void setBindtime(String bindtime) {
		this.bindtime = bindtime;
	}

	public Integer getHeartrateuplimit() {
		return this.heartrateuplimit;
	}

	public void setHeartrateuplimit(Integer heartrateuplimit) {
		this.heartrateuplimit = heartrateuplimit;
	}

	public Integer getHeartratedownlimit() {
		return this.heartratedownlimit;
	}

	public void setHeartratedownlimit(Integer heartratedownlimit) {
		this.heartratedownlimit = heartratedownlimit;
	}

	public Integer getBreatheuplimit() {
		return this.breatheuplimit;
	}

	public void setBreatheuplimit(Integer breatheuplimit) {
		this.breatheuplimit = breatheuplimit;
	}

	public Integer getBreathedownlimit() {
		return this.breathedownlimit;
	}

	public void setBreathedownlimit(Integer breathedownlimit) {
		this.breathedownlimit = breathedownlimit;
	}

	public Integer getOxygenuplimit() {
		return this.oxygenuplimit;
	}

	public void setOxygenuplimit(Integer oxygenuplimit) {
		this.oxygenuplimit = oxygenuplimit;
	}

	public Integer getOxygendownlimit() {
		return this.oxygendownlimit;
	}

	public void setOxygendownlimit(Integer oxygendownlimit) {
		this.oxygendownlimit = oxygendownlimit;
	}

	public Integer getPulseuplimit() {
		return this.pulseuplimit;
	}

	public void setPulseuplimit(Integer pulseuplimit) {
		this.pulseuplimit = pulseuplimit;
	}

	public Integer getPulsedownlimit() {
		return this.pulsedownlimit;
	}

	public void setPulsedownlimit(Integer pulsedownlimit) {
		this.pulsedownlimit = pulsedownlimit;
	}

	public Integer getDiastolicpreuplimit() {
		return this.diastolicpreuplimit;
	}

	public void setDiastolicpreuplimit(Integer diastolicpreuplimit) {
		this.diastolicpreuplimit = diastolicpreuplimit;
	}

	public Integer getDiastolicpredownlimit() {
		return this.diastolicpredownlimit;
	}

	public void setDiastolicpredownlimit(Integer diastolicpredownlimit) {
		this.diastolicpredownlimit = diastolicpredownlimit;
	}

	public Integer getSystolicpreuplimit() {
		return this.systolicpreuplimit;
	}

	public void setSystolicpreuplimit(Integer systolicpreuplimit) {
		this.systolicpreuplimit = systolicpreuplimit;
	}

	public Integer getSystolicpredownlimit() {
		return this.systolicpredownlimit;
	}

	public void setSystolicpredownlimit(Integer systolicpredownlimit) {
		this.systolicpredownlimit = systolicpredownlimit;
	}

	public Integer getDaytestcycle() {
		return this.daytestcycle;
	}

	public void setDaytestcycle(Integer daytestcycle) {
		this.daytestcycle = daytestcycle;
	}

	public Integer getNighttestcycle() {
		return this.nighttestcycle;
	}

	public void setNighttestcycle(Integer nighttestcycle) {
		this.nighttestcycle = nighttestcycle;
	}

	public Integer getTemperatureuplimit() {
		return this.temperatureuplimit;
	}

	public void setTemperatureuplimit(Integer temperatureuplimit) {
		this.temperatureuplimit = temperatureuplimit;
	}

	public Integer getTemperaturedownlimit() {
		return this.temperaturedownlimit;
	}

	public void setTemperaturedownlimit(Integer temperaturedownlimit) {
		this.temperaturedownlimit = temperaturedownlimit;
	}

	public Boolean getElectrocardioon() {
		return this.electrocardioon;
	}

	public void setElectrocardioon(Boolean electrocardioon) {
		this.electrocardioon = electrocardioon;
	}

	public Boolean getBreathon() {
		return this.breathon;
	}

	public void setBreathon(Boolean breathon) {
		this.breathon = breathon;
	}

	public Boolean getOxygenon() {
		return this.oxygenon;
	}

	public void setOxygenon(Boolean oxygenon) {
		this.oxygenon = oxygenon;
	}

	public Boolean getBloodpreon() {
		return this.bloodpreon;
	}

	public void setBloodpreon(Boolean bloodpreon) {
		this.bloodpreon = bloodpreon;
	}

	public Boolean getTemperatureon() {
		return this.temperatureon;
	}

	public void setTemperatureon(Boolean temperatureon) {
		this.temperatureon = temperatureon;
	}

	public Boolean getAutotest() {
		return this.autotest;
	}

	public void setAutotest(Boolean autotest) {
		this.autotest = autotest;
	}

	public Boolean getIssync() {
		return this.issync;
	}

	public void setIssync(Boolean issync) {
		this.issync = issync;
	}

	public Boolean getIsbindinfosync() {
		return this.isbindinfosync;
	}

	public void setIsbindinfosync(Boolean isbindinfosync) {
		this.isbindinfosync = isbindinfosync;
	}

	public Boolean getDeleteflag() {
		return this.deleteflag;
	}

	public void setDeleteflag(Boolean deleteflag) {
		this.deleteflag = deleteflag;
	}

	public Boolean getNewflag() {
		return this.newflag;
	}

	public void setNewflag(Boolean newflag) {
		this.newflag = newflag;
	}

	public Boolean getOnline() {
		return this.online;
	}

	public void setOnline(Boolean online) {
		this.online = online;
	}

}