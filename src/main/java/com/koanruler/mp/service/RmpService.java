package com.koanruler.mp.service;

import com.koanruler.mp.entity.Patient;
import org.springframework.stereotype.Service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

/**
 * Created by hose on 2017/8/15.
 */
@Service
@WebService
public interface RmpService {

    /**************************************************************************/
    @WebMethod
    String USER_GetUserName(@WebParam(name = "userID") int userID);

    @WebMethod
    String USER_GetAllParentID(@WebParam(name = "userID") int userID);

    @WebMethod
    String USER_GetSubDepartmentInfo(@WebParam(name = "userID") int userID);

    @WebMethod
    String USER_Login(@WebParam(name = "account") String account, @WebParam(name = "pwd") String pwd);

    /**************************************************************************/
    @WebMethod
    String PATIENT_GetCount(@WebParam(name = "userID") int userID, @WebParam(name = "patientName") String patientName, @WebParam(name = "inhospital") boolean inhospital);

    @WebMethod
    String PATIENT_GetBindPatientInfo(@WebParam(name = "patientIDList") int[] patientIDList);

    @WebMethod
    String PATIENT_GetOneGroupPatientInfo(@WebParam(name = "userID") int userID, @WebParam(name = "patientName") String patientName, @WebParam(name = "inhospital") boolean inhospital, @WebParam(name = "firstPatientIndex") int firstPatientIndex, @WebParam(name = "patientCount") int patientCount);

    @WebMethod
    String PATIENT_GetAllPatientInfo(@WebParam(name = "userID") int userID);

    @WebMethod
    String PATIENT_SearchPatient(@WebParam(name = "patientName") String patientName);

    @WebMethod
    String PATIENT_AddPatient(@WebParam(name = "patientinfo") Patient patientInfo);

    @WebMethod
    String PATIENT_GetPatientInfo(@WebParam(name = "patientID") int patientID);

    /**************************************************************************/
    @WebMethod
    String DATA_GetCount();

    @WebMethod
    String DATA_GetReplayInfoCount(@WebParam(name = "userID") int userID, @WebParam(name = "search") String search);

    @WebMethod
    String DATA_GetOneGroupReplayInfo(@WebParam(name = "userID") int userID, @WebParam(name = "search") String search, @WebParam(name = "firstIndex") int firstIndex, @WebParam(name = "count") int count);

    //will be deprecated because of efficiency
    @WebMethod
    String DATA_SearchReplayInfo(@WebParam(name = "userID") int userID,
                                 @WebParam(name = "patientname") String patientname,
                                 @WebParam(name = "bednum") String bednum,
                                 @WebParam(name = "hospitalid") int hospitalid,
                                 @WebParam(name = "departmentid") int departmentid,
                                 @WebParam(name = "begindate") String begindate,
                                 @WebParam(name = "enddate") String enddate,
                                 @WebParam(name = "type") int type,
                                 @WebParam(name = "state") int state,
                                 @WebParam(name = "minseconds") int minseconds,
                                 @WebParam(name = "patientcount") int patientcount);

    @WebMethod
    byte[] DATA_GetCompressedSearchReplayInfo(@WebParam(name = "userID") int userID,
                                              @WebParam(name = "patientname") String patientname,
                                              @WebParam(name = "bednum") String bednum,
                                              @WebParam(name = "hospitalid") int hospitalid,
                                              @WebParam(name = "departmentid") int departmentid,
                                              @WebParam(name = "begindate") String begindate,
                                              @WebParam(name = "enddate") String enddate,
                                              @WebParam(name = "type") int type,
                                              @WebParam(name = "state") int state,
                                              @WebParam(name = "minseconds") int minseconds,
                                              @WebParam(name = "patientcount") int patientcount);

    @WebMethod
    String DATA_GetOneGroupPatientInfo(@WebParam(name = "userID") int userID, @WebParam(name = "count") int count, @WebParam(name = "minseconds") int minseconds);

    @WebMethod
    String DATA_GetPatientData(@WebParam(name = "patientID") int patientID, @WebParam(name = "type") int type);

    @WebMethod
    String DATA_GetDataFileNames(@WebParam(name = "patientID") int patientID, @WebParam(name = "days") int days, @WebParam(name = "type") int datatype);

    @WebMethod
    String DATA_GetTransferState(@WebParam(name = "dataID") int dataID);

    @WebMethod
    String DATA_Login(@WebParam(name = "account") String account, @WebParam(name = "pwd") String pwd);

    @WebMethod
    String DATA_SetHandleState(@WebParam(name = "dataIDs") List<Integer> dataIDs, @WebParam(name = "state") int state, @WebParam(name = "userid") int userid);

    @WebMethod
    String DATA_GetOnePatientData(@WebParam(name = "patientID") int patientID, @WebParam(name = "begeindate") String begeindate, @WebParam(name = "enddate") String enddate, @WebParam(name = "state") int state, @WebParam(name = "minseconds") int minseconds);

    @WebMethod
    String Data_HasNewFileToDownload(@WebParam(name = "patientID") int patientID, @WebParam(name = "datatype") int datatype, @WebParam(name = "filelength") long filelength);

    /**************************************************************************/
    @WebMethod
    String ShuangJia_EcgReport(@WebParam(name = "ReportCode") String ReportCode, @WebParam(name = "ReportData") String ReportData);

    /**************************************************************************/
    @WebMethod
    String TERMINAL_GetAllTerminalInfo(@WebParam(name = "userID") int userID);

    @WebMethod
    String TERMINAL_GetTerminal(@WebParam(name = "terminalID") String terminalID);

    @WebMethod
    String TERMINAL_BindTerminal(@WebParam(name = "terminalID") String terminalID, @WebParam(name = "patientID") int patientID);

    /**************************************************************************/
    @WebMethod
    String SUBFACILITIES_GetAllDepartmentInfo(@WebParam(name = "userID") int userID);
}
