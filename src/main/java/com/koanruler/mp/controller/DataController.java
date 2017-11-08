package com.koanruler.mp.controller;

import com.koanruler.mp.entity.DataInfo;
import com.koanruler.mp.entity.ResultData;
import com.koanruler.mp.entity.ResultList;
import com.koanruler.mp.service.DataService;
import com.koanruler.mp.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {
	
	@Autowired
	private DataService dataService;

    /**
     * 根据病人姓名或者终端编号分页获取用户及下属机构的数据信息
     * @param patientNameOrTerNum：病人姓名或者终端编号
     * @param page
     * @param countPerPage：要搜索的数据量
     * @return 满足搜索条件的数据总数和本次搜索的数据信息
     */
	@GetMapping("/datas")
	public ResultData<DataInfo> searchData(@RequestParam(name = "page", defaultValue = "1") Integer page,
										   @RequestParam(name = "per_page", defaultValue = "-1") Integer countPerPage,
										   @RequestParam(name = "filter", defaultValue = "") String patientNameOrTerNum,
										   @RequestParam(name = "sort", defaultValue = "") Integer sort){

		ResultList<DataInfo> results = dataService.getOneGroupData(UserUtil.getCurUser().getId(), patientNameOrTerNum, (page-1) * countPerPage, countPerPage);
		return new ResultData(page, countPerPage, null, null, results.getTotalCount(), results.getDataInfo());
	}
}
