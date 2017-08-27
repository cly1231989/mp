package com.koanruler.mp.service;

import com.koanruler.mp.entity.*;
import com.koanruler.mp.repository.DataRepository;
import com.koanruler.mp.util.TypeConverter;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
	
	@PersistenceContext
	private EntityManager em;

	@Autowired
    private JPAQueryFactory queryFactory;

	public long getCount() {		
		return dataRepository.count();
	}

	//获取某个用户及其下属机构的病人的数量，并根据终端编号或者病人姓名进行过滤
	public Long getDataCount(int userID, String terminalNumOrPatientName) {
		List<Integer> userIds = userService.getAllChildID(userID);
		userIds.add(0, userID);

		List<Integer> patientIds = patientService.getPatientIds(userIds);
		if(patientIds.size() > 0){

            String sql = "Select d.id from Data d where (d.patientid IN :patientIDList";
            if(terminalNumOrPatientName != null && terminalNumOrPatientName.length() > 0) {
                sql += " and d.terminalnum like '%" + terminalNumOrPatientName + "%') or ( d.patientid IN (select p.id from Patient p where p.id In :patientIDList and p.name like '%" + terminalNumOrPatientName + "%')";
            }

            sql += ")";
			Query query = em.createQuery(sql);
            query.setParameter("patientIDList", patientIds);

            return Long.valueOf( query.getResultList().size() );
            //return (Long) query.getSingleResult();
		}

		return 0L;
	}

    //分页获取某个用户及其下属机构的病人的数据，并根据终端编号或者病人姓名进行过滤
	public List<DataInfo> getOneGroupData(int userID, String terminalNumOrPatientName, int firstIndex, int count) {
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

        QueryResults results = queryFactory.select(Projections.constructor(DataInfo.class, qData.id, qData.patientid, qData.type, qData.terminalnum, qData.filename, qData.createdate, qData.endtime, qPatient.name))
                    .from(qData)
                    .join(qPatient)
                    .on(qData.patientid.eq(qPatient.id))
                    .where(predicate)
                    .offset(firstIndex)
                    .limit(count).fetchResults();

        //results.getTotal(); //总数
        return results.getResults();

//        String ids = String.join(",", patientIds.stream().map(id -> String.valueOf(id)).collect(Collectors.toList()));
//        String sql = "Select d.id as dataId, d.patientid as patientId, d.type as dataType, d.terminalnum as terminalNum, d.filename as fileName, d.createdate as createData, d.endtime as endTime, "
//                + "p.name as patientName from Data d inner join Patient p on d.patientid = p.id where (d.patientid IN (" + ids + ") and p.id IN (" + ids + "))";
//        if(terminalNumOrPatientName != null && terminalNumOrPatientName.length() > 0)
//            sql += " and (d.terminalnum like '%" + terminalNumOrPatientName + "%' or p.name like '%" + terminalNumOrPatientName + "%' ) ";
//
//        sql += " limit "+ (firstIndex-1) + "," + count;
//        return em.createNativeQuery(sql, "DataInfoMapping").getResultList();

        //resultListToDataInfoList(dataList, dataInfoList);
        //return dataInfoList;
	}

//	private void resultListToDataInfoList(List dataList, List<DataInfo> replayInfoList){
//		for (Iterator iter=dataList.iterator(); iter.hasNext();) {
//		    Object[] obj = (Object[])iter.next();
//		    DataInfo replayInfo = new DataInfo();
//            replayInfo.setDataId((Integer)obj[0]);
//            replayInfo.setPatientId((Integer)obj[1]);
//            replayInfo.setTerminalNum((String)obj[2]);
//            replayInfo.setFileName((String )obj[3]);
//            replayInfo.setDataType((Integer)obj[4]);
//            replayInfo.setCreateData((String)obj[5]);
//            replayInfo.setEndTime((String)obj[6]);
//            replayInfo.setPatientName((String)obj[7]);
//
//		    replayInfoList.add(replayInfo);
//		}
//	}

	//获取某个用户及下属机构的病人信息和对应的数据信息，并根据条件过滤
    //只获取病人信息，在需要的时候再获取数据信息
	public List<PatientDataInfo> searchPatientDataInfo(int userID, DataSearchCondition searchCondition) {
        long beginTime1 = System.currentTimeMillis();
		List<Integer> userIds = userService.getAllChildID(userID);
		userIds.add(0, userID);

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
        }

		List<Patient> result = queryFactory.selectFrom(qPatient)
                    .where(predicate)
                    .limit(searchCondition.patientcount)
                    .orderBy(qPatient.id.desc())
                    .fetch();

        List<PatientDataInfo> patientDataInfoList = new ArrayList<PatientDataInfo>();

        String sql =   "SELECT "
                + "p.id AS p_id, "
                + "p.name AS p_name, "
                + "p.age AS p_age, "
                + "p.sex AS p_sex, "
                + "p.userid AS p_userid, "
                + "p.bednumber AS b_bednum, "
                + "p.birthday AS p_birthday, "
                + "p.outpatientnumber AS p_outpatientnumber, "
                + "p.hospitalnumber AS p_hospitalnumber, "
                + "p.symptom AS p_symptom, "
                + "d.id AS d_id, "
                + "d.type AS d_type, "
                + "d.transferflag AS d_transferflag, "
                + "d.filename AS d_filename, "
                + "d.createdate AS d_createdate, "
                + "d.endtime AS d_endtime, "
                + "d.handlestate AS d_handlestate, "
                + "d.userid AS d_userid, "
                + "d.handlestarttime AS d_handlestarttime, "
                + "d.handleendtime AS d_handleendtime "
                + "FROM "
                + "`data` AS d "
                + "INNER JOIN ( "
                + "SELECT "
                + "* "
                + "FROM "
                + "`patient` "
                + "WHERE (";

        if(searchCondition.departmentid != 0){
            sql += "patient.userid = " + searchCondition.departmentid;
        }
        else if(searchCondition.hospitalid != 0){
            String usersql = "select u.id from User u where u.parentuserid = " + searchCondition.hospitalid;
            List<Integer> departmentIDList = em.createQuery(usersql).getResultList();
            departmentIDList.add(searchCondition.hospitalid);

            String departmentIds = String.join(",", departmentIDList.stream().map(id -> String.valueOf(id)).collect(Collectors.toList()));
            sql += "patient.userid in (" + departmentIds + ") ";
        }
        else{
            String ids = String.join(",", userIds.stream().map(id -> String.valueOf(id)).collect(Collectors.toList()));
            sql += "patient.userid in (" + ids + ") ";
        }

        sql += " ) ";
        if(searchCondition.patientname.length() > 0)
            sql += " and patient.name like '%" + searchCondition.patientname + "%'";
        if(searchCondition.bednum.length() > 0)
            sql += " and patient.bednumber = " + searchCondition.bednum;

        if(searchCondition.state != 5){ //不是查找所有状态的数据
            if(searchCondition.state == 1){
                sql += " and exists (select 1 from data where data.patientid = patient.id and data.handlestate=1";
                sql += " and data.type=" + searchCondition.type + ")";
            }
            else{
                if(searchCondition.state == 0)
                    sql += " and exists (select 1 from data where data.patientid = patient.id and (data.handlestate=0 or data.handlestate is null)";
                else
                    sql += " and exists (select 1 from data where data.patientid = patient.id and data.handlestate=" + searchCondition.state;

                sql += " and data.endtime != '0000-00-00 00:00:00' and data.type=" + searchCondition.type + ")";
                sql += " and not exists (select 1 from data where data.patientid = patient.id and data.handlestate=1 and data.type=" + searchCondition.type + ")";
            }
        }
        else{
            sql += " and exists (select 1 from data where data.patientid = patient.id and data.endtime != '0000-00-00 00:00:00' and data.type=" + searchCondition.type + ")";
        }

        sql += " ORDER BY patient.id DESC ";
        if(searchCondition.patientcount > 0)
            sql += "LIMIT " + searchCondition.patientcount;
        sql += ") AS p ON d.patientid = p.id where d.type = " + searchCondition.type;
        if(searchCondition.state != 5)
            sql += " and d.handlestate = " + searchCondition.state;
        sql += " and d.endtime != '0000-00-00 00:00:00'";
        sql += " order by p.id DESC, d.id DESC";
        //System.out.println( "make sql time: " + (System.currentTimeMillis() - beginTime1));

        long beginTime3 = System.currentTimeMillis();
        List<User> users = userService.getAllAnalysts();
        users.addAll( userService.getAllUser(userIds) );
        //System.out.println( "get user time: " + (System.currentTimeMillis() - beginTime3));

        long beginTime4 = System.currentTimeMillis();
        List<Object[]> results = em.createQuery(sql).getResultList();
        //System.out.println( "get data time: " + (System.currentTimeMillis() - beginTime4));

        long beginTime2 = System.currentTimeMillis();
        int previousPID = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for(int i = 0; i < results.size(); i++)
        {
            Object[] record = results.get(i);

            //p.id p.name p.age p.sex p.userid p.bednumber p.birthday p.outpatientnumber
            //p.hospitalnumber p.symptom d.id d.type d.transferflag d.filename d.createdate d.endtime

            try {
                Date createtime = sdf.parse( TypeConverter.objectToString(record[14]) );
                Date endtime = sdf.parse( TypeConverter.objectToString(record[15]) );

                long diff = ( endtime.getTime() - createtime.getTime() ) / 1000;
                if(diff >= searchCondition.minseconds){
                    int PID =  TypeConverter.objectToInt(record[0]);
                    if(PID != previousPID){
                        PatientDataInfo patientDataInfo = new PatientDataInfo();
                        patientDataInfo.patientinfo.setId(PID);
                        patientDataInfo.patientinfo.setName( TypeConverter.objectToString(record[1]) );
                        patientDataInfo.patientinfo.setAge( TypeConverter.objectToString(record[2]) );
                        patientDataInfo.patientinfo.setSex( TypeConverter.objectToString(record[3]) );
                        //patientDataInfo.patientinfo.setu(record[4].toString());
                        patientDataInfo.patientinfo.setBednum( TypeConverter.objectToString(record[5]) );
                        patientDataInfo.patientinfo.setBirthday( TypeConverter.objectToString(record[6]) );
                        patientDataInfo.patientinfo.setOutpatientnumber( TypeConverter.objectToString(record[7]) );
                        patientDataInfo.patientinfo.setHospitalnumber( TypeConverter.objectToString(record[8]) );
                        patientDataInfo.patientinfo.setSymptom( TypeConverter.objectToString(record[9]) );

                        User user = GetUser( TypeConverter.objectToInt(record[4]), users );
                        if(user.getType() == 5){   //科室
                            patientDataInfo.patientinfo.setDepartment(user.getName());
                            int parentID = user.getParentuserid();
                            patientDataInfo.patientinfo.setHospital( GetUser(parentID, users).getName() );
                        }else if(user.getType() == 4){   //医院
                            patientDataInfo.patientinfo.setDepartment("");
                            patientDataInfo.patientinfo.setHospital(user.getName());
                        }

                        patientDataInfoList.add(patientDataInfo);
                        previousPID = PID;
                    }

                    //10-d.id d.type d.transferflag d.filename d.createdate d.endtime
                    //int id, int type, String filename, int transferflag, String createdate,String endtime
                    DataIDAndFileName dataIDAndFileName = new DataIDAndFileName( TypeConverter.objectToInt(record[10]) ,
                            TypeConverter.objectToInt(record[11]) ,
                            TypeConverter.objectToString(record[13]) ,
                            TypeConverter.objectToInt(record[12]),
                            TypeConverter.objectToString(record[14]) ,
                            TypeConverter.objectToString(record[15]),
                            TypeConverter.objectToInt(record[16]),
                            TypeConverter.objectToInt(record[17]),
                            TypeConverter.objectToString(record[18]),
                            TypeConverter.objectToString(record[19]));
                    patientDataInfoList.get(patientDataInfoList.size()-1).datas.add(dataIDAndFileName);
                }
            } catch (ParseException e) {
                e.printStackTrace();
                continue;
            }
        }

        for(int i = 0; i < patientDataInfoList.size(); i++){
            patientDataInfoList.get(i).patientinfo.setBegintime( patientDataInfoList.get(i).datas.get(patientDataInfoList.get(i).datas.size() - 1).getCreatedate() );
            patientDataInfoList.get(i).patientinfo.setEndtime( patientDataInfoList.get(i).datas.get(0).getEndtime() );
            patientDataInfoList.get(i).patientinfo.setState( GetHandleStateDesc(patientDataInfoList.get(i).datas, users) );
        }
        //System.out.println( "parse data time: " + (System.currentTimeMillis() - beginTime2));
        return patientDataInfoList;
	}

    private User GetUser(int userid, List<User> users){
        for(User user: users){
            if(user.getId().equals( userid ))
                return user;
        }

        return null;
    }

    private String GetHandleStateDesc(List<DataIDAndFileName> datalist, List<User> users){
        String desc = "";
        int  nDataHandlingIndex = -1;
        int  nDataNotHanldeIndex = -1;
        int  nDataStopHandleIndex = -1;
        int  nDataUpdateHandleIndex = -1;
        int  nDataFinishHandleIndex = -1;

        int i = -1;
        for(DataIDAndFileName data: datalist){
            i++;
            if(data.getHstate() == null){
                nDataNotHanldeIndex = i;
            }
            else if (data.getHstate() == 1)
            {
                nDataHandlingIndex = i;
            }
            else if (data.getHstate() == 0)
            {
                nDataNotHanldeIndex = i;
            }
            else if (data.getHstate() == 4)
            {
                nDataStopHandleIndex = i;
            }
            else if (data.getHstate() == 3)
            {
                nDataUpdateHandleIndex = i;
            }
            else if(data.getHstate() == 2)
            {
                nDataFinishHandleIndex = i;
            }
        }

        if (nDataHandlingIndex != -1){
            //Query query = getSession().createQuery( "From User u WHERE u.id = " + datalist.get(nDataHandlingIndex).getUserid() );
            //User user = (User) query.uniqueResult();
            int userid = datalist.get(nDataHandlingIndex).getUserid();
            desc = datalist.get(nDataHandlingIndex).getHstime() + "    " + GetUser(userid, users).getName() + "正在处理";
            if( datalist.get(0).getHstate() == null || datalist.get(0).getHstate() == 0 ){
                desc += "    有新数据";
            }
        }
        else if (nDataNotHanldeIndex != -1){
            desc = datalist.get(nDataNotHanldeIndex).getCreatedate() + "    " + "未处理";
        }
        else if (nDataStopHandleIndex != -1){
            desc = datalist.get(nDataStopHandleIndex).getCreatedate() + "    " + "中止处理";
        }
        else if (nDataUpdateHandleIndex != -1){
            desc = datalist.get(nDataUpdateHandleIndex).getHetime() + "    " + "更新处理";
        }
        else if(nDataFinishHandleIndex != -1){
            desc = datalist.get(nDataFinishHandleIndex).getHetime() + "    " + "完成处理";
            if( datalist.get(0).getHstate() == null || datalist.get(0).getHstate() == 0 )
                desc += "    有新数据";
        }

        return desc;
    }

    private String GetHandleStateDesc(SimplePatientInfo patientDataInfo, List<User> users) {
        switch(patientDataInfo.getHandleState()){
            case 0:
                return patientDataInfo.getBegintime() + "    " + "未处理";
            case 1:
                return patientDataInfo.getHandlebegintime() + "    " + GetUser(patientDataInfo.getHandleUserID(), users).getName() + "正在处理";
            case 2:
                return patientDataInfo.getHandlebegintime() + "    " + GetUser(patientDataInfo.getHandleUserID(), users).getName() + "完成处理";
            case 3:
                return patientDataInfo.getHandlebegintime() + "    " + GetUser(patientDataInfo.getHandleUserID(), users).getName() + "更新处理";
            case 4:
                return patientDataInfo.getHandlebegintime() + "    " + GetUser(patientDataInfo.getHandleUserID(), users).getName() + "中断处理";
            //case 5:
            //	return patientDataInfo.getBegintime() + "    " + GetUser(patientDataInfo.getHandleUserID(), users).getName() + "正在处理";
            //case 6:
            //	return patientDataInfo.getBegintime() + "    " + GetUser(patientDataInfo.getHandleUserID(), users).getName() + "正在处理";
            case 7:
                return patientDataInfo.getHandlebegintime() + "    " + GetUser(patientDataInfo.getHandleUserID(), users).getName() + "正在处理  有新数据";
            case 8:
                return patientDataInfo.getHandlebegintime() + "    " + GetUser(patientDataInfo.getHandleUserID(), users).getName() + "完成处理  有新数据";
            default:
                return "";
        }
    }

    public List<SimplePatientInfo> searchReplayInfo1(int userID, DataSearchCondition searchCondition) {
        long beginTime1 = System.currentTimeMillis();
        List<Integer> userIDList = userService.getAllChildID(userID);
        userIDList.add(0, userID);

        List<SimplePatientInfo> dataInfoList = new ArrayList<SimplePatientInfo>();

        StringBuilder sql = new StringBuilder("SELECT id, name, age, sex, userid, bednumber, birthday, outpatientnumber, hospitalnumber, handlestate, handleuserid, handlestarttime, recordstarttime, recordendtime FROM `patient` WHERE (");
        if(searchCondition.departmentid != 0){
            sql.append("patient.userid = " + searchCondition.departmentid);
        }
        else if(searchCondition.hospitalid != 0){
            String usersql = "select u.id from User u where u.parentuserid = " + searchCondition.hospitalid;
            List<Integer> departmentIDList = em.createQuery(usersql).getResultList();
            departmentIDList.add(searchCondition.hospitalid);

            for(Integer userid: departmentIDList){
                sql.append("patient.userid = " + userid + " or ");
            }

            sql.delete(sql.length()-4, sql.length());
        }
        else{
            for(Integer userid: userIDList){
                sql.append("patient.userid = " + userid + " or ");
            }

            sql.delete(sql.length()-4, sql.length());
        }

        sql.append(" ) ");
        if(searchCondition.state != 5) //如果不是查找所有状态的数据
            sql.append("and patient.handlestate = " + searchCondition.state);
        if(searchCondition.patientname.length() > 0)
            sql.append(" and patient.name like '%" + searchCondition.patientname + "%'");
        if(searchCondition.bednum.length() > 0)
            sql.append(" and patient.bednumber = " + searchCondition.bednum);

        sql.append(" and exists (select 1 from data where data.patientid = patient.id and TIMESTAMPDIFF(SECOND, createdate, endtime) > " + searchCondition.minseconds + " and ");
        if(searchCondition.state != 5){
            if(searchCondition.state == 1){
                sql.append("data.handlestate=1 and data.endtime != '0000-00-00 00:00:00' and data.type=" + searchCondition.type + ")");
            }
            else{
                if(searchCondition.state == 0)
                    sql.append(" (data.handlestate=0 or data.handlestate is null)");
                else
                    sql.append("data.handlestate=" + searchCondition.state);

                sql.append(" and data.endtime != '0000-00-00 00:00:00' and data.type=" + searchCondition.type + ")");
                sql.append(" and not exists (select 1 from data where data.patientid = patient.id and data.handlestate=1 and data.type=" + searchCondition.type + ")");
            }
        }
        else{
            sql.append(" data.endtime != '0000-00-00 00:00:00' and data.type=" + searchCondition.type + ")");
        }

        //sql.append("and exists (select 1 from data where data.patientid = patient.id and TIMESTAMPDIFF(SECOND, createdate, endtime) > " + searchCondition.minseconds + " )");

        sql.append(" ORDER BY patient.id DESC ");
        if(searchCondition.patientcount > 0)
            sql.append("LIMIT " + searchCondition.patientcount);

        //System.out.println( "make sql time: " + (System.currentTimeMillis() - beginTime1));

        long beginTime3 = System.currentTimeMillis();
        List<User> users = userService.getAllAnalysts();
        users.addAll(userService.getAllUser(userIDList));
        //System.out.println( "get user time: " + (System.currentTimeMillis() - beginTime3));
        //System.out.println("sql:" + sql.toString());

        long beginTime4 = System.currentTimeMillis();
        List<Object[]> results = em.createQuery(sql.toString()).getResultList();
       // System.out.println( "get data time: " + (System.currentTimeMillis() - beginTime4));

        long beginTime2 = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for(int i = 0; i < results.size(); i++)
        {
            Object[] record = results.get(i);

            //p.id p.name p.age p.sex p.userid p.bednumber p.birthday p.outpatientnumber
            //p.hospitalnumber p.symptom d.id d.type d.transferflag d.filename d.createdate d.endtime
            SimplePatientInfo patientDataInfo = new SimplePatientInfo();
            patientDataInfo.setId(TypeConverter.objectToInt(record[0]));
            patientDataInfo.setName( TypeConverter.objectToString(record[1]) );
            patientDataInfo.setAge( TypeConverter.objectToString(record[2]) );
            patientDataInfo.setSex( TypeConverter.objectToString(record[3]) );
            patientDataInfo.setBednum( TypeConverter.objectToString(record[5]) );
            patientDataInfo.setBirthday( TypeConverter.objectToString(record[6]) );
            patientDataInfo.setOutpatientnumber( TypeConverter.objectToString(record[7]) );
            patientDataInfo.setHospitalnumber( TypeConverter.objectToString(record[8]) );
            patientDataInfo.setHandleUserID(TypeConverter.objectToInt(record[10]) );
            patientDataInfo.setHandlebegintime( TypeConverter.objectToString(record[11]) );
            patientDataInfo.setBegintime( TypeConverter.objectToString(record[12]) );
            patientDataInfo.setEndtime( TypeConverter.objectToString(record[13]) );
            patientDataInfo.setHandleState( TypeConverter.objectToInt(record[9]) );
            patientDataInfo.setState( GetHandleStateDesc(patientDataInfo, users) );

            User user = GetUser( TypeConverter.objectToInt(record[4]), users );
            if(user.getType() == 5){   //科室
                patientDataInfo.setDepartment(user.getName());
                int parentID = user.getParentuserid();
                User parentUser = GetUser(parentID, users);
                if(parentUser != null)
                    patientDataInfo.setHospital(parentUser.getName());
            }else if(user.getType() == 4){   //医院
                patientDataInfo.setDepartment("");
                patientDataInfo.setHospital(user.getName());
            }

            dataInfoList.add(patientDataInfo);
        }

        //System.out.println( "parse data time: " + (System.currentTimeMillis() - beginTime2));
        return dataInfoList;
    }

    public List<PatientDataInfo> getOneGroupPatientInfo(int userID, int count, int minseconds) {
        List<Integer> userIDList = userService.getAllChildID(userID);
        userIDList.add(0, userID);

        List<PatientDataInfo> dataInfoList = new ArrayList<PatientDataInfo>();

        List<Patient> patientlist = patientService.getPatientsByUserId(userIDList, count);
        //System.out.println( "patient time: " + (System.currentTimeMillis() - beginTime1));

        long beginTime = System.currentTimeMillis();
        List<Data> datas = getDatasByPatients(patientlist);
        //System.out.println( "data time: " + (System.currentTimeMillis() - beginTime));


        List<Data> dataList = new LinkedList<Data>();
        long beginTime11 = System.currentTimeMillis();
        for(Data data: datas){
            if( IsDataTimeSpanEnough(data, minseconds) )
                dataList.add(data);
        }

        //System.out.println( "parse date: " + (System.currentTimeMillis() - beginTime11));

        long beginTime2 = System.currentTimeMillis();
        List<User> users = userService.getAllUser(userIDList);

        for(Patient p:patientlist){
            List<Data> subdatas = new LinkedList();
            for(Data data: dataList){
                if( data.getPatientid().equals(p.getId()) )
                    subdatas.add(data);
            }

            if(subdatas.size() == 0)
                continue;

            dataInfoList.add( GetPatientDataInfo(p, subdatas, users) );
        }

        //System.out.println( "parse data time: " + (System.currentTimeMillis() - beginTime2));

        return dataInfoList;
    }

    private List<Data> getDatasByPatients(List<Patient> patientlist) {
        String sql = "select d from Data d where (d.patientid in :patientIds) and d.endtime <> '0000-00-00 00:00:00' order by d.id DESC";
//        for(Patient p:patients){
//            sql += "d.patientid = " + p.getId() + " or ";
//        }
//
//        sql = sql.substring(0, sql.length() - 4) + ") ";
//        sql += " and d.endtime != '0000-00-00 00:00:00' order by d.id DESC";

        Query query = em.createQuery(sql);
        query.setParameter("patientIds", patientlist.stream().map(patient -> patient.getId()));
        return query.getResultList();
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

    private PatientDataInfo GetPatientDataInfo(Patient p, List<Data> datas, List<User> users){
        PatientDataInfo patientDataInfo = new PatientDataInfo();
        List<DataIDAndFileName> dataList = patientDataInfo.datas;
        for(Data data:datas){
            DataIDAndFileName datainfo = new DataIDAndFileName(data.getId(),
                    data.getType(),
                    data.getFilename(),
                    data.getTransferflag(),
                    data.getCreatedate(),
                    data.getEndtime(),
                    data.getHandlestate(),
                    data.getUserid(),
                    data.getHandlestarttime(),
                    data.getEndtime());
            dataList.add(datainfo);
        }

        SimplePatientInfo patientinfo = patientDataInfo.patientinfo;
        patientinfo.setAge(p.getAge());
        patientinfo.setBednum(p.getBednumber());
        patientinfo.setHospitalnumber(p.getHospitalnumber());
        patientinfo.setId(p.getId());
        patientinfo.setName(p.getName());
        patientinfo.setBirthday(p.getBirthday());
        patientinfo.setOutpatientnumber(p.getOutpatientnumber());
        patientinfo.setSex(p.getSex());
        patientinfo.setSymptom(p.getSymptom());
        patientinfo.setBegintime( datas.get(datas.size() - 1).getCreatedate() );
        patientinfo.setEndtime( datas.get(0).getEndtime() );
        patientinfo.setState( GetPatientDataHandleStateDesc(datas, users) );

        User user = GetUser( p.getUserid(), users );
        if(user.getType() == 5){   //科室
            patientinfo.setDepartment(user.getName());
            int parentID = user.getParentuserid();
            patientinfo.setHospital( GetUser(parentID, users).getName() );
        }else if(user.getType() == 4){   //医院
            patientinfo.setDepartment("");
            patientinfo.setHospital(user.getName());
        }

        return patientDataInfo;
    }

    private String GetPatientDataHandleStateDesc(List<Data> datalist, List<User> users){
        String desc = "";
        int  nDataHandlingIndex = -1;
        int  nDataNotHanldeIndex = -1;
        int  nDataStopHandleIndex = -1;
        int  nDataUpdateHandleIndex = -1;
        int  nDataFinishHandleIndex = -1;

        int i = -1;
        for(Data data: datalist){
            i++;
            if(data.getHandlestate() == null){
                nDataNotHanldeIndex = i;
            }
            else if (data.getHandlestate() == 1)
            {
                nDataHandlingIndex = i;
            }
            else if (data.getHandlestate() == 0)
            {
                nDataNotHanldeIndex = i;
            }
            else if (data.getHandlestate() == 4)
            {
                nDataStopHandleIndex = i;
            }
            else if (data.getHandlestate() == 3)
            {
                nDataUpdateHandleIndex = i;
            }
            else if(data.getHandlestate() == 2)
            {
                nDataFinishHandleIndex = i;
            }
        }

        if (nDataHandlingIndex != -1){
            //Query query = getSession().createQuery( "From User u WHERE u.id = " + datalist.get(nDataHandlingIndex).getUserid() );
            //User user = (User) query.uniqueResult();
            int userid = datalist.get(nDataHandlingIndex).getUserid();
            desc = datalist.get(nDataHandlingIndex).getHandlestarttime() + "    " + GetUser(userid, users).getName() + "正在处理";
            if( datalist.get(0).getHandlestate() == null || datalist.get(0).getHandlestate() == 0 ){
                desc += "    有新数据";
            }
        }
        else if (nDataNotHanldeIndex != -1){
            desc = datalist.get(nDataNotHanldeIndex).getCreatedate() + "    " + "未处理";
        }
        else if (nDataStopHandleIndex != -1){
            desc = datalist.get(nDataStopHandleIndex).getCreatedate() + "    " + "中止处理";
        }
        else if (nDataUpdateHandleIndex != -1){
            desc = datalist.get(nDataUpdateHandleIndex).getHandleendtime() + "    " + "更新处理";
        }
        else if(nDataFinishHandleIndex != -1){
            desc = datalist.get(nDataFinishHandleIndex).getHandleendtime() + "    " + "完成处理";
            if( datalist.get(0).getHandlestate() == null || datalist.get(0).getHandlestate() == 0 )
                desc += "    有新数据";
        }

        return desc;
    }

    public List<Data> getDatasByPatienIdAndType(int patientID, int type) {
        return dataRepository.findByPatientidAndType(patientID, type);
    }

    public List<String> getDataFileNames(int patientID, int days, int datatype) {
        String date = null;
        if(days != 0){
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE,-days+1);
            date = new SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());
        }

        String sql = "select filename from Data d where d.patientid = :patientID + and type = :type";
        if(date != null)
            sql += " and d.createdate >= :date";

        Query query =  em.createQuery(sql);
        query.setParameter("patientID", patientID);
        query.setParameter("type", datatype);
        query.setParameter("date", date);
        return em.createQuery(sql).getResultList();
    }

    public short getTransferState(int dataID) {
        return dataRepository.findOne(dataID).getTransferflag();
    }

    public boolean setHandleState(List<Integer> dataIDs, int state, int userid) {
        Date date=new Date();
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

    public List<Data> getOnePatientData(int patientID, String begeindate, String enddate, int state, int minseconds) {
        String sql = "select d From Data d WHERE d.patientid = :patientID and d.endtime != '0000-00-00 00:00:00'";

        if(begeindate.length() > 0){
            sql += " and d.createdate >= '" + begeindate + "'";
        }
        if(enddate.length() > 0){
            sql += " and d.endtime <= '" + enddate + "'";
        }
        if(state != 5){
            sql += " and d.handlestate = " + state;
        }

        sql += " order by d.id DESC";
        Query query = em.createQuery(sql);
        query.setParameter("patientID", patientID);

        List<Data> datas = query.getResultList();
        return datas.stream().filter(data -> IsDataTimeSpanEnough(data, minseconds)).collect(Collectors.toList());
    }

    private String []extention = {".mpm", ".mpr", ".mpe", ".mpb"};
    public HasNewFileToDownload hasNewFileToDownload(int patientID, int datatype, long filelength) {

        String filename = dataRepository.findByPatientidAndType(patientID, datatype).get(0).getFilename();
        String yearandmonthfolder = filename.substring(0, 6);
        String datefolder = filename.substring(6, 8);

        try {
            File file = new File(dataPath + yearandmonthfolder + "\\" + datefolder + "\\" + filename + extention[datatype]);
            //System.out.println( "file length at client: " + filelength + " VS file length at server: " + file.length() + " of file " + file.getAbsolutePath());
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
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建httppost
        HttpPost httppost = new HttpPost("http://hub2.ubody.net/expert/post_ecg_report");
        httppost.addHeader("Origin", "http://c.ubody.net");
        // 创建参数队列
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("ReportCode", reportCode));
        formparams.add(new BasicNameValuePair("ReportData", reportData));
        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
            httppost.setEntity(uefEntity);
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    strEcgReport = EntityUtils.toString(entity, "UTF-8");
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return strEcgReport;
    }
}
