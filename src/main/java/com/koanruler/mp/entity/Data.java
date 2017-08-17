package com.koanruler.mp.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Data {

	// Fields

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Patient){
			Patient p = (Patient)obj; 
			return this.getPatientid() == p.getId();
		}
		return super.equals(obj);
	}

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private Integer patientid;
	private String terminalnum;
	private String filename;
	private Integer type;
	private String createdate;
	private String endtime;
	private Integer handlestate;
	private Integer userid;
	private String handlestarttime;
	private String handleendtime;
	private Short transferflag;

	// Constructors

	/** default constructor */
	public Data() {
	}

	/** minimal constructor */
	public Data(String filename, Integer type, String createdate, String endtime) {
		this.filename = filename;
		this.type = type;
		this.createdate = createdate;
		this.endtime = endtime;
	}

	/** full constructor */
	public Data(Integer patientid, String terminalnum, String filename,
			Integer type, String createdate, String endtime,
			Integer handlestate, Integer userid, String handlestarttime,
			String handleendtime, Short transferflag) {
		this.patientid = patientid;
		this.terminalnum = terminalnum;
		this.filename = filename;
		this.type = type;
		this.createdate = createdate;
		this.endtime = endtime;
		this.handlestate = handlestate;
		this.userid = userid;
		this.handlestarttime = handlestarttime;
		this.handleendtime = handleendtime;
		this.transferflag = transferflag;
	}


	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPatientid() {
		return this.patientid;
	}

	public void setPatientid(Integer patientid) {
		this.patientid = patientid;
	}

	public String getTerminalnum() {
		return this.terminalnum;
	}

	public void setTerminalnum(String terminalnum) {
		this.terminalnum = terminalnum;
	}

	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getEndtime() {
		return this.endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public Integer getHandlestate() {
		return this.handlestate;
	}

	public void setHandlestate(Integer handlestate) {
		this.handlestate = handlestate;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getHandlestarttime() {
		return this.handlestarttime;
	}

	public void setHandlestarttime(String handlestarttime) {
		this.handlestarttime = handlestarttime;
	}

	public String getHandleendtime() {
		return this.handleendtime;
	}

	public void setHandleendtime(String handleendtime) {
		this.handleendtime = handleendtime;
	}

	public Short getTransferflag() {
		return this.transferflag;
	}

	public void setTransferflag(Short transferflag) {
		this.transferflag = transferflag;
	}

}