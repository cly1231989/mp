package com.koanruler.mp.controller;

import com.koanruler.mp.entity.*;
import com.koanruler.mp.service.TerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/terminal")
public class TerminalController {
	@Autowired
	private TerminalService terminalService;

    /**
     * 获取用户及下属机构的终端信息，可根据终端编号进行搜索
     * @param terNum 终端编号
     * @return 满足条件的终端数量和终端信息
     */
	@GetMapping("/search")
    public ResultData<TerminalUseInfo> searchTerInfo(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                     @RequestParam(name = "per_page", defaultValue = "-1") Integer countPerPage,
                                                     @RequestParam(name = "filter", defaultValue = "") String terNum,
                                                     @RequestParam(name = "sort", defaultValue = "") Integer sort,
                                                     @RequestParam(name = "only_find_bound_ter", defaultValue = "false") Boolean onlyFindBoundTer){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ResultList<TerminalUseInfo> result = terminalService.getTerminalInfo(user.getId(), page, countPerPage, terNum, onlyFindBoundTer);
        return new ResultData(page, countPerPage, null, null, result);
    }

    /**
     * 添加终端
     * @param terminal 终端信息
     */
	@PostMapping("/add")
    public void addTerminal(@RequestBody Terminal terminal){
	    terminalService.Save(terminal);
    }
	
//	public List<BindTerminalInfo> getAllTerminalInfo(@PathParam("userID") int userID )
//	{
//		return terminalService.getAllTerminalInfo(userID);
//	}
//
//	public Terminal getTerminal(@PathParam("terminalID") String terminalNum)
//	{
//		return terminalService.getTerminal(terminalNum);
//	}
//
//	public boolean bindTerminal( @PathParam("terminalID") String terminalNum, @PathParam("patientID") int patientID )
//	{
//		return terminalService.bindTerminal(terminalNum, patientID);
//	}
	
}
