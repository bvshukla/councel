package com.councel.model.pojo;

import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="Lawyer")
@PrimaryKeyJoinColumn(name="userId")
@SqlResultSetMapping(
	    name = "LawyerWithClient",
	    entities = @EntityResult(entityClass = Lawyer.class),
	    columns = @ColumnResult(name = "CLIENTS"))
public class Lawyer extends User {
	
	

	public Lawyer() {
		super();
		super.setUserType(UserType.LAWYER);
	}
	
	private String lawFirm;
	private String courtName;
	private String location;
	private String contactFor;
	private String experience;
	private String bio;
	
	@Transient
	private Integer clientsCount;
	
	@Transient
	private List<ClientInfo> clientsInfo;
	
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
	public Integer getClientsCount() {
		return clientsCount;
	}
	public void setClientsCount(Integer cnt) {
		this.clientsCount = cnt;
	}
	public List<ClientInfo> getClientsInfo() {
		return clientsInfo;
	}
	public void setClientsInfo(List<ClientInfo> clientsInfo) {
		this.clientsInfo = clientsInfo;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getContactFor() {
		return contactFor;
	}
	public void setContactFor(String contactFor) {
		this.contactFor = contactFor;
	}
	public String getExperience() {
		return experience;
	}
	public void setExperience(String experience) {
		this.experience = experience;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	
	
}
