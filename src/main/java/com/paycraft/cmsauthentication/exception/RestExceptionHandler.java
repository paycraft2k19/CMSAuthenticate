package com.paycraft.cmsauthentication.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(DaoException.class)
	public ResponseEntity<ErrorResponse> daoExceptionHandler(Exception e, HttpServletRequest request) {
		return getErrorResponse(e, request);
	}
	
	private ResponseEntity<ErrorResponse> getErrorResponse(Exception e, HttpServletRequest request) {
		ErrorResponse error = new ErrorResponse();
		error.setResponseCode(HttpStatus.BAD_REQUEST.value());
		error.setMessage(e.getMessage());
		error.setUri(request.getRequestURI());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
}
