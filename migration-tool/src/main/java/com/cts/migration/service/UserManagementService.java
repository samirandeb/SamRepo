package com.cts.migration.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cts.migration.dao.UserManagementDao;
import com.cts.migration.entity.CustomUser;
import com.cts.migration.entity.Role;

@Service
public class UserManagementService implements UserDetailsService {

	@Autowired
	private UserManagementDao userDao;

	public CustomUser loadUserByUsername(String username) throws UsernameNotFoundException {
		return userDao.loadUserByUsername(username);
	}

	public CustomUser getUserDetail(String username) throws UsernameNotFoundException {
		CustomUser user = userDao.loadUserByUsername(username);
		user.setPassword(null);
		return user;
	}

	public void deleteUser(String userId) throws UsernameNotFoundException {
		userDao.deleteUser(userId);

	}

	public List<CustomUser> getAllUser(){
		return userDao.getAllUser();
	}

	public List<Role> getAllRoles(){
		return userDao.getAllRoles();
	}

	public CustomUser createUser(CustomUser user){
		return userDao.createUser(user);
	}

	public void updateUser(CustomUser user){
		userDao.updateUser(user);
	}

	public CustomUser getUserDetailById(String userId) {
		CustomUser user = userDao.loadUserByUserId(userId);
		user.setPassword(null);
		return user;
	}


	//TODO:
	public String getOldPassword(String user){
		return userDao.getOldPassword(user);
	}

	public int updatePassword(String user,String newPass){
		return userDao.updatePassword(user,newPass);
	}

}