package com.cg.bookStore.exceptions;

public class InvalidQuantityException extends Exception{

	@Override
	public String getMessage() {
		
		return "Invalid quantity added";
	}

	
	
}
