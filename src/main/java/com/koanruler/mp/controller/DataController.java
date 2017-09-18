package com.koanruler.mp.controller;

import com.koanruler.mp.entity.DataInfo;
import com.koanruler.mp.entity.ResultList;
import com.koanruler.mp.entity.User;
import com.koanruler.mp.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/data")
public class DataController {
	
	@Autowired
	private DataService dataService;

    /**
     * 根据病人姓名或者终端编号分页获取用户及下属机构的数据信息
     * @param patientNameOrTerNum：病人姓名或者终端编号
     * @param firstIndex：要搜索的第一条记录的索引
     * @param dataCount：要搜索的数据量
     * @return 满足搜索条件的数据总数和本次搜索的数据信息
     */
	@GetMapping("/search")
	public ResultList<DataInfo> searchData(@RequestParam(name="patientNameOrTerNum", defaultValue = "") String patientNameOrTerNum,
										   @RequestParam(name="firstIndex", defaultValue = "0") int firstIndex,
										   @RequestParam(name="count", defaultValue = "30") long dataCount){
	    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return dataService.getOneGroupData(user.getId(), patientNameOrTerNum, firstIndex, dataCount);


	}
	
//	public Long getCount()
//	{
//		return dataService.getCount();
//	}
//
//	public List<DataInfo> getOneGroupReplayInfo(@WebParam(name="userID") int userID,
//												@WebParam(name="search")String search,
//												@WebParam(name="firstIndex") int firstIndex,
//												@WebParam(name="count") int count)
//	{
//		return dataService.getOneGroupData( userID, search, firstIndex, count);
//	}
}
