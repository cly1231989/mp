package com.koanruler.mp.entity;

import java.util.List;

/**
 * Created by hose on 2017/9/11.
 */
public class PatientSearchResult {
    private long totalCount;
    private List<PatientInfo> patientInfo;

    public PatientSearchResult(long totalCount, List<PatientInfo> patientInfo) {
        this.totalCount = totalCount;
        this.patientInfo = patientInfo;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public List<PatientInfo> getPatientInfo() {
        return patientInfo;
    }

    public void setPatientInfo(List<PatientInfo> patientInfo) {
        this.patientInfo = patientInfo;
    }
}
