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
	
	public List<DataInfo> getOneGroupReplayInfo(@WebParam(name="userID") int userID,
												@WebParam(name="search")String search,
												@WebParam(name="firstIndex") int firstIndex,
												@WebParam(name="count") int count)
	{
		return dataService.getOneGroupData( userID, search, firstIndex, count);
	}
}
