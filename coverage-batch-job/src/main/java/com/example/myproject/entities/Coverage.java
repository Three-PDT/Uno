package com.example.myproject.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "COVERAGE_CHECKER")
public class Coverage {
	@Id
	@Column(name = "POSTCODE")
	private String postcode;
	@Column(name = "THREEG")
	private int threeg;
	@Column(name = "FOURG")
	private int fourg;
	@Column(name = "L800")
	private int l800;
	public String getPostcode() {
		return postcode;
	}
	public int getThreeg() {
		return threeg;
	}
	public int getFourg() {
		return fourg;
	}
	public int getL800() {
		return l800;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public void setThreeg(int threeg) {
		this.threeg = threeg;
	}
	public void setFourg(int fourg) {
		this.fourg = fourg;
	}
	public void setL800(int l800) {
		this.l800 = l800;
	}


	@Override
	public String toString() {
		return "Coverage [postcode=" + postcode + ", threeg=" + threeg + ", fourg=" + fourg + ", l800=" + l800
				+ "]";
	}


	
}
