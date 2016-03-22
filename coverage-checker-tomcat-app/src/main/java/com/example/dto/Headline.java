package com.example.dto;

import org.springframework.context.MessageSource;

public class Headline {
	private boolean available;
	private String headline;
	private String bodytext;

	public Headline() {

	}

	public boolean isAvailable() {
		return available;
	}
	public String getHeadline() {
		return headline;
	}
	public String getBodytext() {
		return bodytext;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	public void setHeadline(String headline) {
		this.headline = headline;
	}
	public void setBodytext(String bodytext) {
		this.bodytext = bodytext;
	}

}
