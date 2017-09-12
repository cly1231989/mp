package com.koanruler.mp.entity;

/**
 * Created by hose on 2017/9/11.
 */
public class PatientSearchCondition {
    private String nameOrBedNum = "";
    private int firstIndex = 0;
    private long count = 30;
    private boolean desc = true;
    private InHospitalStatus inHospitalStatus = InHospitalStatus.all;

    public enum InHospitalStatus{
        inHospital, outHospital, all
    }

    public PatientSearchCondition() {
    }

    public PatientSearchCondition(String nameOrBedNum, int firstIndex, int count, boolean desc, InHospitalStatus inHospitalStatus) {
        this.nameOrBedNum = nameOrBedNum;
        this.firstIndex = firstIndex;
        this.count = count;
        this.desc = desc;
        this.inHospitalStatus = inHospitalStatus;
    }

    public String getNameOrBedNum() {
        return nameOrBedNum;
    }

    public PatientSearchCondition setNameOrBedNum(String nameOrBedNum) {
        this.nameOrBedNum = nameOrBedNum;
        return this;
    }

    public int getFirstIndex() {
        return firstIndex;
    }

    public PatientSearchCondition setFirstIndex(int firstIndex) {
        this.firstIndex = firstIndex;
        return this;
    }

    public long getCount() {
        return count;
    }

    public PatientSearchCondition setCount(long count) {
        this.count = count;
        return this;
    }

    public boolean isDesc() {
        return desc;
    }

    public PatientSearchCondition setDesc(boolean desc) {
        this.desc = desc;
        return this;
    }

    public InHospitalStatus getInHospitalStatus() {
        return inHospitalStatus;
    }

    public PatientSearchCondition setInHospitalStatus(InHospitalStatus inHospitalStatus) {
        this.inHospitalStatus = inHospitalStatus;
        return this;
    }
}
