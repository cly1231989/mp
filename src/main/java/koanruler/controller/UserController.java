package koanruler.controller;

import koanruler.entity.PwdEditInfo;
import koanruler.entity.ResultData;
import koanruler.entity.ResultList;
import koanruler.entity.User;
import koanruler.service.UserService;
import koanruler.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@RestController
//@CrossOrigin(origins = "http://localhost:8060")
public class UserController {
	@Autowired
	private UserService userService;

	/**
	 * 获取本用户及下属用户的信息
	 */
	@GetMapping("/users")
	public ResultData<User> getAllSubUsers( @RequestParam(name = "page", defaultValue = "1") Integer page,
											@RequestParam(name = "per_page", defaultValue = "-1") Integer countPerPage,
											@RequestParam(name = "filter", defaultValue = "") String filter,
											@RequestParam(name = "sort", defaultValue = "") Integer sort
											){
        String url = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString();

		ResultList<User> result = userService.getSubUser( UserUtil.getCurUser().getId(), page-1, countPerPage, filter);
		return new ResultData(page, countPerPage, null, url+"?page="+(page+1), result.getTotalCount(), result.getDataInfo());
	}

	/**
	 * 新增用户
	 * @param user 用户
	 */
	@PostMapping("/users")
	public void createUser(@RequestBody User user){
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = Calendar.getInstance().getTime();

		user.setCreatedate(formatter.format(today));
		userService.addUser(user);
	}

	/**
	 * 编辑用户信息
	 * @param user 用户信息
	 */
	@PutMapping("/users/{id}")
    public void editUser(@PathVariable Integer id, @RequestBody User user){
	    userService.addUser(user);
    }

	/**
	 * 修改密码
	 * @param user 用户信息
	 */
	@PutMapping("/users/{id}/pwd")
	public void editPwd(@PathVariable Integer id, @RequestBody PwdEditInfo pwdEditInfo){
		userService.editPwd(pwdEditInfo);
	}

    /**
     * 删除用户
     * @param id 用户ID
     */
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Integer id){
        userService.deleteUser(id);
    }

}
