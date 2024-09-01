package com.devgroup.basic.exceptionHandlers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyExceptionHandler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> validationExceptionHandler(MethodArgumentNotValidException ex){
		Map<String, String> res = new HashMap<String, String>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String field = ((FieldError) error).getField();
			String defaultMessage = error.getDefaultMessage();
			res.put(field, defaultMessage);
		});
		return new ResponseEntity<Map<String,String>>(res, HttpStatus.BAD_REQUEST);
	} 	
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<String> methodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException ex){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You are making wrong type of request");
	}
}
