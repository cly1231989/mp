package koanruler.webservice;

import koanruler.entity.Patient;
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
    //获取用户名
    @WebMethod
    String USER_GetUserName(@WebParam(name = "userID") int userID);

    @WebMethod
    String USER_GetUser( @WebParam(name="userID") int userID );

    //获取所有下属机构的信息(id, type, name)
    @WebMethod
    String USER_GetSubDepartmentInfo(@WebParam(name = "userID") int userID);

    /**************************************************************************/
    //获取某一用户的病人数量，并根据病人姓名和住院状态进行过滤
    @WebMethod
    String PATIENT_GetCount(@WebParam(name = "userID") int userID,
                            @WebParam(name = "patientName") String patientName,
                            @WebParam(name = "inhospital") boolean inhospital);

    //根据id获取病人信息
    @WebMethod
    String PATIENT_GetBindPatientInfo(@WebParam(name = "patientIDList") List<Integer> patientIDList);

    //分布获取某一用户下的病人信息
    @WebMethod
    String PATIENT_GetOneGroupPatientInfo(@WebParam(name = "userID") int userID,
                                          @WebParam(name = "patientName") String patientName,
                                          @WebParam(name = "inhospital") boolean inhospital,
                                          @WebParam(name = "firstPatientIndex") int firstPatientIndex,
                                          @WebParam(name = "patientCount") int patientCount);
//    //获取某一用户的所有病人信息
//    @WebMethod
//    String PATIENT_GetAllPatientInfo(@WebParam(name = "userID") int userID);

    //添加病人
    @WebMethod
    String PATIENT_AddPatient(@WebParam(name = "patientinfo") Patient patientInfo);

    ////根据id获取病人信息
    @WebMethod
    String PATIENT_GetPatientInfo(@WebParam(name = "patientID") int patientID);

    /**************************************************************************/
    //获取某一用户及其下属机构的数据，并根据条件过滤
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

//    //获取某一用户及其下属机构的病人及对应数据
//    @WebMethod
//    String DATA_GetOneGroupPatientInfo(@WebParam(name = "userID") int userID,
//                                       @WebParam(name = "count") int count,
//                                       @WebParam(name = "minseconds") int minseconds);

    //获取某一病人的数据
    @WebMethod
    String DATA_GetPatientData(@WebParam(name = "patientID") int patientID, @WebParam(name = "type") int type);

    //获取某一病人最新的数据
    String DATA_GetPatientLatestData(@WebParam(name="patientID") int patientID);

    //设置数据的处理状态及处理用户
    @WebMethod
    String DATA_SetHandleState(@WebParam(name = "dataIDs") List<Integer> dataIDs,
                               @WebParam(name = "state") int state,
                               @WebParam(name = "userid") int userid);

    //获取某一病人的限定日期的数据
    @WebMethod
    String DATA_GetOnePatientData(@WebParam(name = "patientID") int patientID,
                                  @WebParam(name = "begeindate") String begeindate,
                                  @WebParam(name = "enddate") String enddate,
                                  @WebParam(name = "state") int state,
                                  @WebParam(name = "minseconds") int minseconds);

    //某一病人是否有新文件需要下载
    @WebMethod
    String Data_HasNewFileToDownload(@WebParam(name = "patientID") int patientID,
                                     @WebParam(name = "datatype") int datatype,
                                     @WebParam(name = "filelength") long filelength);

    @WebMethod
    String Data_HasNewFileToDownload1(@WebParam(name="fileName") String fileName,
                                      @WebParam(name="filelength") long filelength);

    /**************************************************************************/
    //双佳打印报告
    @WebMethod
    String ShuangJia_EcgReport(@WebParam(name = "ReportCode") String ReportCode, @WebParam(name = "ReportData") String ReportData);

    /**************************************************************************/
    //获取某一用户的所有终端
    @WebMethod
    String TERMINAL_GetAllTerminalInfo(@WebParam(name = "userID") int userID);

    //根据id获取终端信息
    @WebMethod
    String TERMINAL_GetTerminal(@WebParam(name = "terminalID") String terminalID);

    /**************************************************************************/
    //获取某一用户的所有科室
    @WebMethod
    String SUBFACILITIES_GetAllDepartmentInfo(@WebParam(name = "userID") int userID);
}
