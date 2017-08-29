package com.koanruler.mp.service;

import com.koanruler.mp.entity.*;
import com.koanruler.mp.repository.DataRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
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

    //分页获取某个用户及其下属机构的病人的数据，并根据终端编号或者病人姓名进行过滤
	public List<DataInfo> getOneGroupData(int userID, String terminalNumOrPatientName, int firstIndex, long count) {
		List<Integer> userIds = userService.getAllChildID(userID);
		userIds.add(0, userID);

		List<Integer> patientIds = patientService.getPatientIds(userIds);
		if(patientIds.size() == 0) {
            return null;
        }

        QData qData = QData.data;
		QPatient qPatient = QPatient.patient;
        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and( qData.patientid.in(patientIds).and(qPatient.id.in(patientIds)) );
        if( terminalNumOrPatientName != null && !terminalNumOrPatientName.isEmpty() )
            predicate.and(qData.terminalnum.contains(terminalNumOrPatientName).or(qPatient.name.contains(terminalNumOrPatientName)));

        QueryResults<Tuple> results = queryFactory.select(qData.id, qData.patientid, qData.type, qData.terminalnum, qData.filename, qData.createdate, qData.endtime, qPatient.name)
                .from(qData)
                .join(qPatient)
                .on(qData.patientid.eq(qPatient.id))
                // .where(predicate)
                .offset(firstIndex)
                .limit(count)
                .fetchResults();

        //results.getTotal(); //总数
        return results.getResults().stream().map(row -> new DataInfo(row.get(qData.id),
                row.get(qData.patientid),
                row.get(qData.type),
                row.get(qData.terminalnum),
                row.get(qData.filename),
                row.get(qData.createdate),
                row.get(qData.endtime),
                row.get(qPatient.name))).collect(Collectors.toList());

        //results.getTotal(); //总数
        //return results.getResults();
	}

    private User GetUser(int userid, List<User> users){
        for(User user: users){
            if(user.getId().equals( userid ))
                return user;
        }

        return null;
    }

    private String GetHandleStateDesc(Patient patientDataInfo, List<User> users) {

        String userName = "";
        User user = GetUser(patientDataInfo.getHandleuserid(), users);
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

    public List<SimplePatientInfo> searchReplayInfo1(int userID, DataSearchCondition searchCondition) {

        List<Integer> userIds = userService.getAllChildID(userID);
        userIds.add(0, userID);

        QPatient qPatient = QPatient.patient;

        BooleanBuilder predicate = new BooleanBuilder();
        if (searchCondition != null) {
            predicate.and(queryFactory.selectFrom(QData.data).where(QData.data.patientid.eq(QPatient.patient.id).and(QData.data.type.eq(searchCondition.type))).exists());
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
        }

        List<Patient> patients = null;
        List<User> users = userService.getAllAnalysts();
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
            User user = GetUser( patient.getUserid(), users );

            if (user != null) {
                if (user.getType() == 4) {   //医院
                    hospital = user.getName();
                } else if (user.getType() == 5) {   //科室
                    department = user.getName();
                    User parentUser = GetUser(user.getParentuserid(), users);
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

    List<Data> getDatasByPatienIdAndType(int patientID, int type) {
        return dataRepository.findByPatientidAndType(patientID, type);
    }

    boolean setHandleState(List<Integer> dataIDs, int state, int userid) {
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

    List<Data> getOnePatientData(int patientID, String begeindate, String enddate, int state, int minseconds) {

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
    HasNewFileToDownload hasNewFileToDownload(int patientID, int datatype, long filelength) {

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

    HasNewFileToDownload hasNewFileToDownload(String fileName, long filelength) {

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

    String ecgReport(String reportCode, String reportData) {
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
}
