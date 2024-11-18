package com.microservices.department.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.microservices.department.entity.Department;

import jakarta.transaction.Transactional;

@Transactional
public interface DepartmentRepository extends JpaRepository<Department, Long> {

	@Query("SELECT d FROM Department d " + "WHERE (:department_id IS NULL OR d.department_id = :department_id) "
			+ "AND (:department_code IS NULL OR d.department_code = :department_code)")
	List<Department> searchDepartments(@Param("department_id") Long department_id,
			@Param("department_code") String department_code);

	@Query("Select d from Department d WHERE d.department_id = :department_id")
	Department findByDepartmentId(@Param("department_id") Long department_id);

	@Modifying
	@Query(value = "UPDATE department SET department_name = ?2 , department_code = ?3, department_address = ?4 WHERE department_id = ?1", nativeQuery = true)
	void updateDepartment(Long department_id, String department_name, String department_code,
			String department_address);
}