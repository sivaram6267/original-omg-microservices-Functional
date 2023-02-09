package com.lancesoft.customexception;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
public class RegistrationCustomException extends RuntimeException {
	 
	public RegistrationCustomException(String errorMessage) {
		super(errorMessage);
	}

}
