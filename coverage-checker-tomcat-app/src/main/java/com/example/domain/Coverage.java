package com.example.domain;

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
	@Column(name = "L800")
	private int l800;
	@Column(name = "FOURG")
	private int fourg;
	public String getPostcode() {
		return postcode;
	}
	public int getThreeg() {
		return threeg;
	}
	public int getL800() {
		return l800;
	}
	public int getFourg() {
		return fourg;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
		
	}
	public void setThreeg(int threeg) {
		this.threeg = threeg;
		
	}
	public void setL800(int l800) {
		this.l800 = l800;
		
	}
	public void setFourg(int fourg) {
		this.fourg = fourg;
		
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + fourg;
		result = prime * result + l800;
		result = prime * result + ((postcode == null) ? 0 : postcode.hashCode());
		result = prime * result + threeg;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coverage other = (Coverage) obj;
		if (fourg != other.fourg)
			return false;
		if (l800 != other.l800)
			return false;
		if (postcode == null) {
			if (other.postcode != null)
				return false;
		} else if (!postcode.equals(other.postcode))
			return false;
		if (threeg != other.threeg)
			return false;
		return true;
	}
	
}
