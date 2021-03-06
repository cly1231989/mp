package koanruler.entity;

public class DataInfo {
	private int dataId;
	private int patientId;
	private int dataType;
	private String terminalNum;
	private String fileName;
	private String createData;
	private String endTime;
	private String patientName;

    public DataInfo() {
    }

    public DataInfo(int dataId, int patientId, int dataType, String terminalNum, String fileName, String createData, String endTime, String patientName) {
        this.dataId = dataId;
        this.patientId = patientId;
        this.dataType = dataType;
        this.terminalNum = terminalNum;
        this.fileName = fileName;
        this.createData = createData;
        this.endTime = endTime;
        this.patientName = patientName;
    }

    public int getDataId() {
		return dataId;
	}

	public void setDataId(int dataId) {
		this.dataId = dataId;
	}

	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	public String getTerminalNum() {
		return terminalNum;
	}

	public void setTerminalNum(String terminalNum) {
		this.terminalNum = terminalNum;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getCreateData() {
		return createData;
	}

	public void setCreateData(String createData) {
		this.createData = createData;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
}
