package com.example.demo.customexception;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CustomException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String errorcode="";
	public CustomException(String errorcode ,String errorMessage ) {
		super(errorcode);
		this.errorcode= errorMessage;
	}

}
