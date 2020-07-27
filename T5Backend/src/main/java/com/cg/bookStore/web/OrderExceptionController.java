package com.cg.bookStore.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cg.bookStore.exceptions.NullArgumentException;

@ControllerAdvice
public class OrderExceptionController {

	@ExceptionHandler(NullArgumentException.class)
	public ResponseEntity<Object> exception(NullArgumentException exception) {
		return new ResponseEntity<>("Null Argument passed", HttpStatus.NOT_FOUND);
	}

}
