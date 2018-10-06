package com.fico.demo.vo;

import com.fico.demo.model.User;

public class UserVo {
	private String userPassword;
	private User user;

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
