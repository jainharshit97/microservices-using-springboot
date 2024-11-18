package com.microservices.department.pojo;

import java.util.List;

import com.microservices.department.entity.Department;

public class ResponseTemplateVO {
	private List<UserResource> user;
	private Department department;

	public List<UserResource> getUser() {
		return user;
	}

	public void setUser(List<UserResource> user) {
		this.user = user;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

}
