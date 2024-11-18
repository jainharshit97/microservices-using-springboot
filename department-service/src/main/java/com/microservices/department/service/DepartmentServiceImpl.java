package com.microservices.department.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservices.department.entity.Department;
import com.microservices.department.pojo.ResponseTemplateVO;
import com.microservices.department.pojo.UserResource;
import com.microservices.department.repository.DepartmentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

	private static final Logger logger = LoggerFactory.getLogger(DepartmentServiceImpl.class);

	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private RestTemplate restTemplate;

	public Department saveDepartment(Department department) {

		logger.debug("Saving department in service.");
		return departmentRepository.save(department);
	}

	public List<Department> getAllDepartments(Long departmentId, String departmentCode) {

		logger.debug("Fetching department in service.");
		List<Department> departments;
		if (departmentId != null || departmentCode != null) {

			logger.debug("Fetching department in service based on search criteria.");

			// Search with provided criteria
			departments = departmentRepository.searchDepartments(departmentId, departmentCode);
		} else {

			logger.debug("Fetching all departments in service.");

			// Fetch all departments
			departments = departmentRepository.findAll();
		}

		return departments;
	}

	public void updateDepartment(Department department) {

		logger.debug("Updating department in service.");
		
		logger.debug("department.getDepartment_id() : " + department.getDepartment_id() + 
				"department.getDepartment_name() : " + department.getDepartment_name() + 
				"department.getDepartment_code() : " + department.getDepartment_code() + 
				"department.getDepartment_address() : " + department.getDepartment_address());
		try {
			departmentRepository.updateDepartment(department.getDepartment_id(), department.getDepartment_name(),
					department.getDepartment_code(), department.getDepartment_address());

		} catch (Exception e) {
			throw e;
		}
	}

	public Department findDepartmentById(Long departmentId) {

		logger.debug("Inside findDepartmentById of DepartmentService.");
		return departmentRepository.findByDepartmentId(departmentId);
	}

	public ResponseTemplateVO getDepartmentWithUsers(Long departmentId) {

		ResponseTemplateVO vo = new ResponseTemplateVO();
		Optional<Department> departmentOption = departmentRepository.findById(departmentId);

		Department department = departmentOption.get();

		logger.debug("retriveingg data !!!!!!!!!!");

		ResponseEntity<List<UserResource>> response = restTemplate.exchange(
				"http://USERS/users/department/" + departmentId, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<UserResource>>() {
				});

		List<UserResource> users = response.getBody();

		logger.debug("fetched data !!!!!!!!!!");
		vo.setDepartment(department);
		vo.setUser(users);

		return vo;

	}
}