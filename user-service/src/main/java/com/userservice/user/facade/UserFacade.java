package com.userservice.user.facade;

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

	public void addUser(UserResource userResource) {

		User user = new User();
		user.setFirstName(userResource.getFirstName());
		user.setLastName(userResource.getLastName());
		user.setEmail(userResource.getEmail());
		user.setDepartmentId(userResource.getDepartmentId());

		userService.saveUser(user);

	}

	public List<UserResource> getAllUsers(String firstName, String lastName) {

		List<UserResource> list = userService.getAllUsers(firstName, lastName);

		return list;
	}

	public ResponseTemplateVO getUserWithDepartment(Long userId) {
		ResponseTemplateVO vo = userService.getUserWithDepartment(userId);

		return vo;
	}

}
