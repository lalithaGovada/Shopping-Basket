package com.ecom.priceengine.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerError extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public InternalServerError() {
		super();
	}

	public InternalServerError(String message) {
		super(message);
	}
	
	public InternalServerError(String message, Throwable cause) {
		super(message, cause);
	}

}
