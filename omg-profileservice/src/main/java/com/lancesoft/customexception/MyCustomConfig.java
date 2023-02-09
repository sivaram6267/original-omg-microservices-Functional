package com.lancesoft.customexception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class MyCustomConfig {

	@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
	@ExceptionHandler(CustomException.class)
	public Map<String, String> handleBusinessException(CustomException customException) {
		Map<String, String> errorMap = new HashMap<>();
		errorMap.put(customException.getMessage(), customException.getErrorcode());
		return errorMap;
	}
}
