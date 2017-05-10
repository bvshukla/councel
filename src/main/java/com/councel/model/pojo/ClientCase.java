package com.councel.model.pojo;

import java.util.List;

import javax.persistence.*;

@Entity

public class ClientCase {

	public enum CaseType{
		CIVIL,
		CRIMINAL
	}
	
	@Id @GeneratedValue
	private long caseId;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "client")
	private Client client;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "lawyer")
	private Lawyer lawyer;
	
	@OneToMany(fetch = FetchType.EAGER,mappedBy = "userCase",cascade = CascadeType.ALL)
	private List<CaseHearings> hearings;
	
	private CaseType caseType;
	
	private String caseSummary;

	public long getCaseId() {
		return caseId;
	}

	public void setCaseId(long caseId) {
		this.caseId = caseId;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public CaseType getCaseType() {
		return caseType;
	}

	public void setCaseType(CaseType caseType) {
		this.caseType = caseType;
	}

	public String getCaseSummary() {
		return caseSummary;
	}

	public void setCaseSummary(String caseSummary) {
		this.caseSummary = caseSummary;
	}

	public Lawyer getLawyer() {
		return lawyer;
	}

	public void setLawyer(Lawyer lawyer) {
		this.lawyer = lawyer;
	}

	public List<CaseHearings> getHearings() {
		return hearings;
	}

	public void setHearings(List<CaseHearings> hearings) {
		this.hearings = hearings;
	}
	 
	
	
}
