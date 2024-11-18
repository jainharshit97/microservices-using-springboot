package com.microservices.department.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.department.config.DepartmentMessageProducer;
import com.microservices.department.facade.DepartmentFacade;
import com.microservices.department.pojo.DepartmentResource;
import com.microservices.department.pojo.MessageResponse;
import com.microservices.department.pojo.ResponseTemplateVO;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/departments")
@Slf4j
public class DepartmentController {

	private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);

	@Autowired
	private DepartmentFacade departmentFacade;

	@Autowired
	DepartmentMessageProducer departmentMessageProducer;

	@PostMapping("/")
	public ResponseEntity<?> addDepartment(@RequestBody DepartmentResource departmentResource) {
		logger.info("Inside saveDepartment method of DepartmentController.");

		try {
			// Call facade method to add user
			departmentFacade.addDepartment(departmentResource);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Department could not be added!"));
		}

		departmentMessageProducer.sendDepartmentCreatedMessage("New department created: " + departmentResource.getDepartmentName());

		return ResponseEntity.ok(new MessageResponse("Department successfully added!"));
	}

	@PutMapping("/updateDepartment")
	public ResponseEntity<?> updateDepartment(@RequestBody DepartmentResource departmentResource) {
		logger.info("Inside saveDepartment method of DepartmentController.");

		try {
			// Call facade method to add user
			departmentFacade.updateDepartment(departmentResource);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Department could not be updated!"));
		}

		return ResponseEntity.ok(new MessageResponse("Department successfully updated!"));
	}

	// GET - department with filters
	@GetMapping(value = "/", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> searchDepartment(@RequestParam(value = "departmentId", required = false) Long departmentId,
			@RequestParam(value = "departmentCode", required = false) String departmentCode,
			HttpServletRequest request) {

		logger.info("Inside searhcDepartment method of DepartmentController.");

		// Call facade method to get books with optional search criteria
		List<DepartmentResource> departments = departmentFacade.getAllDepartments(departmentId, departmentCode);
		return ResponseEntity.ok(departments);
	}

	@GetMapping("/{departmentId}")
	public ResponseEntity<?> findDepartmentById(@PathVariable Long departmentId) {
		logger.info("Inside findDepartmentById method of DepartmentController.");

		DepartmentResource resource = new DepartmentResource();
		try {
			resource = departmentFacade.getDepartmentById(departmentId);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Department not Found!"));
		}

		return ResponseEntity.ok(resource);
	}

	@GetMapping("/users/{departmentId}")
	public ResponseEntity<?> getDepartmentWithUsers(@PathVariable Long departmentId) {

		logger.info("Fetching department with users method of DepartmentController.");

		ResponseTemplateVO vo = new ResponseTemplateVO();

		try {
			vo = departmentFacade.getDepartmentWithUsers(departmentId);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Data could not be retrived!"));
		}

		return ResponseEntity.ok(vo);
	}

}