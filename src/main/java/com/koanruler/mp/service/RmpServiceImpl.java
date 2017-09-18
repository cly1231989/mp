package com.koanruler.mp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koanruler.mp.entity.PatientDataSearchCondition;
import com.koanruler.mp.entity.Patient;
import com.koanruler.mp.entity.PatientSearchCondition;
import com.koanruler.mp.entity.ServiceResult;
import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.WebParam;
import javax.jws.WebService;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hose on 2017/8/15.
 */
@Service
@WebService(endpointInterface = "com.koanruler.mp.service.RmpService")
public class RmpServiceImpl implements RmpService {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DataService dataService;

    @Autowired
    private TerminalService terminalService;

    @Autowired
    private SubfacilityService subfacilityService;

    private final Logger logger = LoggerFactory.getLogger(RmpServiceImpl.class);

    @Override
    public String USER_GetUserName(@WebParam(name="userID") int userID) {
        return new ServiceResult(true, userService.getUserName(userID)).toJson();
    }

    @Override
    public String USER_GetUser(int userID) {
        return new ServiceResult(true, userService.getUser(userID)).toJson();
    }

    @Override
    public String USER_GetSubDepartmentInfo(int userID) {
        return new ServiceResult(true, userService.getSubDepartmentInfo(userID) ).toJson();
    }

    @Override
    public String PATIENT_GetCount(int userID, String patientName, boolean inhospital) {
        PatientSearchCondition patientSearchCondition = new PatientSearchCondition(patientName,
                0,
                1,
                true,
                inhospital ? PatientSearchCondition.InHospitalStatus.inHospital: PatientSearchCondition.InHospitalStatus.outHospital);

        List userIds = Arrays.asList(userID);
        return new ServiceResult(true, patientService.getOneGroupPatientInfo(userIds, patientSearchCondition).getTotal()).toJson();
    }

    @Override
    public String PATIENT_GetBindPatientInfo(List<Integer> patientIDList) {
        return new ServiceResult(true, patientService.getPatientsInfo(patientIDList)).toJson();
    }

    @Override
    public String PATIENT_GetOneGroupPatientInfo(int userID, String patientName, boolean inhospital, int firstPatientIndex, int patientCount) {
        PatientSearchCondition patientSearchCondition = new PatientSearchCondition(patientName,
                                                                                   firstPatientIndex,
                                                                                   patientCount,
                                                                              true,
                                                                                   inhospital ? PatientSearchCondition.InHospitalStatus.inHospital: PatientSearchCondition.InHospitalStatus.outHospital);

        List userIds = Arrays.asList(userID);
        return new ServiceResult(true, patientService.getOneGroupPatientInfo(userIds, patientSearchCondition).getResults()).toJson();
    }

//    @Override
//    public String PATIENT_GetAllPatientInfo(int userID) {
//        return new ServiceResult(true, patientService.getAllPatientInfo(userID)).toJson();
//    }

    @Override
    public String PATIENT_AddPatient(Patient patientInfo) {
        patientService.Save(patientInfo);
        return new ServiceResult(true, null).toJson();
    }

    @Override
    public String PATIENT_GetPatientInfo(int patientID) {
        return new ServiceResult(true, patientService.getPatient(patientID)).toJson();
    }

    @Override
    public byte[] DATA_GetCompressedSearchReplayInfo(int userID, String patientname, String bednum, int hospitalid, int departmentid, String begindate, String enddate, int type, int state, int minseconds, int patientcount) {
        PatientDataSearchCondition searchCondition = new PatientDataSearchCondition(patientname, bednum, hospitalid, departmentid, begindate, enddate, type, state, patientcount, minseconds);

        long beginTime = System.currentTimeMillis();
        byte[] data;

        try {
            String result = objectMapper.writeValueAsString( new ServiceResult(true, dataService.searchReplayInfo1(userID, searchCondition)) );

            data = result.getBytes("UTF-8");
            final int decompressedLength = data.length;

            LZ4Compressor compressor = LZ4Factory.safeInstance().fastCompressor();
            int maxCompressedLength = compressor.maxCompressedLength(decompressedLength);
            byte[] compressed = new byte[maxCompressedLength];
            int compressedLength = compressor.compress(data, 0, decompressedLength, compressed, 0, maxCompressedLength);

            byte[] re = new byte[compressedLength];
            System.arraycopy(compressed, 0, re, 0, compressedLength);

            logger.debug("compress data time: " + (System.currentTimeMillis() - beginTime));
            return re;
        } catch (JsonProcessingException|UnsupportedEncodingException e) {
            logger.debug(e.getMessage());
            return null;
        }
    }

//    @Override
//    public String DATA_GetOneGroupPatientInfo(int userID, int count, int minseconds) {
//        return new ServiceResult(true, dataService.getOneGroupPatientInfo(userID, count, minseconds) ).toJson();
//    }

    @Override
    public String DATA_GetPatientData(int patientID, int type) {
        return new ServiceResult(true, dataService.getDatasByPatienIdAndType(patientID, type)).toJson();
    }

    @Override
    public String DATA_SetHandleState(List<Integer> dataIDs, int state, int userid) {
        return new ServiceResult(dataService.setHandleState(dataIDs, state, userid), null).toJson();
    }

    @Override
    public String DATA_GetOnePatientData(int patientID, String begeindate, String enddate, int state, int minseconds) {
        return new ServiceResult(true, dataService.getOnePatientData(patientID, begeindate, enddate, state, minseconds)).toJson();
    }

    @Override
    public String Data_HasNewFileToDownload(int patientID, int datatype, long filelength) {
        return new ServiceResult(true, dataService.hasNewFileToDownload(patientID, datatype, filelength)).toJson();
    }

    @Override
    public String Data_HasNewFileToDownload1(@WebParam(name="fileName") String fileName,
                                      @WebParam(name="filelength") long filelength){
        return new ServiceResult(true, dataService.hasNewFileToDownload(fileName, filelength)).toJson();
    }

    @Override
    public String ShuangJia_EcgReport(String reportCode, String reportData) {
        return dataService.ecgReport(reportCode, reportData);
    }

    @Override
    public String TERMINAL_GetAllTerminalInfo(int userID) {
        return new ServiceResult(true, terminalService.getAllTerminalInfo(userID) ).toJson();
    }

    @Override
    public String TERMINAL_GetTerminal(String terminalNumber) {
        return new ServiceResult(true, terminalService.getTerminal(terminalNumber)).toJson();
    }

    @Override
    public String SUBFACILITIES_GetAllDepartmentInfo(int userID) {
        return new ServiceResult(true, subfacilityService.getAllDepartmentInfo(userID)).toJson();
    }
}
