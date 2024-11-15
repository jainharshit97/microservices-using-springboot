package com.userservice.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.userservice.pojo.Department;
import com.userservice.pojo.ResponseTemplateVO;
import com.userservice.pojo.UserResource;
import com.userservice.user.entity.User;
import com.userservice.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RestTemplate restTemplate;

	public boolean saveUser(User user) {
//    log.info("Inside saveUser method of UserService.");\
		
		Department department = restTemplate
				.getForObject("http://DEPARTMENTS/departments/" + user.getDepartment_id(), Department.class);
		
		if(department == null) {
			return true;
		}
		
		 userRepository.save(user);
		
		return false;
	}

	public List<User> getAllUsers(String firstName, String lastName) {

		List<User> users;
		if (firstName != null || lastName != null) {
			// Search with provided criteria
			users = userRepository.searchUsers(firstName, lastName);
		} else {
			// Fetch all books
			users = userRepository.findAll();
		}
		return users;
	}
	
	public User getUserById(Long user_id) {
		
		User user = userRepository.getUserById(user_id);
		return user;
	}
	
	public boolean updateUser(User user) {
		
		try {
			userRepository.updateUser(user.getUser_id(), user.getFirst_name(), user.getLast_name(), 
					user.getEmail(), user.getDepartment_id());
		} catch(Exception e) {
			return true;
		}
		return false;
	}

	public ResponseTemplateVO getUserWithDepartment(Long user_id) {
//    log.info("Inside saveUser method of UserService.");
		ResponseTemplateVO vo = new ResponseTemplateVO();
		Optional<User> users = userRepository.findById(user_id);
		
		User user = users.get();

		Department department = restTemplate
				.getForObject("http://DEPARTMENTS/departments/" + user.getDepartment_id(), Department.class);
		vo.setUser(user);
		vo.setDepartment(department);

		return vo;
	}

}
