package com.inditex.ecommerce.infrastructure.rest.controller;

import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.inditex.ecommerce.domain.exception.NotFoundException;

public interface GlobalExceptionHandler {

	String handleValidationExceptions(MethodArgumentTypeMismatchException ex);
	String handleNotFoundException(NotFoundException ex);
	String handleInternarServerException(Exception ex);

}
