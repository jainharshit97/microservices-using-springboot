package com.userservice.user.facade;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.userservice.pojo.ResponseTemplateVO;
import com.userservice.pojo.UserResource;
import com.userservice.user.entity.User;
import com.userservice.user.service.UserService;

@Component
public class UserFacade {

	@Autowired
	private UserService userService;

	public boolean addUser(UserResource userResource) {

		User user = new User();
		user.setFirst_name(userResource.getFirstName());
		user.setLast_name(userResource.getLastName());
		user.setEmail(userResource.getEmail());
		user.setDepartment_id(userResource.getDepartmentId());

		boolean error = userService.saveUser(user);

		return error;
	}

	public List<UserResource> getAllUsers(String firstName, String lastName) {
		List<User> users = userService.getAllUsers(firstName, lastName);
		
		List<UserResource> list = new ArrayList<>();
		for (User user : users) {
			list.add(convertDTOtoResource(user));
		}

		return list;
	}
	
	private UserResource convertDTOtoResource(User user) {
		UserResource res = new UserResource();

		res.setFirstName(user.getFirst_name());
		res.setLastName(user.getLast_name());
		res.setDepartmentId(user.getDepartment_id());
		res.setEmail(user.getEmail());

		return res;
	}
	
	public UserResource getUserById(Long user_id) {
		User user = userService.getUserById(user_id);
		
		UserResource userRes = convertDTOtoResource(user);
		
		return userRes;
	}
	
	public boolean updateUser(UserResource userResource) {

		User user = new User();
		user.setFirst_name(userResource.getFirstName());
		user.setLast_name(userResource.getLastName());
		user.setEmail(userResource.getEmail());
		user.setDepartment_id(userResource.getDepartmentId());

		boolean error = userService.updateUser(user);

		return error;
	}

	public ResponseTemplateVO getUserWithDepartment(Long userId) {
		ResponseTemplateVO vo = userService.getUserWithDepartment(userId);
		return vo;
	}

}
