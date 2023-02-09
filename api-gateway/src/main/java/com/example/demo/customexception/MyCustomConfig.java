package com.example.demo.customexception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
