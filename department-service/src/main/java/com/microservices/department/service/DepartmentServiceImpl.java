package com.microservices.department.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservices.department.entity.Department;
import com.microservices.department.repository.DepartmentRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentRepository departmentRepository;

	public Department saveDepartment(Department department) {
//		log.info("Inside saveDepartment of DepartmentService.");
		return departmentRepository.save(department);
	}

	public List<Department> getAllDepartments(Long departmentId, String departmentCode) {

		List<Department> departments;
		if (departmentId != null || departmentCode != null) {
			// Search with provided criteria
			departments = departmentRepository.searchDepartments(departmentId, departmentCode);
		} else {
			// Fetch all departments
			departments = departmentRepository.findAll();
		}

		return departments;
	}

	public void updateDepartment(Department department) {
		try {
			departmentRepository.updateDepartment(department.getDepartment_id(), department.getDepartment_name(),
					department.getDepartment_code(), department.getDepartment_address());
			
		} catch (Exception e) {
			throw e;
		}
	}

	public Department findDepartmentById(Long departmentId) {
//		log.info("Inside findDepartmentById of DepartmentService.");
		return departmentRepository.findByDepartmentId(departmentId);
	}
}