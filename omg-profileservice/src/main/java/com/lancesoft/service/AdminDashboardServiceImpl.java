package com.lancesoft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import com.lancesoft.dao.RegistrationRepo;
import com.lancesoft.entity.RegistrationEntity;
import com.lancesoft.payload.ForgotPasswordPayload;

@Service
public class AdminDashboardServiceImpl implements AdminDashboardService {

//	@Autowired
//	JwtUtil jwtUtil;

	@Autowired
	RegistrationRepo registrationRepo;

	@Autowired
	BCryptPasswordEncoder encoder;

//	Logger logger = Logger.getLogger(AdminDashboardServiceImpl.class);

	public ResponseEntity<RegistrationEntity> getMyProfile(@RequestHeader String userName) {

		return new ResponseEntity<RegistrationEntity>(registrationRepo.findByUserName(userName), HttpStatus.OK);

	}

	public ResponseEntity<RegistrationEntity> updateProfile(RegistrationEntity registrationEntity) {
		return new ResponseEntity<RegistrationEntity>(registrationRepo.save(registrationEntity), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> forgotPassword(ForgotPasswordPayload forgotPasswordPayload) {
		RegistrationEntity registrationEntity = registrationRepo.findByUserName(forgotPasswordPayload.getUserName());

		if (encoder.matches(forgotPasswordPayload.getOldPassword(), registrationEntity.getPassword())) {
			registrationEntity.setPassword(encoder.encode(forgotPasswordPayload.getNewPassword()));
			registrationRepo.save(registrationEntity);
			return new ResponseEntity<Object>(HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}

	}

}
