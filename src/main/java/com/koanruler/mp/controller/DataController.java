package com.koanruler.mp.controller;

import com.koanruler.mp.entity.DataSearchCondition;
import com.koanruler.mp.entity.PatientDataInfo;
import com.koanruler.mp.entity.DataInfo;
import com.koanruler.mp.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jws.WebParam;
import java.util.List;

@RestController
@RequestMapping("/data")
public class DataController {
	
	@Autowired
	private DataService dataService;
	
	public Long getCount()
	{
		return dataService.getCount();
	}

	public Long getReplayInfoCount( @WebParam(name="userID") int userID, @WebParam(name="search")String search)
	{
		return dataService.getDataCount(userID, search);
	}
	
	public List<DataInfo> getOneGroupReplayInfo(@WebParam(name="userID") int userID,
												@WebParam(name="search")String search,
												@WebParam(name="firstIndex") int firstIndex,
												@WebParam(name="count") int count)
	{
		return dataService.getOneGroupData( userID, search, firstIndex, count);
	}
	
	public List<PatientDataInfo> searchReplayInfo(@WebParam(name="userID") int userID,
                                                  @WebParam(name="patientname")String patientname,
                                                  @WebParam(name="bednum")String bednum,
                                                  @WebParam(name="hospitalid")int hospitalid,
                                                  @WebParam(name="departmentid")int departmentid,
                                                  @WebParam(name="begindate")String begindate,
                                                  @WebParam(name="enddate")String enddate,
                                                  @WebParam(name="type") int type,
                                                  @WebParam(name="state") int state,
                                                  @WebParam(name="minseconds")int minseconds,
                                                  @WebParam(name="patientcount")int patientcount)
	{
		DataSearchCondition searchCondition = new DataSearchCondition(patientname, bednum, hospitalid, departmentid, begindate, enddate, type, state, patientcount, minseconds);
		return dataService.searchPatientDataInfo(userID, searchCondition);
	}
}
