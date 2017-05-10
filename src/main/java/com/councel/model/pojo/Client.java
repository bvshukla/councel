package com.councel.model.pojo;

import javax.persistence.*;

@Entity
@PrimaryKeyJoinColumn(name="userId")
public class Client extends User {


	

	public Client() {
		super();
		super.setUserType(UserType.CLIENT);
		// TODO Auto-generated constructor stub
	}

	@ManyToOne(cascade = CascadeType.ALL,targetEntity=Lawyer.class)
	@JoinColumn(name = "lawyer_userId",updatable=true,insertable=true)
	//@Column(name="lawyerId")
	private Lawyer lawyer;

	public Lawyer getLawyer() {
		return lawyer;
	}

	public void setLawyer(Lawyer lawyer) {
		this.lawyer = lawyer;
	}
	
	
}
