package com.koanruler.mp.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class User {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private Integer parentuserid;
	private String account;
	private String pwd;
	private String name;
	private Integer type;
	private Integer right1;
	private Integer right2;
	private Integer right3;
	private Integer right4;
	private String phone;
	private String address;
	private String createdate;
	private Integer delflag;
	private Integer version;
	private Integer BPShowMode = 0;
	private Boolean spo2Warning = true;
	private Boolean BTWarning = true;

	// Constructors

	/** default constructor */
	public User() {
	}

	/** minimal constructor */
	public User(String account, String pwd, String name, Integer type,
			String createdate) {
		this.account = account;
		this.pwd = pwd;
		this.name = name;
		this.type = type;
		this.createdate = createdate;
	}

	/** full constructor */
	public User(Integer parentuserid, String account, String pwd, String name,
			Integer type, Integer right1, Integer right2, Integer right3,
			Integer right4, String phone, String address, String createdate,
			Integer delflag, Integer version, Integer BPShowMode, Boolean spo2Warning,
			Boolean BTWarning) {
		this.parentuserid = parentuserid;
		this.account = account;
		this.pwd = pwd;
		this.name = name;
		this.type = type;
		this.right1 = right1;
		this.right2 = right2;
		this.right3 = right3;
		this.right4 = right4;
		this.phone = phone;
		this.address = address;
		this.createdate = createdate;
		this.delflag = 0;
		this.version = 0;
		this.delflag = delflag;
		this.version = version;
		this.BPShowMode = BPShowMode;
		this.spo2Warning = spo2Warning;
		this.BTWarning = BTWarning;
	}

	public User(Integer id, Integer parentuserid, String account, String pwd,
			String name, Integer type, Integer right1, Integer right2,
			Integer right3, Integer right4, String phone, String address,
			String createdate, Integer delflag, Integer version, Integer BPShowMode,
			Boolean spo2Warning, Boolean BTWarning) {
		super();
		this.id = id;
		this.parentuserid = parentuserid;
		this.account = account;
		this.pwd = pwd;
		this.name = name;
		this.type = type;
		this.right1 = right1;
		this.right2 = right2;
		this.right3 = right3;
		this.right4 = right4;
		this.phone = phone;
		this.address = address;
		this.createdate = createdate;
		this.delflag = delflag;
		this.version = version;
		this.BPShowMode = BPShowMode;
		this.spo2Warning = spo2Warning;
		this.BTWarning = BTWarning;
	}

	public User(User user) {
		this(user.getId(), user.getParentuserid(), user.getAccount(), user.getPwd(),
				user.getName(), user.getType(), user.getRight1(), user.getRight2(),
				user.getRight3(), user.getRight4(), user.getPhone(), user.getAddress(),
				user.getCreatedate(), user.getdelflag(), user.getVersion(), user.BPShowMode,
				user.spo2Warning, user.getBTWarning());
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParentuserid() {
		return this.parentuserid;
	}

	public void setParentuserid(Integer parentuserid) {
		this.parentuserid = parentuserid;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getRight1() {
		return this.right1;
	}

	public void setRight1(Integer right1) {
		this.right1 = right1;
	}

	public Integer getRight2() {
		return this.right2;
	}

	public void setRight2(Integer right2) {
		this.right2 = right2;
	}

	public Integer getRight3() {
		return this.right3;
	}

	public void setRight3(Integer right3) {
		this.right3 = right3;
	}

	public Integer getRight4() {
		return this.right4;
	}

	public void setRight4(Integer right4) {
		this.right4 = right4;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	
	public Integer getdelflag() {
		return this.delflag;
	}
	
	public void setdelflag(Integer delflag) {
		this.delflag = delflag;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getDelflag() {
		return delflag;
	}

	public void setDelflag(Integer delflag) {
		this.delflag = delflag;
	}

	public Integer getBPShowMode() {
		return BPShowMode;
	}

	public void setBPShowMode(Integer BPShowMode) {
		this.BPShowMode = BPShowMode;
	}

	public Boolean getSpo2Warning() {
		return spo2Warning;
	}

	public void setSpo2Warning(Boolean spo2Warning) {
		this.spo2Warning = spo2Warning;
	}

	public Boolean getBTWarning() {
		return BTWarning;
	}

	public void setBTWarning(Boolean BTWarning) {
		this.BTWarning = BTWarning;
	}
}