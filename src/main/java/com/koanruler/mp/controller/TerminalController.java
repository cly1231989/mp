package com.koanruler.mp.controller;

import com.koanruler.mp.entity.BindTerminalInfo;
import com.koanruler.mp.entity.Terminal;
import com.koanruler.mp.service.TerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jws.WebParam;
import java.util.List;

@RestController
@RequestMapping("/terminal")
public class TerminalController {
	@Autowired
	private TerminalService terminalService;
	
	public List<BindTerminalInfo> getAllTerminalInfo(@WebParam(name="userID") int userID )
	{
		return terminalService.getAllTerminalInfo(userID);
	}
	
	public Terminal getTerminal(@WebParam(name="terminalID") String terminalNum)
	{
		return terminalService.getTerminal(terminalNum);
	}
	
	public boolean bindTerminal( @WebParam(name="terminalID") String terminalNum, @WebParam(name="patientID") int patientID )
	{
		return terminalService.bindTerminal(terminalNum, patientID);
	}
	
}
