package com.councel.model.pojo;

import java.sql.Timestamp;

import javax.persistence.*;

@Entity
public class CaseHearings {

	@Id @GeneratedValue
	private long hearingId;
	
	@ManyToOne
	@JoinColumn(name = "userCase",insertable=false, updatable=false)
	private ClientCase userCase;
	private Timestamp hearingDate;
	private String hearingVenue;
	private String hearingDetails;
	
	
	public long getHearingId() {
		return hearingId;
	}
	public void setHearingId(long hearingId) {
		this.hearingId = hearingId;
	}
	public ClientCase getUserCase() {
		return userCase;
	}
	public void setUserCase(ClientCase userCase) {
		this.userCase = userCase;
	}
	public Timestamp getHearingDate() {
		return hearingDate;
	}
	public void setHearingDate(Timestamp hearingDate) {
		this.hearingDate = hearingDate;
	}
	public String getHearingVenue() {
		return hearingVenue;
	}
	public void setHearingVenue(String hearingVenue) {
		this.hearingVenue = hearingVenue;
	}
	public String getHearingDetails() {
		return hearingDetails;
	}
	public void setHearingDetails(String hearingDetails) {
		this.hearingDetails = hearingDetails;
	}
	
	
}
