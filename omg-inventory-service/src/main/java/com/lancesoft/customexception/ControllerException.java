package com.lancesoft.customexception;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Component
@Data
@EqualsAndHashCode(callSuper = false) 
@NoArgsConstructor
@AllArgsConstructor
public class ControllerException extends RuntimeException {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errorMessage;
	private String errorCode;

}
