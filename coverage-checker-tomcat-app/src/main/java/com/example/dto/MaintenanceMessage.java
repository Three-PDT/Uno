package com.example.dto;

import org.springframework.context.MessageSource;


public class MaintenanceMessage {
	private String currentPlannedMessage;
	private String forecastPlannedMessage;
	private String resolvedPlannedMessage;
	private String currentUnplannedMessage;
	private String forecastUnplannedMessage;
	private String resolvedUnplannedMessage;

	

	public MaintenanceMessage(MessageSource ms) {
		setCurrentPlannedMessage(ms.getMessage("maintenance.currentPlannedMessage", null, null));
		setCurrentUnplannedMessage(ms.getMessage("maintenance.currentUnplannedMessage", null, null));
		setForecastPlannedMessage(ms.getMessage("maintenance.forecastPlannedMessage", null, null));
		setForecastUnplannedMessage(ms.getMessage("maintenance.forecastUnplannedMessage", null, null));
		setResolvedPlannedMessage(ms.getMessage("maintenance.resolvedPlannedMessage", null, null));
		setResolvedUnplannedMessage(ms.getMessage("maintenance.resolvedUnplannedMessage", null, null));
	}

	public MaintenanceMessage(){
		
	}
	public String getCurrentPlannedMessage() {
		return currentPlannedMessage;
	}
	public String getForecastPlannedMessage() {
		return forecastPlannedMessage;
	}
	public String getResolvedPlannedMessage() {
		return resolvedPlannedMessage;
	}
	public String getCurrentUnplannedMessage() {
		return currentUnplannedMessage;
	}
	public String getForecastUnplannedMessage() {
		return forecastUnplannedMessage;
	}
	public String getResolvedUnplannedMessage() {
		return resolvedUnplannedMessage;
	}
	public void setCurrentPlannedMessage(String currentPlannedMessage) {
		this.currentPlannedMessage = currentPlannedMessage;

	}
	public void setForecastPlannedMessage(String forecastPlannedMessage) {
		this.forecastPlannedMessage = forecastPlannedMessage;

	}
	public void setResolvedPlannedMessage(String resolvedPlannedMessage) {
		this.resolvedPlannedMessage = resolvedPlannedMessage;

	}
	public void setCurrentUnplannedMessage(String currentUnplannedMessage) {
		this.currentUnplannedMessage = currentUnplannedMessage;

	}
	public void setForecastUnplannedMessage(String forecastUnplannedMessage) {
		this.forecastUnplannedMessage = forecastUnplannedMessage;

	}
	public void setResolvedUnplannedMessage(String resolvedUnplannedMessage) {
		this.resolvedUnplannedMessage = resolvedUnplannedMessage;

	}
}
