package com.lancesoft.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@ NoArgsConstructor
@AllArgsConstructor
@Data
public  class  ForgotPasswordPayload {
	
	private String userName;
	private String oldPassword;
	private String newPassword;
	
}
