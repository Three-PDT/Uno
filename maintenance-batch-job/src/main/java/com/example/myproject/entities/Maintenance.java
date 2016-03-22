package com.example.myproject.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PLANNED_MAINTENANCE")
public class Maintenance {

	@Column(name = "NOMINAL_ID")
	private String nominalId;

	@Column(name = "START_DATE")
	private Date startDate;

	@Column(name = "FINISH_DATE")
	private Date finishDate;

	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "NETWORK")
	private String Network;

	@Column(name = "MBNL_Site_ID")
	private String MBNL_Site_ID;

	@Column(name = "OUTAGE")
	private String outage;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "SERVICE")
	private String service;

	@Id
	@Column(name = "REFERENCE")
	private String reference;

	// @Id
	// @GeneratedValue(strategy=GenerationType.SEQUENCE)
	// @GeneratedValue
	// @Column(name = "PLANNED_MAINTENANCE_ID")
	// private long plannedMaintenanceId;

//	public long getplannedMaintenanceId() {
//		return plannedMaintenanceId;
//	}
//
//	public void setplannedMaintenanceId(long plannedMaintenanceId) {
//		this.plannedMaintenanceId = plannedMaintenanceId;
//
//	}

	@Column(name = "BREACH_ID")
	private String breachId;

	public String getNominalId() {
		return nominalId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public String getDescription() {
		return description;
	}

	public String getNetwork() {
		return Network;
	}

	public String getMBNL_Site_ID() {
		return MBNL_Site_ID;
	}

	public String getOutage() {
		return outage;
	}

	public String getStatus() {
		return status;
	}

	public String getService() {
		return service;
	}

	public String getReference() {
		return reference;
	}

	public String getbreachId() {
		return breachId;
	}

	public void setNominalId(String nominalId) {
		this.nominalId = nominalId;

	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;

	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;

	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;

	}

	public void setDescription(String description) {
		this.description = description;

	}

	public void setNetwork(String network) {
		Network = network;

	}

	public void setMBNL_Site_ID(String mBNL_Site_ID) {
		MBNL_Site_ID = mBNL_Site_ID;

	}

	public void setOutage(String outage) {
		this.outage = outage;

	}

	public void setStatus(String status) {
		this.status = status;

	}

	public void setService(String service) {
		this.service = service;

	}

	public void setReference(String reference) {
		this.reference = reference;

	}

	public void setBREACH_ID(String breachId) {
		this.breachId = breachId;

	}

	public String getBreachId() {
		return breachId;
	}

	public void setBreachId(String breachId) {
		this.breachId = breachId;
	}

	@Override
	public String toString() {
		return "Maintenance [nominalId=" + nominalId + ", startDate=" + startDate + ", finishDate="
				+ finishDate + ", createdDate=" + createdDate + ", description=" + description + ", Network="
				+ Network + ", MBNL_Site_ID=" + MBNL_Site_ID + ", outage=" + outage + ", status=" + status
				+ ", service=" + service + ", reference=" + reference + ", breachId=" + breachId + "]";
	}
}
