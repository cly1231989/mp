package com.koanruler.mp.controller;

import com.koanruler.mp.entity.User;
import com.koanruler.mp.entity.UserInfo;
import com.koanruler.mp.service.UserService;
import com.koanruler.mp.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	/**
	 * 获取本用户及下属用户的信息
	 */
	@GetMapping("/all")
	public List<UserInfo> getAllSubUser(){
		return userService.getAllSubUser( UserUtil.getCurUser().getId() );
	}

	/**
	 * 新增、编辑用户信息
	 * @param user 用户信息
	 */
	@PostMapping(value = {"/add", "/edit"})
    public void addUser(@RequestBody User user){
	    userService.addUser(user);
    }
	
//	@RequestMapping(value="/getparents/{id}", method=RequestMethod.GET)
//	public List<Integer> getAllParentID( @PathVariable("id") int userID )
//	{
//		return userService.getAllParentID(userID) ;
//	}
//
//	@RequestMapping(value="/{id}", method=RequestMethod.GET)
//	public String getUserName( @PathVariable("id") int userID )
//	{
//		return userService.getUserName(userID);
//	}
//
//	public Organization getSubDepartmentInfo(@PathParam("userID") int userID )
//	{
//		return userService.getSubDepartmentInfo(userID);
//	}
//
//	public Boolean login( @PathParam("account") String account, @PathParam("pwd") String pwd)
//	{
//		return userService.login(account, pwd);
//	}
//
//	@RequestMapping("/type")
//	public Map<String, Integer> getUserType(ModelMap model)
//	{
//		Integer userId = (Integer) model.get("userId");
//		userId = 3;
//		Map<String, Integer> result = new HashMap<String, Integer>();
//		result.put("type", userService.getUserById(userId).getType());
//		return result;
//	}

}
