package com.cg.bookStore.web;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cg.bookStore.exceptions.BookNotFoundException;
import com.cg.bookStore.exceptions.InvalidCustomerIdException;

@RestControllerAdvice
public class OrderControllerAdvice {

	/***********************************************************************************************
	 * Method :handleException Description :To handle the InvalidCustomerIdException
	 * .
	 * 
	 * @param ex :Exception instance
	 * @return
	 * @returns void Created By :Anusha Created Date :17-07-2020
	 ************************************************************************************************/
	@ExceptionHandler(value = { InvalidCustomerIdException.class })
	public String handleException(Exception ex) {
		return ex.getMessage();
	}

	@ExceptionHandler(value = { BookNotFoundException.class })
	public String handleException2(Exception ex) {
		return ex.getMessage();
	}

}
