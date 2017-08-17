package com.koanruler.mp.entity;

import java.util.List;

public class Organization {
	private UserIDAndName selfinfo;
	private List<Organization> subs;
	public UserIDAndName getSelfinfo() {
		return selfinfo;
	}
	public void setSelfinfo(UserIDAndName selfinfo) {
		this.selfinfo = selfinfo;
	}
	public List<Organization> getSubs() {
		return subs;
	}
	public void setSubs(List<Organization> subs) {
		this.subs = subs;
	}
	public Organization(UserIDAndName selfinfo, List<Organization> subs) {
		super();
		this.selfinfo = selfinfo;
		this.subs = subs;
	}
	public Organization() {
		super();
	}
	
}
