package com.lancesoft.customexception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BusinessException extends RuntimeException{
	
	
	private static final long serialVersionUID = 1L;
	private String errorMessage;
	private String errorCode;

}
