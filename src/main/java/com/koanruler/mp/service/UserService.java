package com.koanruler.mp.service;

import com.koanruler.mp.entity.Organization;
import com.koanruler.mp.entity.User;
import com.koanruler.mp.entity.UserIDAndName;
import com.koanruler.mp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public List<Integer> getAllParentID(int userID)
	{			 
		List<Integer> parentIDList = new ArrayList<Integer>();
		GetParentID(userID, userRepository.findAll(), parentIDList);
	    
		return parentIDList;
	}
	
	private void GetParentID(int userID, List<User> users, List<Integer> parentIDList)
	{
		for(int i = 0; i < users.size(); i++)
		{
			User user = users.get(i);
			int id = user.getId();						
			if(userID == id)
			{
				if(user.getParentuserid() == 0)
					break;
				
				parentIDList.add(user.getParentuserid());
				GetParentID(user.getParentuserid(), users, parentIDList);
				break;
			}
		}
	}

	public String getUserName(int userID) {		
		return userRepository.findOne(userID).getName();
	}

	public Organization getSubDepartmentInfo(int userID) {
		Organization selfinfo = new Organization();
		List<Organization> subs = new ArrayList<Organization>();
				
		User user = userRepository.getOne(userID);
		
		selfinfo.setSelfinfo( new UserIDAndName(user.getId(), user.getType(), user.getName()) );
		selfinfo.setSubs(subs);

		//如果用户是科室/楼层/病区，就返回自己；否则，返回下属机构的相关数据
		if(user.getType() == 5){	
			return selfinfo;
		}
		
		List<User> childlist = userRepository.findByParentuserid(userID);		
		for(int i = 0; i < childlist.size(); i++)
		{
			int childID = childlist.get(i).getId();	
			subs.add( getSubDepartmentInfo(childID) );
		}
		
		return selfinfo;
	}

	public Boolean login(String account, String pwd) {
		return userRepository.findByAccount(account).getPwd().equals(pwd);
	}
	
	public List<Integer> getAllChildID(int userID)
	{
		List<Integer> childIDList = new ArrayList<Integer>();
		GetChildID(userID, userRepository.findAll(), childIDList);
		return childIDList;
	}
	
	private void GetChildID(int parentID, List<User> users, List<Integer> childIDList)
	{
		for(int i = 0; i < users.size(); i++)
		{		
			User user = users.get(i);
			
			if(parentID == user.getParentuserid())
			{
				childIDList.add(user.getId());
				GetChildID(user.getId(), users, childIDList);
			}
		}
	}

	public User getUser(Integer userid) {		
		return userRepository.findOne(userid);
	}

	public List<User> getAllAnalysts() {
		return userRepository.findByType(7);
	}

	public List<User> getAllUser(List<Integer> childUserIDList) {
		return userRepository.findByIdIn(childUserIDList);
	}
}
