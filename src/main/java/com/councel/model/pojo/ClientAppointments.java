package com.councel.model.pojo;

import java.sql.Timestamp;

import javax.persistence.*;

@Entity
public class ClientAppointments {

	@Id @GeneratedValue
	private long appointmentId;
	
	@ManyToOne
	private Lawyer lawyer;
	
	@ManyToOne
	private Client client;
	
	private Timestamp appointmentTime;
	private String venue;
	private String purpose;
	
	
	public long getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(long appointmentId) {
		this.appointmentId = appointmentId;
	}
	public Lawyer getLawyer() {
		return lawyer;
	}
	public void setLawyer(Lawyer lawyer) {
		this.lawyer = lawyer;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public Timestamp getAppointmentTime() {
		return appointmentTime;
	}
	public void setAppointmentTime(Timestamp appointmentTime) {
		this.appointmentTime = appointmentTime;
	}
	public String getVenue() {
		return venue;
	}
	public void setVenue(String venue) {
		this.venue = venue;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	
	
}
