package com.lancesoft.customexception;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
public class CustomException extends RuntimeException {
	String errorcode="";
	public CustomException(String errorcode ,String errorMessage ) {
		super(errorcode);
		this.errorcode= errorMessage;
	}

}
