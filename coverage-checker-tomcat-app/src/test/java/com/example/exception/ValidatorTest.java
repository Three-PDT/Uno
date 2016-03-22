package com.example.exception;

import org.junit.Test;

import com.example.exception.Validator;

public class ValidatorTest {

	@Test
	public void testPostcode(){
		Validator.matchPostcode("AB21 9BU");
	}
}
