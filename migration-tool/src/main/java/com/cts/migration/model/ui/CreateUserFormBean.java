package com.cts.migration.model.ui;

import com.cts.migration.entity.CustomUser;

public class CreateUserFormBean {

	private CustomUser user;

	private String selectedRole;

	public String getSelectedRole() {
		return selectedRole;
	}

	public void setSelectedRole(String selectedRole) {
		this.selectedRole = selectedRole;
	}

	public CustomUser getUser() {
		return user;
	}

	public void setUser(CustomUser user) {
		this.user = user;
	}

	public CreateUserFormBean() {
		this.user = new CustomUser();
	}

	// public String toString(){
	// return "first name : "+ getFirstName()
	// +",last name : "+getLastName()
	// + ",username : "+getUsername()
	// + ",email : "+getEmail()
	// + ",password : "+getPassword()
	// + ",role :  "+getSelectedRole();
	//
	// }

}
