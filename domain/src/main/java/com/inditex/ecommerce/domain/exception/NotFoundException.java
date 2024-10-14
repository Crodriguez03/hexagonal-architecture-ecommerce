package com.inditex.ecommerce.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class NotFoundException extends RuntimeException {
	private static final long serialVersionUID = -7431654623692738145L;
	
	private final String messageError;
}
