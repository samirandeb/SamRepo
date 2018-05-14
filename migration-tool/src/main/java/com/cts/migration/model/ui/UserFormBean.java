package com.cts.migration.model.ui;

import java.util.List;

import com.cts.migration.entity.CustomUser;

public class UserFormBean {
	
	private CustomUser selectedUser;
	private List<CustomUser> userList;
	public CustomUser getSelectedUser() {
		return selectedUser;
	}
	public void setSelectedUser(CustomUser selectedUser) {
		this.selectedUser = selectedUser;
	}
	public List<CustomUser> getUserList() {
		return userList;
	}
	public void setUserList(List<CustomUser> userList) {
		this.userList = userList;
	}
	
}
