package com.koanruler.mp.controller;

import com.koanruler.mp.entity.*;
import com.koanruler.mp.service.TerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import javax.websocket.server.PathParam;

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
    public ResultList<TerminalUseInfo> searchTerInfo(@PathParam("terNum")String terNum){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return terminalService.getTerminalInfo(user.getId(), terNum, false);
    }

    /**
     * 获取用户及下属机构的已绑定的终端信息，可根据终端编号进行搜索
     * @param terNum 终端编号
     * @return 满足条件的终端数量和已绑定的终端信息
     */
	@GetMapping("/use/search")
	public ResultList<TerminalUseInfo> searchBoundTerInfo(@PathParam("terNum")String terNum){
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return terminalService.getTerminalInfo(user.getId(), terNum, true);
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
