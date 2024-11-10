package com.microservices.department.service;

import java.util.List;

import com.microservices.department.entity.Department;
import com.microservices.department.pojo.DepartmentResource;

public interface DepartmentService {
	public Department saveDepartment(Department department);

	public List<DepartmentResource> getAllDepartments(Long departmentId, String departmentCode);

	public Department findDepartmentById(Long departmentId);
}
