package com.example.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

//SELECT np.POSTCODE_ID, pm.START_DATE, pm.FINISH_DATE, pm.outage, pm.network, pm.status, pm.BREACH_ID
@Entity
@Table(name = "PLANNED_MAINTENANCE")
public class Maintenance implements Serializable{

	// @Column(name="POSTCODE_ID")
	// private String postcode;
	
	/**
	 * Hibernate only works with a primary key
	 */
	
	@Id
	@Column(name = "PLANNED_MAINTENANCE_ID")
	private long id;
	@Column(name = "START_DATE")
    //@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date startDate;
	@Column(name = "FINISH_DATE")
    //@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date finishDate;
	@Column(name = "OUTAGE")
	private String outageType;
	@Column(name = "NETWORK")
	private String networkType;
	@Column(name = "STATUS")
	private String status;
	@Column(name = "BREACH_ID")
	private String breachId;
	public long getId() {
		return id;
	}
//	public String getPostcode() {
//		return postcode;
//	}
	public Date getStartDate() {
		return startDate;
	}
	public Date getFinishDate() {
		return finishDate;
	}
	public String getOutageType() {
		return outageType;
	}
	public String getNetworkType() {
		return networkType;
	}
	public String getStatus() {
		return status;
	}
	public String getBreachId() {
		return breachId;
	}
//	public void setPostcode(String postcode) {
//		this.postcode = postcode;
//
//	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;

	}
	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;

	}
	public void setOutageType(String outageType) {
		this.outageType = outageType;

	}
	public void setNetworkType(String networkType) {
		this.networkType = networkType;

	}
	public void setStatus(String status) {
		this.status = status;

	}
	public void setBreachId(String breachId) {
		this.breachId = breachId;

	}
}
