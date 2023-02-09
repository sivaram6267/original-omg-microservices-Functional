package com.lancesoft.controller;

import java.text.ParseException;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lancesoft.customexception.CustomException;
import com.lancesoft.dao.RegistrationRepo;
import com.lancesoft.dto.AddressDto;
import com.lancesoft.dto.JwtLoginDto;
import com.lancesoft.dto.RegistrationDto;
import com.lancesoft.dto.RegistrationVerfication;
import com.lancesoft.entity.AddressEntity;
import com.lancesoft.entity.RegistrationEntity;
import com.lancesoft.jwt.JwtTokenString;
import com.lancesoft.jwt.JwtUtil;
import com.lancesoft.jwt.Token;
import com.lancesoft.payload.ForgotPasscode;
import com.lancesoft.payload.OtpValidationPayload;
import com.lancesoft.payload.TwilioPasscode;
import com.lancesoft.payload.TwilioPasscodeValidation;
import com.lancesoft.service.JavaMailService;
import com.lancesoft.service.OtpValidationService;
import com.lancesoft.service.RegistrationService;
import com.lancesoft.service.TwilioOtpService;
import com.lancesoft.service.UserDashboardService;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.retry.annotation.Retry;
import io.jsonwebtoken.ExpiredJwtException;

//@CrossOrigin("*")
@RestController
@RequestMapping("/api")
@RefreshScope
public class RegistrationController {

	@Autowired
	RegistrationService registrationService;

	@Autowired
	JavaMailService mailService;

	@Autowired
	OtpValidationService otpvalid;

	@Autowired
	TwilioOtpService twilioservice;

	@Autowired
	HttpSession session;

	@Autowired
	JwtUtil jwtUtil;

	@Autowired
	RegistrationRepo registrationRepo;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	UserDashboardService userDashboardService;

	@Retry(name = "profile-service")
	@PostMapping("/user/sendOtp")
	public void userOtp(@RequestBody RegistrationVerfication verification) throws ParseException {
		twilioservice.sendRegisterOtp(verification);
	}
	
	@Retry(name = "profile-service")
	@PostMapping("/sendOtp")
	public boolean forgotPassword(@RequestBody RegistrationVerfication verification) throws ParseException {
		return twilioservice.sendRegisterOtp(verification);
	}


	@Retry(name = "profile-service")
	@PostMapping("/user/register")
	public ResponseEntity<?> addAccount(@RequestBody @Valid RegistrationDto registrationDto) {
		System.out.println("called");
		return registrationService.addReg(registrationDto);

	}

	@Retry(name = "profile-service")
	@PostMapping("/email")
	public ResponseEntity<?> checkEmail(@RequestBody ForgotPasscode forgotPasscode) {
		ResponseEntity<?> res = registrationService.CheckingEmail(forgotPasscode.getEmail());
		System.out.println(res);
		mailService.sendMail(forgotPasscode.getEmail());
		return res;

	}

	@Retry(name = "profile-service")
	@PostMapping("/validate")
	public ResponseEntity<?> checkEmail(@RequestBody OtpValidationPayload otpValidationPayload) {

		return otpvalid.reset(otpValidationPayload);
	}

	@Retry(name = "profile-service")
	@PostMapping("/verifyPhone")
	public ResponseEntity<?> checkMobile(@RequestBody TwilioPasscode forgotPasscode) throws ParseException {
		return twilioservice.send(forgotPasscode);
	}

	@Retry(name = "profile-service")
	@Bulkhead(name = "default")
	@PostMapping("/verify")
	public ResponseEntity<?> checkMobile(@RequestBody TwilioPasscodeValidation forgotPasscodevalidation)
			throws ParseException {
		return twilioservice.validate(forgotPasscodevalidation);
	}

	@Retry(name = "profile-service")
	@PostMapping("/login")
	public JwtTokenString generateToken(@RequestBody JwtLoginDto authRequest) throws Exception {

		return userDashboardService.login(authRequest);
		
	}
	
	@Retry(name = "profile-service")
	@PostMapping("/user/updateaddress")
	public AddressEntity updateaddress(@RequestBody AddressDto addressdto) {
		return userDashboardService.updateAddress(addressdto);
	}
	
	@Retry(name = "profile-service")
	@PostMapping("/user/addAddress")
	public AddressEntity addAddress(@RequestBody AddressDto addressdto) {
		return userDashboardService.addAddress(addressdto);
	}

}
