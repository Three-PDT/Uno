package com.example.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.example.domain.Coverage;
import com.example.domain.Maintenance;

/**
 * No business logic here - all in service layer. this DTO will manipulate
 * domain objects into whatever the clients should see
 * 
 * DTO model is to make message structurally compatible with front end
 * @author jbwtan
 *
 */

public class CoverageResult {
	
	private String maintenance;
	private List<Maintenance> maintenancePlannedResults = new ArrayList<Maintenance>();
	private List<Maintenance> maintenanceUnplannedResults = new ArrayList<Maintenance>();
	private MaintenanceMessage maintenanceMessage;
	private Headline threeg;
	private Headline fourg;
	private Coverage deviceSpeed;
	
	public CoverageResult(){
		
	}
	public String getMaintenance(){
		return maintenance;
	}
	public List<Maintenance> getMaintenancePlannedResults() {
		return maintenancePlannedResults;
	}
	public List<Maintenance> getMaintenanceUnplannedResults() {
		return maintenanceUnplannedResults;
	}
	public MaintenanceMessage getMaintenanceMessage() {
		return maintenanceMessage;
	}
	public Headline getThreeg() {
		return threeg;
	}

	public Headline getFourg() {
		return fourg;
	}
	
	public Coverage getDeviceSpeed(){
		return deviceSpeed;
	}
	public void setMaintenance(String maintenance) {
		this.maintenance = maintenance;
		
	}
	public void setMaintenancePlannedResults(List<Maintenance> maintenancePlannedResults) {
		this.maintenancePlannedResults = maintenancePlannedResults;
		
	}
	public void setMaintenanceUnplannedResults(List<Maintenance> maintenanceUnplannedResults) {
		this.maintenanceUnplannedResults = maintenanceUnplannedResults;
		
	}
	public void setMaintenanceMessage(MaintenanceMessage maintenanceMessage) {
		this.maintenanceMessage = maintenanceMessage;
	}
	public void setThreeg(Headline threeg) {
		this.threeg = threeg;		
	}
	public void setFourg(Headline fourg) {
		this.fourg = fourg;
	}
	public void setDeviceSpeed(Coverage coverage){
		this.deviceSpeed = coverage;
	}
	

}
