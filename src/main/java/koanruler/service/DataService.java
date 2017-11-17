package koanruler.service;

import koanruler.repository.DataRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import koanruler.entity.*;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DataService {

    @Value("${datapath}")
    private String dataPath;
	
	@Autowired
	private DataRepository dataRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PatientService patientService;

	@Autowired
    private JPAQueryFactory queryFactory;

	public long getCount() {		
		return dataRepository.count();
	}

    /**
     * 根据病人姓名或者终端编号分页获取用户及下属机构的数据信息
     * @param userID: 要搜索的用户ID
     * @param terminalNumOrPatientName：病人姓名或者终端编号
     * @param firstIndex：要搜索的第一条记录的索引
     * @param count：要搜索的数据量
     * @return：满足搜索条件的数据总数和本次搜索的数据信息
     */
	public ResultList<DataInfo> getOneGroupData(int userID, String terminalNumOrPatientName, int firstIndex, long count) {
		List<Integer> patientIds = patientService.getPatientIds( userService.getAllChildID(userID) );
		if(patientIds.size() == 0) {
            return null;
        }

        QData qData = QData.data;
		QPatient qPatient = QPatient.patient;

        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and( qData.patientid.in(patientIds) );

        if( terminalNumOrPatientName != null && !terminalNumOrPatientName.isEmpty() )
            predicate.and(qData.terminalnum.contains(terminalNumOrPatientName)
                               .or(qPatient.name.contains(terminalNumOrPatientName)));

        QueryResults<Tuple> results = queryFactory.select(qData, qPatient)
                                                .from(qData)
                                                .join(qPatient)
                                                .on(qData.patientid.eq(qPatient.id))
                                                .where(predicate)
                                                .orderBy(qData.id.desc())
                                                .offset(firstIndex)
                                                .limit(count)
                                                .fetchResults();

        return new ResultList<>(results.getTotal(), results.getResults()
                                                           .stream()
                                                           .map(row -> {
                                                                Data data = row.get(qData);
                                                                Patient patient = row.get(qPatient);

                                                                return new DataInfo(data.getId(),
                                                                        data.getPatientid(),
                                                                        data.getType(),
                                                                        data.getTerminalnum(),
                                                                        data.getFilename(),
                                                                        data.getCreatedate(),
                                                                        data.getEndtime(),
                                                                        patient.getName());
                                                            }).collect(Collectors.toList()));

//        return results.getResults().stream().map(row -> new DataInfo(row.get(qData.id),
//                row.get(qData.patientid),
//                row.get(qData.type),
//                row.get(qData.terminalnum),
//                row.get(qData.filename),
//                row.get(qData.createdate),
//                row.get(qData.endtime),
//                row.get(qPatient.name))).collect(Collectors.toList());

        //results.getTotal(); //总数
	}

    /**
     * 获取病人的处理状态描述
     * @param patientDataInfo 病人信息
     * @param users 用户数据
     * @return 处理状态描述
     */
    private String GetHandleStateDesc(Patient patientDataInfo, Map<Integer, User> users) {

        String userName = "";
        User user = users.get(patientDataInfo.getHandleuserid());
        if (user != null)
            userName = user.getName();

        switch(patientDataInfo.getHandlestate()){
            case 0:
                return patientDataInfo.getRecordstarttime() + "    " + "未处理";
            case 1:
                return patientDataInfo.getHandlestarttime() + "    " + userName + "正在处理";
            case 2:
                return patientDataInfo.getHandlestarttime() + "    " + userName + "完成处理";
            case 3:
                return patientDataInfo.getHandlestarttime() + "    " + userName + "更新处理";
            case 4:
                return patientDataInfo.getHandlestarttime() + "    " + userName + "中断处理";
            //case 5:
            //	return patientDataInfo.getBegintime() + "    " + userName + "正在处理";
            //case 6:
            //	return patientDataInfo.getBegintime() + "    " + userName + "正在处理";
            case 7:
                return patientDataInfo.getHandlestarttime() + "    " + userName + "正在处理  有新数据";
            case 8:
                return patientDataInfo.getHandlestarttime() + "    " + userName + "完成处理  有新数据";
            default:
                return "";
        }
    }

    /**
     * 根据病人姓名、床号、医院、科室、处理状态等获取用户及下属机构的病人处理状态数据
     * @param userID 用户ID
     * @param searchCondition 搜索条件：病人姓名、床号、医院、科室、处理状态等
     * @return 病人处理状态数据列表
     */
    public List<SimplePatientInfo> searchReplayInfo1(int userID, PatientDataSearchCondition searchCondition) {

        List<Integer> userIds = userService.getAllChildID(userID);
        QPatient qPatient = QPatient.patient;

        BooleanBuilder predicate = new BooleanBuilder();
        if (searchCondition != null) {
            if (searchCondition.state != 5)
                predicate.and(qPatient.handlestate.eq(searchCondition.state));
            if (searchCondition.bednum != null && !searchCondition.bednum.isEmpty())
                predicate.and(qPatient.bednumber.contains(searchCondition.bednum));
            if (searchCondition.patientname != null && !searchCondition.patientname.isEmpty())
                predicate.and(qPatient.name.contains(searchCondition.patientname));
            if (searchCondition.departmentid != 0) {
                predicate.and(qPatient.userid.eq(searchCondition.departmentid));
            } else if (searchCondition.hospitalid != 0) {
                List<Integer> userList = queryFactory.select(QUser.user.id).from(QUser.user)
                        .where(QUser.user.parentuserid.eq(searchCondition.hospitalid))
                        .fetch();
                userList.add(searchCondition.hospitalid);
                predicate.and(qPatient.userid.in(userList));
            } else {
                predicate.and(qPatient.userid.in(userIds));
            }

            predicate.and(queryFactory.selectFrom(QData.data).where(QData.data.patientid.eq(QPatient.patient.id).and(QData.data.type.eq(searchCondition.type))).exists());
        }

        List<Patient> patients;
        Map<Integer, User> users = userService.getAllParentsAndChildrenAndAnalysts(userID);
        if (searchCondition != null && searchCondition.patientcount > 0) {
            patients = queryFactory.selectFrom(qPatient)
                                    .where(predicate)
                                    .limit(searchCondition.patientcount)
                                    .orderBy(qPatient.id.desc())
                                    .fetch();
        }
        else {
            patients = queryFactory.selectFrom(qPatient)
                                    .where(predicate)
                                    .orderBy(qPatient.id.desc())
                                    .fetch();
        }

        return patients.stream().map(patient -> {

            String hospital = "", department = "";
            User user = users.get( patient.getUserid() );

            if (user != null) {
                if (user.getType() == 4) {   //医院
                    hospital = user.getName();
                } else if (user.getType() == 5) {   //科室
                    department = user.getName();
                    User parentUser = users.get(user.getParentuserid());
                    if (parentUser != null)
                        hospital = parentUser.getName();
                }
            }

            return new SimplePatientInfo(patient.getId(), patient.getName(), patient.getAge(),
                    patient.getSex(), patient.getBednumber(), patient.getBirthday(),
                    patient.getOutpatientnumber(), patient.getHospitalnumber(), hospital,
                    department, patient.getSymptom(), patient.getHandlestarttime(),
                    patient.getRecordstarttime(), patient.getRecordendtime(), patient.getHandleuserid(),
                    patient.getHandlestate(), GetHandleStateDesc(patient, users));
        }).collect(Collectors.toList());
    }

    /**
     * 数据时长是否大于等于minseconds
     * @param data 数据信息
     * @param minseconds 比较的时长
     * @return
     */
    private boolean IsDataTimeSpanEnough(Data data, int minseconds){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date createtime;
        try {
            createtime = sdf.parse( data.getCreatedate() );
            Date endtime = sdf.parse( data.getEndtime() );

            long diff = ( endtime.getTime() - createtime.getTime() ) / 1000;
            return (diff >= minseconds);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据病人ID和数据类型获取数据
     * @param patientID 病人ID
     * @param type 数据类型
     * @return 数据列表
     */
    public List<Data> getDatasByPatienIdAndType(int patientID, int type) {
        return dataRepository.findByPatientidAndType(patientID, type);
    }

    /**
     * 设置数据的处理状态
     * @param dataIDs 数据ID
     * @param state 处理状态
     * @param userid 进行处理的用户
     * @return
     */
    public boolean setHandleState(List<Integer> dataIDs, int state, int userid) {
        Date date = new Date();
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);

        for(int dataID: dataIDs){

            Data data = dataRepository.findOne(dataID);
            Patient patient = patientService.getPatient(data.getPatientid());
            if (state == 1){ //eHandling
                data.setHandlestate(state);
                data.setUserid(userid);

                patient.setHandlestate(state);
                patient.setHandleuserid(userid);

                if ( data.getHandlestate() == 0){ //eNotHandle
                    data.setHandlestarttime(time);
                    patient.setHandlestarttime(time);
                }
            }
            else if (state == 2 /*eFinishHandle*/ || state == 3 /*eUpdateHandle*/){
                data.setHandlestate(state);
                data.setHandleendtime(time);
                patient.setHandlestate(state);
            }
            else
            {
                data.setHandlestate(state);
                patient.setHandlestate(state);
            }

            dataRepository.save(data);
            patientService.Save(patient);
        }

        return true;
    }

    /**
     * 根据数据处理状态、起始时间和结束时间获取某个病人的数据信息
     * @param patientID 病人ID
     * @param begeindate 起始时间
     * @param enddate 结束时间
     * @param state 处理状态
     * @param minseconds 如果数据时长小于minseconds，则忽略该数据
     * @return 数据列表
     */
    public List<Data> getOnePatientData(int patientID, String begeindate, String enddate, int state, int minseconds) {

        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and(QData.data.patientid.eq(patientID));
        if(begeindate.length() > 0){
            predicate.and(QData.data.createdate.goe(begeindate));
        }
        if(enddate.length() > 0){
            predicate.and(QData.data.endtime.loe(enddate));
        }
        if(state != 5){
            predicate.and(QData.data.handlestate.eq(state));
        }

        return queryFactory.selectFrom(QData.data)
                    .where(predicate)
                    .orderBy(QData.data.id.desc())
                    .fetch()
                    .stream()
                    .filter(data -> IsDataTimeSpanEnough(data, minseconds)).collect(Collectors.toList());
    }

    private String []extention = {".mpm", ".mpr", ".mpe", ".mpb"};

    /**
     * 是否有新文件下载(deprecated)
     * @param patientID 病人ID
     * @param datatype 数据类型
     * @param filelength 文件长度
     * @return
     */
    public HasNewFileToDownload hasNewFileToDownload(int patientID, int datatype, long filelength) {

        String filename = dataRepository.findByPatientidAndTypeOrderByIdDesc(patientID, datatype).get(0).getFilename();
        String yearandmonthfolder = filename.substring(0, 6);
        String datefolder = filename.substring(6, 8);

        try {
            File file = new File(dataPath + yearandmonthfolder + "\\" + datefolder + "\\" + filename + extention[datatype]);

            if( filelength != file.length() )
                return new HasNewFileToDownload(1);
            else
                return new HasNewFileToDownload(0);
        } catch (Exception e) {
            return new HasNewFileToDownload(0);
        }
    }

    /**
     * 是否有新文件下载
     * @param fileName 文件名
     * @param filelength 文件长度
     * @return
     */
    public HasNewFileToDownload hasNewFileToDownload(String fileName, long filelength) {

        String yearAndMonthFolder = fileName.substring(0, 6);
        String dateFolder = fileName.substring(6, 8);

        try {
            File file = new File(dataPath + yearAndMonthFolder + "\\" + dateFolder + "\\" + fileName);

            if( filelength != file.length() )
                return new HasNewFileToDownload(1);
            else
                return new HasNewFileToDownload(0);
        } catch (Exception e) {
            return new HasNewFileToDownload(0);
        }
    }

    public String ecgReport(String reportCode, String reportData) {
        String strEcgReport = "";

        CloseableHttpClient httpclient = HttpClients.createDefault();

        // 创建httppost
        HttpPost httppost = new HttpPost("http://hub2.ubody.net/expert/post_ecg_report");
        httppost.addHeader("Origin", "http://c.ubody.net");
        // 创建参数队列
        List<NameValuePair> formparams = new ArrayList<>();
        formparams.add(new BasicNameValuePair("ReportCode", reportCode));
        formparams.add(new BasicNameValuePair("ReportData", reportData));
        UrlEncodedFormEntity uefEntity;

        try {
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
            httppost.setEntity(uefEntity);

            try (CloseableHttpResponse response = httpclient.execute(httppost)){
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    strEcgReport = EntityUtils.toString(entity, "UTF-8");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return strEcgReport;
    }

    public List<Data> getPatientLatestData(int patientID) {
        return dataRepository.findTopByPatientidOrderByIdDesc(patientID);
    }

    public List<Data> GetTerminalLatestData(String terminalNum) {
        return dataRepository.findTopByTerminalnumOrderByIdDesc(terminalNum);
    }
}
