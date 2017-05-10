package com.councel.model.pojo;

import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="Lawyer")
@PrimaryKeyJoinColumn(name="userId")
public class Lawyer extends User {
	
	

	public Lawyer() {
		super();
		super.setUserType(UserType.LAWYER);
	}
	
	private String lawFirm;
	private String courtName;
	
	@Transient
	private Long clients;
	
	
	public String getLawFirm() {
		return lawFirm;
	}
	public void setLawFirm(String lawFirm) {
		this.lawFirm = lawFirm;
	}
	public String getCourtName() {
		return courtName;
	}
	public void setCourtName(String courtName) {
		this.courtName = courtName;
	}
	
	public Long getClients() {
		return clients;
	}
	public void setClients(Long clients) {
		this.clients = clients;
	}
	
	
}
