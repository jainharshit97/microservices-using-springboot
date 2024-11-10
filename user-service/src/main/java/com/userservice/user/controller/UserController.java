package com.userservice.user.controller;

import java.util.List;

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

import com.userservice.pojo.MessageResponse;
import com.userservice.pojo.ResponseTemplateVO;
import com.userservice.pojo.UserResource;
import com.userservice.user.facade.UserFacade;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
	@Autowired
	private UserFacade userFacade;

	@CrossOrigin(origins = "*", exposedHeaders = "**")
	@PostMapping(value = "/addUser", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> addUser(@RequestBody UserResource userResource) {

		try {
			// Call facade method to add user
			userFacade.addUser(userResource);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: User could not be added!"));
		}

		return ResponseEntity.ok(new MessageResponse("User successfully added!"));
	}

	// GET - users with filters
	@CrossOrigin(origins = "*", exposedHeaders = "**")
	@GetMapping(value = "/", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> searchBooks(@RequestParam(value = "firstName", required = false) String firstName,
			@RequestParam(value = "lastName", required = false) String lastName, HttpServletRequest request) {

		// Call facade method to get books with optional search criteria
		List<UserResource> users = userFacade.getAllUsers(firstName, lastName);
		return ResponseEntity.ok(users);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<?> getUserWithDepartment(@PathVariable Long userId) {
//    log.info("Inside getUserWithDepartment method of UserController.");

		ResponseTemplateVO vo = new ResponseTemplateVO();
		try {
			vo = userFacade.getUserWithDepartment(userId);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: User could not be added!"));
		}

		return ResponseEntity.ok(vo);
	}

}
