package com.example.myproject.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Record {
	@Id
	private String postcode;
	private int coverage;
	private CoverageType coverageType;
	public String getPostcode() {
		return postcode;
	}
	public int getCoverage() {
		return coverage;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public void setCoverage(int coverage) {
		this.coverage = coverage;
	}

	public CoverageType getCoverageType() {
		return coverageType;
	}
	public void setCoverageType(CoverageType coverageType) {
		this.coverageType = coverageType;
	}
	@Override
	public String toString() {
		return "Record [postcode=" + postcode + ", coverage=" + coverage + ", coverageType=" + coverageType
				+ "]";
	}
}
