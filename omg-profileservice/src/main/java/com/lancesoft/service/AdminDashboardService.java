package com.lancesoft.service;

import org.springframework.http.ResponseEntity;

import com.lancesoft.entity.RegistrationEntity;
import com.lancesoft.payload.ForgotPasswordPayload;

public interface AdminDashboardService  {

public ResponseEntity<?> getMyProfile(String userName);
public ResponseEntity<?> updateProfile(RegistrationEntity registrationEntity);
public ResponseEntity<?> forgotPassword(ForgotPasswordPayload forgotPasswordPayload);

}
