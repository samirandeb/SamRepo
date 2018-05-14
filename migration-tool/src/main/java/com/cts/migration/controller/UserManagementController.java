package com.cts.migration.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cts.migration.entity.CustomUser;
import com.cts.migration.entity.Role;
import com.cts.migration.model.ui.CreateUserFormBean;
import com.cts.migration.model.ui.UserFormBean;
import com.cts.migration.service.OipaMigrationService;
import com.cts.migration.service.UserManagementService;

@Controller
@Scope("session")
public class UserManagementController {

	@Autowired
	private UserManagementService userManagementService;

	@Autowired
	private OipaMigrationService oipaService;

	@RequestMapping("/userMgmt")
	public String userMgmt(Model model) {

		model.addAttribute("fragmentName", "fragments/userMgmt");
		model.addAttribute("currentModule", "userManagement");
		List<CustomUser> userlist = userManagementService.getAllUser();
		UserFormBean formbean = new UserFormBean();
		formbean.setUserList(userlist);
		model.addAttribute("userForm", formbean);
		model.addAttribute("newUserForm", new CreateUserFormBean());
		return "index";
	}

	@RequestMapping(value = "/getUserForm/{userId}")
	public String getModal(Model model, @PathVariable("userId") String userId) {
		////System.out.println("Get the modal for : " + userId);
		CustomUser user = userManagementService.getUserDetailById(userId);
		CreateUserFormBean userForm = new CreateUserFormBean();
		userForm.setUser(user);
		userForm.setSelectedRole((user.getAuthorities()!=null && user.getAuthorities().size()>0 )?user.getAuthorities().get(0).getRoleId():"");
		model.addAttribute("allSourceRegions",oipaService.getAllSourceRegion());
		model.addAttribute("allRoles",userManagementService.getAllRoles());
		model.addAttribute("newUserForm", userForm);
		return "fragments/partials :: createUpdateUser";
	}

	@RequestMapping(value = "/getUserForm")
	public String getModal(Model model) {
		////System.out.println("Get the modal for New User ");
		model.addAttribute("allRoles",userManagementService.getAllRoles());
		CreateUserFormBean userForm = new CreateUserFormBean();
		model.addAttribute("allSourceRegions",oipaService.getAllSourceRegion());
		model.addAttribute("newUserForm",userForm);
		return "fragments/partials :: createUpdateUser";
	}


	@RequestMapping("/createUser")
	public @ResponseBody String createUser(Model model, @ModelAttribute CreateUserFormBean newUser) {
		////System.out.println("Inside new user : "+newUser.getUser().getPassword());
		////System.out.println("Region Selected : " + newUser.getUser().getSourceRegions());

		newUser.getUser().setAuthorities(new ArrayList<Role>());
		Role rl = new Role();
		rl.setRoleId(newUser.getSelectedRole());
		newUser.getUser().getAuthorities().add(rl);
		if(null == newUser.getUser().getUserId() || "".equalsIgnoreCase(newUser.getUser().getUserId())){
			CustomUser createdUser = userManagementService.createUser(newUser.getUser());
			return "User has been created with login id : " + createdUser.getUsername();
		}
		else{
			//String userName = newUser.getUser().getUsername();
			userManagementService.updateUser(newUser.getUser());
			return "User information updated";
		}

	}

	@RequestMapping("/deleteUser")
	public @ResponseBody String deleteUser(Model model, @ModelAttribute CreateUserFormBean user) {
		////System.out.println("Inside delete user : " + user.getUser().getUserId());
		userManagementService.deleteUser(user.getUser().getUserId());
		return "User has been deleted for id : " + user.getUser().getUserId();
	}

//	@ModelAttribute("allRoles")
//	public List<Role> allRoles() {
//		return userManagementService.getAllRoles();
//	}

//	@RequestMapping("/changePassword")
//	public String changePassword(Model model) {
//		model.addAttribute("fragmentName", "fragments/changePassword");
//		return "index";
//	}

	//TODO:
	@RequestMapping(value = "/changePasswrd/{oldPass}/{newPass}")
	public @ResponseBody String modifyPassword(Model model, @PathVariable("oldPass") String oldPass, @PathVariable("newPass") String newPass) {
		////System.out.println(oldPass);
		////System.out.println(newPass);

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = ((CustomUser)auth.getPrincipal()).getUserId();
		String oldPwd=userManagementService.getOldPassword(userId);
		if(oldPass.equals(oldPwd)){
			////System.out.println("Match Found");
			userManagementService.updatePassword(userId,newPass);
		}
		else{
			////System.out.println("Match NOT Found");
			throw new RuntimeException("Password update failed");
		}

		return "success";
	}

}
