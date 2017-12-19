package koanruler.webservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import koanruler.entity.*;
import koanruler.service.*;
import koanruler.util.CompressUtil;
import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.WebParam;
import javax.jws.WebService;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by hose on 2017/8/15.
 */
@Service
@WebService(endpointInterface = "koanruler.webservice.RmpService")
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
        long beginTime = System.currentTimeMillis();

        String name = userService.getUserName(userID);
        return new ServiceResult1(true, System.currentTimeMillis() - beginTime, "name", name).toJson();
        //return new ServiceResult(true, userService.getUserName(userID)).toJson();
    }

    @Override
    public String USER_GetUser(int userID) {
        long beginTime = System.currentTimeMillis();

        User user = userService.getUser(userID);
        return new ServiceResult1(true, System.currentTimeMillis() - beginTime, "user", user).toJson();
        //return new ServiceResult(true, userService.getUser(userID)).toJson();
    }

    @Override
    public String USER_GetSubDepartmentInfo(int userID) {
        long beginTime = System.currentTimeMillis();

        Organization organization = userService.getSubDepartmentInfo(userID);
        return new ServiceResult1(true, System.currentTimeMillis() - beginTime, "users", organization).toJson();
        //return new ServiceResult(true, userService.getSubDepartmentInfo(userID) ).toJson();
    }

    @Override
    public String PATIENT_GetCount(int userID, String patientName, boolean inhospital) {
        long beginTime = System.currentTimeMillis();

        PatientSearchCondition patientSearchCondition = new PatientSearchCondition(patientName,
                0,
                1,
                true,
                inhospital ? PatientSearchCondition.InHospitalStatus.inHospital: PatientSearchCondition.InHospitalStatus.outHospital);

        List<Integer> userIds = Arrays.asList(userID);
        Long count = patientService.getOneGroupPatientInfo(userIds, patientSearchCondition).getTotal();
        return new ServiceResult1(true, System.currentTimeMillis()-beginTime, "count", count).toJson();
        //return new ServiceResult(true, patientService.getOneGroupPatientInfo(userIds, patientSearchCondition).getTotal()).toJson();
    }

    @Override
    public String PATIENT_GetBindPatientInfo(List<Integer> patientIDList) {
        long beginTime = System.currentTimeMillis();

        List patients = patientService.getPatientsInfo(patientIDList);
        return new ServiceResult1(true, System.currentTimeMillis() - beginTime, "patientlist", patients).toJson();
        //return new ServiceResult(true, patientService.getPatientsInfo(patientIDList)).toJson();
    }

    @Override
    public String PATIENT_GetOneGroupPatientInfo(int userID, String patientName, boolean inhospital, int firstPatientIndex, int patientCount) {
        long beginTime = System.currentTimeMillis();

        PatientSearchCondition patientSearchCondition = new PatientSearchCondition(patientName,
                                                                                   firstPatientIndex,
                                                                                   patientCount,
                                                                              false,
                                                                                   inhospital ? PatientSearchCondition.InHospitalStatus.inHospital: PatientSearchCondition.InHospitalStatus.outHospital);

        List<Integer> userIds = Arrays.asList(userID);
        List patients = patientService.getOneGroupPatientInfo(userIds, patientSearchCondition).getResults();
        return new ServiceResult1(true, System.currentTimeMillis()-beginTime, "patientlist", patients).toJson();
        //return new ServiceResult(true, patientService.getOneGroupPatientInfo(userIds, patientSearchCondition).getResults()).toJson();
    }

//    @Override
//    public String PATIENT_GetAllPatientInfo(int userID) {
//        return new ServiceResult(true, patientService.getAllPatientInfo(userID)).toJson();
//    }

    @Override
    public String PATIENT_AddPatient(Patient patientInfo) {
        long beginTime = System.currentTimeMillis();

        Date nowTime=new Date();

        SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = time.format( nowTime.getTime() );

        patientInfo.setCreatedate(now);
        patientInfo = patientService.Save(patientInfo);

        return new ServiceResult1(true, System.currentTimeMillis() - beginTime, "patientid", patientInfo.getId()).toJson();
        //return new ServiceResult(true, null).toJson();
    }

    @Override
    public String PATIENT_GetPatientInfo(int patientID) {
        long beginTime = System.currentTimeMillis();

        Patient patient = patientService.getPatient(patientID);
        return new ServiceResult1(true, System.currentTimeMillis() - beginTime, "patient", patient).toJson();
        //return new ServiceResult(true, patientService.getPatient(patientID)).toJson();
    }

    @Override
    public byte[] DATA_GetCompressedSearchReplayInfo(int userID, String patientname, String bednum, int hospitalid, int departmentid, String begindate, String enddate, int type, int state, int minseconds, int patientcount) {
        PatientDataSearchCondition searchCondition = new PatientDataSearchCondition(patientname, bednum, hospitalid, departmentid, begindate, enddate, type, state, patientcount, minseconds);

        long beginTime = System.currentTimeMillis();
        List datas = dataService.searchReplayInfo1(userID, searchCondition);
        String result = new ServiceResult1(true, System.currentTimeMillis()-beginTime, "replayinfo", datas).toJson();
        return CompressUtil.compressData(result);
    }

//    @Override
//    public String DATA_GetOneGroupPatientInfo(int userID, int count, int minseconds) {
//        return new ServiceResult(true, dataService.getOneGroupPatientInfo(userID, count, minseconds) ).toJson();
//    }

    @Override
    public String DATA_GetPatientData(int patientID, int type) {
        long beginTime = System.currentTimeMillis();

        List datas = dataService.getDatasByPatienIdAndType(patientID, type);
        return new ServiceResult1(true, System.currentTimeMillis() - beginTime, "datalist", datas).toJson();
        //return new ServiceResult(true, dataService.getDatasByPatienIdAndType(patientID, type)).toJson();
    }

    @Override
    public String DATA_GetPatientLatestData(@WebParam(name="patientID") int patientID) {
        long beginTime = System.currentTimeMillis();

        List datas = dataService.getPatientLatestData(patientID);
        return new ServiceResult1(true, System.currentTimeMillis() - beginTime, "data", datas).toJson();
        //return new ServiceResult(true, dataService.getPatientLatestData(patientID)).toJson();
    }

    @Override
    public String DATA_GetTerminalLatestData(String terminalNum) {
        long beginTime = System.currentTimeMillis();

        List datas = dataService.GetTerminalLatestData(terminalNum);
        return new ServiceResult1(true, System.currentTimeMillis() - beginTime, "data", datas).toJson();
    }

    @Override
    public String DATA_SetHandleState(List<Integer> dataIDs, int state, int userid) {
        long beginTime = System.currentTimeMillis();

        boolean result = dataService.setHandleState(dataIDs, state, userid);
        return new ServiceResult1(result, System.currentTimeMillis() - beginTime, "", null).toJson();
        //return new ServiceResult(dataService.setHandleState(dataIDs, state, userid), null).toJson();
    }

    @Override
    public String DATA_GetOnePatientData(int patientID, String begeindate, String enddate, int state, int minseconds) {
        long beginTime = System.currentTimeMillis();

        List datas = dataService.getOnePatientData(patientID, begeindate, enddate, state, minseconds);
        return new ServiceResult1(true, System.currentTimeMillis() - beginTime, "datas", datas).toJson();
        //return new ServiceResult(true, dataService.getOnePatientData(patientID, begeindate, enddate, state, minseconds)).toJson();
    }

    @Override
    public String Data_HasNewFileToDownload(int patientID, int datatype, long filelength) {
        long beginTime = System.currentTimeMillis();

        HasNewFileToDownload hasNewFileToDownload = dataService.hasNewFileToDownload(patientID, datatype, filelength);
        return new ServiceResult1(true, System.currentTimeMillis() - beginTime, "hasnew", hasNewFileToDownload.getHasnew()).toJson();
        //return new ServiceResult(true, dataService.hasNewFileToDownload(patientID, datatype, filelength)).toJson();
    }

    @Override
    public String Data_HasNewFileToDownload1(@WebParam(name="fileName") String fileName,
                                      @WebParam(name="filelength") long filelength){
        long beginTime = System.currentTimeMillis();

        HasNewFileToDownload hasNewFileToDownload = dataService.hasNewFileToDownload(fileName, filelength);
        return new ServiceResult1(true, System.currentTimeMillis() - beginTime, "hasnew", hasNewFileToDownload.getHasnew()).toJson();
        //return new ServiceResult(true, dataService.hasNewFileToDownload(fileName, filelength)).toJson();
    }

    @Override
    public String ShuangJia_EcgReport(String reportCode, String reportData) {
        return dataService.ecgReport(reportCode, reportData);
    }

    @Override
    public String TERMINAL_GetAllTerminalInfo(int userID) {
        long beginTime = System.currentTimeMillis();

        List terminalInfo = terminalService.getAllTerminalInfo(userID);
        ServiceResult1 result = new ServiceResult1(true, System.currentTimeMillis()-beginTime, "terminalinfo", terminalInfo);
        result.put("user", userService.getUser(userID));
        return result.toJson();
        //return new ServiceResult(true, terminalService.getAllTerminalInfo(userID) ).toJson();
    }

    @Override
    public byte[] TERMINAL_GetAllTerminalInfo1(@WebParam(name = "userID") int userID) {
        long beginTime = System.currentTimeMillis();

        List terminalInfo = terminalService.getAllTerminalInfo(userID);
        ServiceResult1 result = new ServiceResult1(true, System.currentTimeMillis()-beginTime, "terminalinfo", terminalInfo);
        result.put("user", userService.getUser(userID));
        return CompressUtil.compressData(result.toJson());
    }

    @Override
    public String TERMINAL_GetTerminal(String terminalNumber) {
        long beginTime = System.currentTimeMillis();

        Terminal terminal = terminalService.getTerminal(terminalNumber);
        return new ServiceResult1(true, System.currentTimeMillis() - beginTime, "terminal", terminal).toJson();
        //return new ServiceResult(true, terminalService.getTerminal(terminalNumber)).toJson();
    }

    @Override
    public String SUBFACILITIES_GetAllDepartmentInfo(int userID) {
        long beginTime = System.currentTimeMillis();

        List subfacilities = subfacilityService.getAllDepartmentInfo(userID);
        return new ServiceResult1(true, System.currentTimeMillis() - beginTime, "subfacilitieslist", subfacilities).toJson();
        //return new ServiceResult(true, subfacilityService.getAllDepartmentInfo(userID)).toJson();
    }
}
