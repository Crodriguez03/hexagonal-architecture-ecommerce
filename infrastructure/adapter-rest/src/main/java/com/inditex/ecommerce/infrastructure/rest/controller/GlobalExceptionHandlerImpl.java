package com.inditex.ecommerce.infrastructure.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.inditex.ecommerce.domain.exception.NotFoundException;


@RestController
@ControllerAdvice
public class GlobalExceptionHandlerImpl implements GlobalExceptionHandler {

	@Override
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public String handleValidationExceptions(MethodArgumentTypeMismatchException ex) {
		return "Formato de fecha incorrecto. Formato aceptado: yyyy-MM-ddTHH:mm:ssZ";
	}
	
	@Override
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public String handleNotFoundException(NotFoundException ex) {
		return ex.getMessageError();
	}
	
	@Override
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public String handleInternarServerException(Exception ex) {
		return ex.getMessage();
	}
}