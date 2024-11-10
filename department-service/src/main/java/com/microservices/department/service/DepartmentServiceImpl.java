package com.microservices.department.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservices.department.entity.Department;
import com.microservices.department.pojo.DepartmentResource;
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

	public List<DepartmentResource> getAllDepartments(Long departmentId, String departmentCode) {

		List<Department> departments;
		if (departmentId != null || departmentCode != null) {
			// Search with provided criteria
			departments = departmentRepository.searchDepartments(departmentId, departmentCode);
		} else {
			// Fetch all books
			departments = departmentRepository.findAll();
		}

		List<DepartmentResource> list = new ArrayList<>();
		for (Department department : departments) {
			list.add(convertDTOtoResource(department));
		}

		return list;
	}

	private DepartmentResource convertDTOtoResource(Department department) {
		DepartmentResource res = new DepartmentResource();

		res.setDepartmentName(department.getDepartmentName());
		res.setDepartmentCode(department.getDepartmentCode());
		res.setDepartmentAddress(department.getDepartmentAddress());

		return res;
	}

	public Department findDepartmentById(Long departmentId) {
//		log.info("Inside findDepartmentById of DepartmentService.");
		return departmentRepository.findByDepartmentId(departmentId);
	}
}
