package koanruler.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Subfacilities {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private Integer userid;
	private String name;
	private Integer type;
	private String phone;
	private String content;
	private String remark;
	private String createdate;

	// Constructors

	/** default constructor */
	public Subfacilities() {
	}

	/** minimal constructor */
	public Subfacilities(Integer userid, String name, Integer type,
			String createdate) {
		this.userid = userid;
		this.name = name;
		this.type = type;
		this.createdate = createdate;
	}

	/** full constructor */
	public Subfacilities(Integer userid, String name, Integer type,
			String phone, String content, String remark, String createdate) {
		this.userid = userid;
		this.name = name;
		this.type = type;
		this.phone = phone;
		this.content = content;
		this.remark = remark;
		this.createdate = createdate;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
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

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

}