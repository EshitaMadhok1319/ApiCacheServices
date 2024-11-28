package com.cache.service.Dao;

import javax.persistence.Id;

@javax.persistence.Entity
public class Employees {

    @Id
	private String employeeID;
    private String employeeName;
	private String employeeAddress;
	private String employeePhoneNumber;
	
	public Employees() {
	}
	
	public Employees(String employeeID, String employeeName, String employeeAddress, String employeePhoneNumber) {
		super();
		this.employeeID = employeeID;
		this.employeeName = employeeName;
		this.employeeAddress = employeeAddress;
		this.employeePhoneNumber = employeePhoneNumber;
	}

	public String getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeeAddress() {
		return employeeAddress;
	}

	public void setEmployeeAddress(String employeeAddress) {
		this.employeeAddress = employeeAddress;
	}

	public String getEmployeePhoneNumber() {
		return employeePhoneNumber;
	}

	public void setEmployeePhoneNumber(String employeePhoneNumber) {
		this.employeePhoneNumber = employeePhoneNumber;
	}
	
}
