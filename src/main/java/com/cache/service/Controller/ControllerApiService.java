package com.cache.service.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cache.service.CacheService.CacheServiceImpl;
import com.cache.service.Dao.Employees;
import com.cache.service.Exception.CacheServiceExceptionHandler;
import com.cache.service.Service.EmployeesService;

@RestController
@RequestMapping("/api/employee")
public class ControllerApiService {

	private Logger LOGGER = LoggerFactory.getLogger(ControllerApiService.class);
	
	@Autowired
	EmployeesService employeesService;

	@Autowired
	CacheServiceImpl cachingService;

	public ControllerApiService(CacheServiceImpl cachingService) {
		this.cachingService = cachingService;
	}

	@PostMapping(value = "/add")
	public String addEntity(@RequestBody Employees e) {
		try {
			cachingService.add(e);
			return "Entity added successfully!";
		} catch (Exception ex) {
			CacheServiceExceptionHandler.handleException("addEntity", ex);
			return "Error adding entity: " + ex.getMessage();
		}
	}

	@DeleteMapping("/remove/{employeeId}")
	public void removeEntity(@PathVariable("employeeId") String employeeId) {
		try {
			cachingService.remove(employeeId);
			LOGGER.info("Remove " + employeeId);
		} catch (Exception ex) {
			CacheServiceExceptionHandler.handleException("removeEntity", ex);
		}
	}

	@DeleteMapping("/removeAll")
	public void removeAllEntities() {
		try {
			cachingService.removeAll();
			LOGGER.info("deleted ");
		} catch (Exception ex) {
			CacheServiceExceptionHandler.handleException("removeAllEntities", ex);
		}
	}

	@GetMapping("/get/{employeeId}")
	public Employees getEntity(@PathVariable("employeeId") String employeeId) {
		try {
			cachingService.get(employeeId);
			return employeesService.getEmployees(employeeId);
		} catch (Exception ex) {
			CacheServiceExceptionHandler.handleException("getEntity", ex);
			return null;
		}

	}

	@PostMapping("/clear")
	public void clearCache() {
		try {
			cachingService.clear();
		} catch (Exception ex) {
			CacheServiceExceptionHandler.handleException("clearCache", ex);
		}
	}

}
