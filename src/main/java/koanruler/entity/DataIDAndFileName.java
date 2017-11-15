package koanruler.entity;

public class DataIDAndFileName {
	private int id;
	private int type;
	private int transferflag;
	private String filename;
	private String createdate;
	private String endtime;
	
	private Integer hstate;				//handlestate
	private Integer  userid;				//userid
	private String hstime;  			//handlestarttime
	private String hetime;				//handleendtime
	
	public Integer getHstate() {
		return hstate;
	}
	public void setHstate(Integer hstate) {
		this.hstate = hstate;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getHstime() {
		return hstime;
	}
	public void setHstime(String hstime) {
		this.hstime = hstime;
	}
	public String getHetime() {
		return hetime;
	}
	public void setHetime(String hetime) {
		this.hetime = hetime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public DataIDAndFileName(int id, 
							  int type, 
							  String filename, 
							  int transferflag, 
							  String createdate, 
							  String endtime, 
							  int handlestate, 
							  int userid, 
							  String handlestarttime, 
							  String handleendtime) {
		super();
		this.id = id;
		this.type = type;
		this.filename = filename;
		this.transferflag = transferflag;
		this.createdate = createdate;
		this.endtime = endtime;
		this.hstate = handlestate;
		this.userid = userid;
		this.hstime = handlestarttime;
		this.hetime = handleendtime;
	}
	public int getTransferflag() {
		return transferflag;
	}
	public void setTransferflag(int transferflag) {
		this.transferflag = transferflag;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
}
