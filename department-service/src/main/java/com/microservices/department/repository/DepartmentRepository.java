package com.microservices.department.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.microservices.department.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

	@Query("SELECT d FROM department d " + "WHERE (:department_id IS NULL OR b.department_id = :department_id) "
			+ "AND (:department_code IS NULL OR b.department_code = :department_code)")
	List<Department> searchDepartments(@Param("department_id") Long departemnt_id, @Param("department_code") String department_code);

	@Query("Select d from Department d WHERE d.department_id = :department_id")
	Department findByDepartmentId(@Param("department_id") Long department_id);
}
