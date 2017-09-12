package com.koanruler.mp.entity;

/**
 * Created by hose on 2017/9/11.
 */
public class PatientSearchCondition {
    private String patientNameOrBedNum;
    private int pageth;
    private int countPerPage;
    private boolean desc;

    public PatientSearchCondition() {
    }

    public String getPatientNameOrBedNum() {
        return patientNameOrBedNum;
    }

    public void setPatientNameOrBedNum(String patientNameOrBedNum) {
        this.patientNameOrBedNum = patientNameOrBedNum;
    }

    public int getPageth() {
        return pageth;
    }

    public void setPageth(int pageth) {
        this.pageth = pageth;
    }

    public int getCountPerPage() {
        return countPerPage;
    }

    public void setCountPerPage(int countPerPage) {
        this.countPerPage = countPerPage;
    }

    public boolean isDesc() {
        return desc;
    }

    public void setDesc(boolean desc) {
        this.desc = desc;
    }
}
