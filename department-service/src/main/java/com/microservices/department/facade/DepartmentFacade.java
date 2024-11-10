package com.microservices.department.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.microservices.department.entity.Department;
import com.microservices.department.pojo.DepartmentResource;
import com.microservices.department.service.DepartmentService;

public class DepartmentFacade {

	@Autowired
	DepartmentService departmentService;

	public void addDepartment(DepartmentResource departmentResource) {

		Department department = new Department();
		department.setDepartmentName(departmentResource.getDepartmentName());
		department.setDepartmentCode(departmentResource.getDepartmentCode());
		department.setDepartmentAddress(departmentResource.getDepartmentAddress());

		departmentService.saveDepartment(department);
	}

	public DepartmentResource getDepartmentById(Long departmentId) {

		Department department = departmentService.findDepartmentById(departmentId);

		DepartmentResource resource = new DepartmentResource();
		resource.setDepartmentName(department.getDepartmentName());
		resource.setDepartmentCode(department.getDepartmentCode());
		resource.setDepartmentAddress(department.getDepartmentAddress());

		return resource;
	}

	public List<DepartmentResource> getAllDepartments(Long departmentId, String departmentCode) {

		List<DepartmentResource> list = departmentService.getAllDepartments(departmentId, departmentCode);

		return list;
	}

}
