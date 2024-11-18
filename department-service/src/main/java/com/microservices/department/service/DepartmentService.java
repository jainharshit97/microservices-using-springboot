package com.microservices.department.service;

import java.util.List;

import com.microservices.department.entity.Department;
import com.microservices.department.pojo.ResponseTemplateVO;

public interface DepartmentService {
	public Department saveDepartment(Department department);

	public List<Department> getAllDepartments(Long departmentId, String departmentCode);

	public void updateDepartment(Department department);

	public Department findDepartmentById(Long departmentId);

	public ResponseTemplateVO getDepartmentWithUsers(Long departmentId);
}