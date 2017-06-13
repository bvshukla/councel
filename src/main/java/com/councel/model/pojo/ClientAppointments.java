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
	
	private Timestamp appointmentStartTime;
	private Timestamp appointmentEndTime;
	private String venue;
	private String purpose;
	private boolean availableToClients;
	
	
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
	public Timestamp getAppointmentStartTime() {
		return appointmentStartTime;
	}
	public void setAppointmentStartTime(Timestamp appointmentStartTime) {
		this.appointmentStartTime = appointmentStartTime;
	}
	
	public Timestamp getAppointmentEndTime() {
		return appointmentEndTime;
	}
	public void setAppointmentEndTime(Timestamp appointmentEndTime) {
		this.appointmentEndTime = appointmentEndTime;
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
	public boolean getAvailableToClients() {
		return availableToClients;
	}
	public void setAvailableToClients(boolean availableToClients) {
		this.availableToClients = availableToClients;
	}
	
	
	
}
