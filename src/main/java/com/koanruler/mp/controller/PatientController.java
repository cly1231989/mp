package com.koanruler.mp.controller;

import com.koanruler.mp.entity.*;
import com.koanruler.mp.service.PatientService;
import com.koanruler.mp.service.UserService;
import com.koanruler.mp.util.UserUtil;
import com.querydsl.core.QueryResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/patient")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @Autowired
    private UserService userService;

    /**
     * 分页获取本用户及下属机构的病人信息
     * @param nameOrBedNum: 病人姓名或者床号
     * @param inHospitalStatus: 住院状态("inHospital", "outHospital", "all")
     * @param firstIndex
     * @param count
     * @param order: 排序("desc", "asc")
     * @return PatientSearchResult: 包括满足条件的病人总数和本次查询的病人信息
     */
    @GetMapping("/search")
    public ResultList<PatientInfo> searchPatient(@RequestParam(name = "nameOrBedNum", defaultValue = "") String nameOrBedNum,
                                                 @RequestParam(name = "inHospitalStatus", defaultValue = "all") String inHospitalStatus,
                                                 @RequestParam(name = "firstIndex", defaultValue = "0") int firstIndex,
                                                 @RequestParam(name = "count", defaultValue = "30") long count,
                                                 @RequestParam(name = "order", defaultValue = "desc") String order){
                                                  //PatientSearchCondition patientSearchCondition) {

        boolean desc = order.equalsIgnoreCase("desc");

        PatientSearchCondition.InHospitalStatus status = PatientSearchCondition.InHospitalStatus.all;
        if (inHospitalStatus.equalsIgnoreCase("inHospital"))
            status = PatientSearchCondition.InHospitalStatus.inHospital;
        else if (inHospitalStatus.equalsIgnoreCase("outHospital"))
            status = PatientSearchCondition.InHospitalStatus.outHospital;

        PatientSearchCondition patientSearchCondition = new PatientSearchCondition(nameOrBedNum,
                firstIndex, count, desc, status);
        List<Integer> userIds = userService.getAllChildID(UserUtil.getCurUser().getId());
        QueryResults<Patient> results = patientService.getOneGroupPatientInfo(userIds,
                patientSearchCondition);

        List<PatientInfo> patientInfolist = results.getResults().stream().map(patient -> {
            PatientInfo patientinfo = new PatientInfo();
            patientinfo.setPatient(patient);
            patientinfo.setUsername(userService.getFullName(patient.getUserid()));

            return patientinfo;
        }).collect(Collectors.toList());

        return new ResultList<>(results.getTotal(), patientInfolist);
    }

//    public long getCount(@PathParam("userID") int userID, @PathParam("patientName") String patientName, @PathParam("inhospital") boolean inhospital) {
//        return patientService.getCount(userID, patientName, inhospital);
//    }
//
//    public List<Patient> getBindPatientInfo(@PathParam("patientIDList") List<Integer> patientIDList) {
//        return patientService.getPatientsInfo(patientIDList);
//    }
//
////    public List<Patient> getOneGroupPatientInfo(@WebParam(name = "userID") int userID, @WebParam(name = "patientName") String patientName, @WebParam(name = "inhospital") boolean inhospital, @WebParam(name = "firstPatientIndex") int firstPatientIndex, @WebParam(name = "patientCount") int patientCount) {
////        return patientService.getOneGroupPatientInfo(userID, patientName, inhospital, firstPatientIndex, patientCount);
////    }
//
//    public List<Patient> getAllPatientInfo(@PathParam("userID") int userID) {
//        return patientService.getAllPatientInfo(userID);
//    }
//
//    public List<Patient> searchPatient(@PathParam("patientName") String patientName) {
//        return patientService.searchPatient(patientName);
//    }
//
//    public boolean addPatient(@PathParam("patientinfo") Patient patientInfo) {
//        return patientService.addPatient(patientInfo);
//    }
//
//    public Patient getPatientInfo(@PathParam("patientID") int patientID) {
//        return patientService.getPatient(patientID);
//    }
}
