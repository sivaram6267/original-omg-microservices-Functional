package com.lancesoft;

import org.springframework.http.ResponseEntity;

import com.lancesoft.entity.RegistrationEntity;
import com.lancesoft.payload.ForgotPasswordPayload;

public interface AdminDashboardService {

	public void getMyProfile();

	public ResponseEntity<?> updateProfile(RegistrationEntity registrationEntity);

	public ResponseEntity<?> forgotPassword(ForgotPasswordPayload forgotPasswordPayload);
}
