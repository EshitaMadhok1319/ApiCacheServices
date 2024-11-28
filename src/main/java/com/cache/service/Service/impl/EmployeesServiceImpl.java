package com.cache.service.Service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cache.service.Dao.Employees;
import com.cache.service.Exception.CacheServiceExceptionHandler;
import com.cache.service.Service.EmployeesService;
import com.cache.service.cacheRepo.EmployeesRepository;

@Service
public class EmployeesServiceImpl implements EmployeesService {

	private Logger LOGGER = LoggerFactory.getLogger(EmployeesServiceImpl.class);

	@Autowired
	EmployeesRepository entityRepository;

	public EmployeesServiceImpl(EmployeesRepository entityRepository) {
		this.entityRepository = entityRepository;
	}

	@Override
	public String add(Employees e) {
		try {
			LOGGER.info("Saving data to DB");
			entityRepository.save(e);
			return "Successfully saved";
		} catch (Exception ex) {
			CacheServiceExceptionHandler.handleException("add", ex);
			return "Error saving entity: " + ex.getMessage();
		}
	}

	@Override
	public String updateEmployees(Employees e) {
		try {
			entityRepository.save(e);
			return "Successfully updated";
		} catch (Exception ex) {
			CacheServiceExceptionHandler.handleException("updateEmployees", ex);
			return "Error updating entity: " + ex.getMessage();
		}
	}

	@Override
	public String remove(String id) {
		try {
			entityRepository.deleteById(id);
			return "Successfully removed";
		} catch (Exception ex) {
			CacheServiceExceptionHandler.handleException("remove", ex);
			return "Error removing entity: " + ex.getMessage();
		}
	}

	@Override
	public Employees getEmployees(String employeeId) {
		try {
			return entityRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("Employee not found"));
		} catch (Exception ex) {
			CacheServiceExceptionHandler.handleException("getEmployees", ex);
			return null;
		}
	}

	@Override
	public void removeAll() {
		try {
			entityRepository.deleteAll();
		} catch (Exception ex) {
			CacheServiceExceptionHandler.handleException("removeAll", ex);
		}

	}

}
