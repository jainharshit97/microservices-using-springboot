package com.userservice.user.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

	public User saveUser(User user) {
//    log.info("Inside saveUser method of UserService.");
		return userRepository.save(user);
	}

	public List<UserResource> getAllUsers(String firstName, String lastName) {

		List<User> users;
		if (firstName != null || lastName != null) {
			// Search with provided criteria
			users = userRepository.searchUsers(firstName, lastName);
		} else {
			// Fetch all books
			users = userRepository.findAll();
		}

		List<UserResource> list = new ArrayList<>();
		for (User user : users) {
			list.add(convertDTOtoResource(user));
		}

		return list;

	}

	private UserResource convertDTOtoResource(User user) {
		UserResource res = new UserResource();

		res.setFirstName(user.getFirstName());
		res.setLastName(user.getLastName());
		res.setDepartmentId(user.getDepartmentId());
		res.setEmail(user.getEmail());

		return res;
	}

	public ResponseTemplateVO getUserWithDepartment(Long userId) {
//    log.info("Inside saveUser method of UserService.");
		ResponseTemplateVO vo = new ResponseTemplateVO();
		User user = userRepository.findByUserId(userId);
//
//    Department department = restTemplate.getForObject(
//        "http://DEPARTMENT-SERVICE/departments/" + user.getDepartmentId(),
//        Department.class);
//    vo.setUser(user);
//    vo.setDepartment(department);

		return vo;
	}

}
