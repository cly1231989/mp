package com.koanruler.mp.entity;

/**
 * Created by chengyuan on 2017/9/16.
 */
public class UserInfo {
    String userName;
    String parentUserName;  //上级用户的全名：中心1/医院1/科室1
    User user;

    public UserInfo(String userName, String parentUserName, User user) {
        this.userName = userName;
        this.parentUserName = parentUserName;
        this.user = user;
    }

    public String getUserName() {
        return userName;
    }

    public UserInfo setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getParentUserName() {
        return parentUserName;
    }

    public UserInfo setParentUserName(String parentUserName) {
        this.parentUserName = parentUserName;
        return this;
    }

    public User getUser() {
        return user;
    }

    public UserInfo setUser(User user) {
        this.user = user;
        return this;
    }
}
