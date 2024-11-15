package com.microservices.department.facade;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.microservices.department.entity.Department;
import com.microservices.department.pojo.DepartmentResource;
import com.microservices.department.service.DepartmentService;

@Component
public class DepartmentFacade {

	@Autowired
	DepartmentService departmentService;

	public void addDepartment(DepartmentResource departmentResource) {

		Department department = new Department();
		department.setDepartment_name(departmentResource.getDepartmentName());
		department.setDepartment_code(departmentResource.getDepartmentCode());
		department.setDepartment_address(departmentResource.getDepartmentAddress());

		departmentService.saveDepartment(department);
	}

	public DepartmentResource getDepartmentById(Long departmentId) {

		Department department = departmentService.findDepartmentById(departmentId);

		DepartmentResource resource = new DepartmentResource();
		resource.setDepartmentId(department.getDepartment_id());
		resource.setDepartmentName(department.getDepartment_name());
		resource.setDepartmentCode(department.getDepartment_code());
		resource.setDepartmentAddress(department.getDepartment_address());

		return resource;
	}

	public List<DepartmentResource> getAllDepartments(Long departmentId, String departmentCode) {

		List<Department> departments = departmentService.getAllDepartments(departmentId, departmentCode);

		List<DepartmentResource> list = new ArrayList<>();
		for (Department department : departments) {
			list.add(convertDTOtoResource(department));
		}

		return list;
	}
	
	public void updateDepartment(DepartmentResource departmentResource) {

		Department department = new Department();
		department.setDepartment_name(departmentResource.getDepartmentName());
		department.setDepartment_code(departmentResource.getDepartmentCode());
		department.setDepartment_address(departmentResource.getDepartmentAddress());

		departmentService.updateDepartment(department);
		
	}

	private DepartmentResource convertDTOtoResource(Department department) {
		DepartmentResource res = new DepartmentResource();

		res.setDepartmentId(department.getDepartment_id());
		res.setDepartmentName(department.getDepartment_name());
		res.setDepartmentCode(department.getDepartment_code());
		res.setDepartmentAddress(department.getDepartment_address());

		return res;
	}

}