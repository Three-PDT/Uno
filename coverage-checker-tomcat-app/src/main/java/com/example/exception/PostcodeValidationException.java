package com.example.exception;

public class PostcodeValidationException extends RuntimeException {
	public PostcodeValidationException(){
		
	}
	public PostcodeValidationException(String message){
		super(message);
	}

}
