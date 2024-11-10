package com.userservice.user.service;

import java.util.List;

import com.userservice.pojo.ResponseTemplateVO;
import com.userservice.pojo.UserResource;
import com.userservice.user.entity.User;

public interface UserService {
	
	public User saveUser(User user);
	
	public List<UserResource> getAllUsers(String firstName, String lastName);

	public ResponseTemplateVO getUserWithDepartment(Long userId);
}
