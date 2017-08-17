package com.koanruler.mp.controller;

import com.koanruler.mp.entity.Organization;
import com.koanruler.mp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.jws.WebParam;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/getparents/{id}", method=RequestMethod.GET)
	public List<Integer> getAllParentID( @PathVariable("id") int userID )
	{
		return userService.getAllParentID(userID) ;
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public String getUserName( @PathVariable("id") int userID )
	{
		return userService.getUserName(userID);
	}
	
	public Organization getSubDepartmentInfo(@WebParam(name="userID") int userID )
	{
		return userService.getSubDepartmentInfo(userID);
	}
	
	public Boolean login( @WebParam(name="account") String account, @WebParam(name="pwd") String pwd)
	{
		return userService.login(account, pwd);
	}
	
}
