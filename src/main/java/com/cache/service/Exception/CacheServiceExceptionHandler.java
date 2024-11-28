package com.cache.service.Exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CacheServiceExceptionHandler {

	@ExceptionHandler
	public static void handleException(String methodName, Exception ex) {
		System.err.println("Error in method " + methodName + ": " + ex.getMessage());
		ex.printStackTrace();

	}
}
