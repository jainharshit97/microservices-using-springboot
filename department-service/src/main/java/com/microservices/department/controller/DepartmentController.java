package com.microservices.department.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.department.facade.DepartmentFacade;
import com.microservices.department.pojo.DepartmentResource;
import com.microservices.department.pojo.MessageResponse;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/departments")
@Slf4j
public class DepartmentController {
	@Autowired
	private DepartmentFacade departmentFacade;

	@PostMapping("/")
	public ResponseEntity<?> addDepartment(@RequestBody DepartmentResource departmentResource) {
//    log.info("Inside saveDepartment method of DepartmentController.");

		try {
			// Call facade method to add user
			departmentFacade.addDepartment(departmentResource);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Department could not be added!"));
		}

		return ResponseEntity.ok(new MessageResponse("Department successfully added!"));
	}

	// GET - users with filters
	@CrossOrigin(origins = "*", exposedHeaders = "**")
	@GetMapping(value = "/", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> searchDepartment(@RequestParam(value = "departmentId", required = false) Long departmentId,
			@RequestParam(value = "departmentCode", required = false) String departmentCode,
			HttpServletRequest request) {

		// Call facade method to get books with optional search criteria
		List<DepartmentResource> departments = departmentFacade.getAllDepartments(departmentId, departmentCode);
		return ResponseEntity.ok(departments);
	}

	@GetMapping("/{departmentId}")
	public ResponseEntity<?> findDepartmentById(@PathVariable Long departmentId) {
//    log.info("Inside findDepartmentById method of DepartmentController.");

		DepartmentResource resource = new DepartmentResource();
		try {
			resource = departmentFacade.getDepartmentById(departmentId);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Department not Found!"));
		}

		return ResponseEntity.ok(resource);
	}

}
