package com.cache.service.Service;

import com.cache.service.Dao.Employees;

public interface EmployeesService {

	public String add(Employees employee);
	public String updateEmployees(Employees e);
	public String remove(String employeeId);
	public Employees getEmployees(String employee);
    public void removeAll();

}
