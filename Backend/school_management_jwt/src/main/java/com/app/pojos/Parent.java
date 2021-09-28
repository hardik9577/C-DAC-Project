package com.app.pojos;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@DiscriminatorValue(value = "parent")
@PrimaryKeyJoinColumn(name = "user_id")
@Table(name = "parents")
public class Parent extends User{
	@Column(length = 20)
	private String name;
	@Column(length = 300)
	private String address;
	@Column(length = 15, unique = true, name = "contact_no")
	private String contactNo;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	
}
