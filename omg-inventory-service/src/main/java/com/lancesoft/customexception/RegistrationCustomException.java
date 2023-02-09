package com.lancesoft.customexception;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Component
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class RegistrationCustomException extends RuntimeException {
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RegistrationCustomException(String errorMessage) {
		super(errorMessage);
	}

}
