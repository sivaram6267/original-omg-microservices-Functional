package com.example.demo.customexception;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DeliveryDeptCustomException extends RuntimeException {
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DeliveryDeptCustomException(String errorMessage) {
		super(errorMessage);
	}

}
