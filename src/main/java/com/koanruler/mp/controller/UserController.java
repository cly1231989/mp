package com.koanruler.mp.controller;

import com.koanruler.mp.entity.ResultData;
import com.koanruler.mp.entity.ResultList;
import com.koanruler.mp.entity.User;
import com.koanruler.mp.service.UserService;
import com.koanruler.mp.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@RestController
//@CrossOrigin(origins = "http://localhost:8060")
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	@Value("${url}")
	private String url;

	/**
	 * 获取本用户及下属用户的信息
	 */
	@GetMapping("/all")
	public ResultData<User> getAllSubUser(@RequestParam(name = "page", defaultValue = "1") Integer page,
										@RequestParam(name = "per_page", defaultValue = "-1") Integer countPerPage,
										@RequestParam(name = "filter", defaultValue = "") String filter,
										@RequestParam(name = "sort", defaultValue = "") Integer sort
										){
		ResultList<User> result = userService.getSubUser( UserUtil.getCurUser().getId(), page-1, countPerPage, filter);
		return new ResultData(page, countPerPage, null, url+"/user/all?page="+(page+1), result);
	}

	/**
	 * 新增、编辑用户信息
	 * @param user 用户信息
	 */
	@PostMapping(value = {"/add", "/edit"})
    public void addUser(@RequestBody User user){
	    if (user.getCreatedate() == null) {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date today = Calendar.getInstance().getTime();
            user.setCreatedate(formatter.format(today));
        }

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
