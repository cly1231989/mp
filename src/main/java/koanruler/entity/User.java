package koanruler.entity;

import javax.persistence.*;


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

	@Transient
	private String parentFullName;
	@Transient
	private String userType;

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
				user.getCreatedate(), user.getDelflag(), user.getVersion(), user.BPShowMode,
				user.spo2Warning, user.getBTWarning());
	}


	public Integer getId() {
		return id;
	}

	public User setId(Integer id) {
		this.id = id;
		return this;
	}

	public Integer getParentuserid() {
		return parentuserid;
	}

	public User setParentuserid(Integer parentuserid) {
		this.parentuserid = parentuserid;
		return this;
	}

	public String getAccount() {
		return account;
	}

	public User setAccount(String account) {
		this.account = account;
		return this;
	}

	public String getPwd() {
		return pwd;
	}

	public User setPwd(String pwd) {
		this.pwd = pwd;
		return this;
	}

	public String getName() {
		return name;
	}

	public User setName(String name) {
		this.name = name;
		return this;
	}

	public Integer getType() {
		return type;
	}

	public User setType(Integer type) {
		this.type = type;
		return this;
	}

	public Integer getRight1() {
		return right1;
	}

	public User setRight1(Integer right1) {
		this.right1 = right1;
		return this;
	}

	public Integer getRight2() {
		return right2;
	}

	public User setRight2(Integer right2) {
		this.right2 = right2;
		return this;
	}

	public Integer getRight3() {
		return right3;
	}

	public User setRight3(Integer right3) {
		this.right3 = right3;
		return this;
	}

	public Integer getRight4() {
		return right4;
	}

	public User setRight4(Integer right4) {
		this.right4 = right4;
		return this;
	}

	public String getPhone() {
		return phone;
	}

	public User setPhone(String phone) {
		this.phone = phone;
		return this;
	}

	public String getAddress() {
		return address;
	}

	public User setAddress(String address) {
		this.address = address;
		return this;
	}

	public String getCreatedate() {
		return createdate;
	}

	public User setCreatedate(String createdate) {
		this.createdate = createdate;
		return this;
	}

	public Integer getDelflag() {
		return delflag;
	}

	public User setDelflag(Integer delflag) {
		this.delflag = delflag;
		return this;
	}

	public Integer getVersion() {
		return version;
	}

	public User setVersion(Integer version) {
		this.version = version;
		return this;
	}

	public Integer getBPShowMode() {
		return BPShowMode;
	}

	public User setBPShowMode(Integer BPShowMode) {
		this.BPShowMode = BPShowMode;
		return this;
	}

	public Boolean getSpo2Warning() {
		return spo2Warning;
	}

	public User setSpo2Warning(Boolean spo2Warning) {
		this.spo2Warning = spo2Warning;
		return this;
	}

	public Boolean getBTWarning() {
		return BTWarning;
	}

	public User setBTWarning(Boolean BTWarning) {
		this.BTWarning = BTWarning;
		return this;
	}

	public String getParentFullName() {
		return parentFullName;
	}

	public User setParentFullName(String parentFullName) {
		this.parentFullName = parentFullName;
		return this;
	}

	public String getUserType() {
		return userType;
	}

	public User setUserType(String userType) {
		this.userType = userType;
		return this;
	}
}