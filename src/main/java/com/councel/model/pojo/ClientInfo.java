package com.councel.model.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity

public class ClientInfo {

	@Id
	private Long id;
	
	private String clientName;
	private String phoneNo;
	
	
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
}
