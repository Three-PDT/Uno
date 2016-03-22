package com.example.exception;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.web.bind.MethodArgumentNotValidException;

public class Validator {
	
	final static Pattern postCodePattern = Pattern.compile("(GIR 0AA)|((([A-Z-[QVX]][0-9][0-9]?)|(([A-Z-[QVX]][A-Z-[IJZ]][0-9][0-9]?)|(([A-Z-[QVX]][0-9][A-HJKSTUW])|([A-Z-[QVX]][A-Z-[IJZ]][0-9][ABEHMNPRVWXY]))))\\s?[0-9][A-Z-[CIKMOV]]{2})");
	
	public static void matchPostcode(String postcode){
		Matcher matcher = postCodePattern.matcher(postcode);
		if(matcher.matches()){
			return;
		} else {
			throw new PostcodeValidationException("Invalid postcode:" + postcode);
		}
	}
	
		
}
