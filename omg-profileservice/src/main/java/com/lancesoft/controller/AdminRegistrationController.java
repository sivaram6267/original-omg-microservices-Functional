package com.lancesoft.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lancesoft.dto.RegistrationDto;
import com.lancesoft.entity.RegistrationEntity;
import com.lancesoft.payload.ForgotPasswordPayload;
import com.lancesoft.service.AdminDashboardService;
import com.lancesoft.service.AdminService;

import io.github.resilience4j.retry.annotation.Retry;

//@CrossOrigin("*")
@RequestMapping("/api/admin")
@RestController
@RefreshScope
public class AdminRegistrationController {
	
	@Autowired
	AdminService adminregservice;
	
	@Autowired
	AdminDashboardService adminDashboardServiceImpl;

	
	@Retry(name = "profile-service")
	@PostMapping("/register")
	public ResponseEntity<?> addAccount(@RequestBody @Valid RegistrationDto registrationdto) {

		return new ResponseEntity<>(adminregservice.addReg(registrationdto), HttpStatus.OK);

	}
	
	
	@Retry(name = "profile-service")
	@GetMapping("/myProfile")
	public ResponseEntity<?> myProfile(@RequestHeader String userName ) {
		try {
			return adminDashboardServiceImpl.getMyProfile(userName);
		} catch (Exception e) {
			return new ResponseEntity<>("exception occured", HttpStatus.BAD_REQUEST);
		}
	}

	
	@Retry(name = "profile-service")
	@PutMapping("/update")
	public ResponseEntity<?> updatedProfile(@RequestBody RegistrationEntity entity) {
		try {
			return adminDashboardServiceImpl.updateProfile(entity);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("exception occured", HttpStatus.BAD_REQUEST);
		}
	}

	@Retry(name = "profile-service")
	@PostMapping("/updatePassword")
	public ResponseEntity<?> updatePassword(@RequestBody ForgotPasswordPayload forgotPasswordPayload) {
		return adminDashboardServiceImpl.forgotPassword(forgotPasswordPayload);	
	}
	
	@PostMapping("/changePassword")
	public ResponseEntity<?> changePassword(@RequestHeader String userName,@RequestBody ForgotPasswordPayload forgotPasswordPayload) {
		forgotPasswordPayload.setUserName(userName);
		return adminDashboardServiceImpl.forgotPassword(forgotPasswordPayload);	
	}
	
}
