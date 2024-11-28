package com.cache.service.CacheService;

import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cache.service.Dao.Employees;
import com.cache.service.Exception.CacheServiceExceptionHandler;
import com.cache.service.Service.impl.EmployeesServiceImpl;
import com.cache.service.config.MyConfigValues;

@Service
public class CacheServiceImpl {

	@Autowired
	MyConfigValues myConfigValues;
	@Autowired
	EmployeesServiceImpl employeesServiceImpl;
	@Autowired
	CacheServiceExceptionHandler cacheServiceExceptionHandler;

	private Logger LOGGER = LoggerFactory.getLogger(CacheServiceImpl.class);

	public CacheServiceImpl() {
	}

	public void add(Employees e) {
		try {
			LOGGER.info(" Internal Cache Size: {} " + myConfigValues.getInternalCache().size());
			LOGGER.info(" maxCacheSize: " + myConfigValues.getMaxCacheSize());
			if (myConfigValues.getInternalCache().size() >= myConfigValues.getMaxCacheSize()) {
				evictCache();
			}
			myConfigValues.getInternalCache().put(e.getEmployeeID(), e);
			LOGGER.info("Added to cache: " + e.getEmployeeID());
			employeesServiceImpl.add(e);
			LOGGER.info("Added to DB " + e.getEmployeeID());
		} catch (Exception ex) {
			CacheServiceExceptionHandler.handleException("add", ex);
		}
	}

	public void remove(String employeeId) {
		try {
			LOGGER.info("Remove from cache: " + employeeId);
			myConfigValues.getInternalCache().remove(employeeId);

			LOGGER.info("Remove from DB: " + employeeId);
			employeesServiceImpl.remove(employeeId);

			LOGGER.info("Entity removed from cache and database: ", employeeId);
		} catch (Exception ex) {
			CacheServiceExceptionHandler.handleException("Error removing entity from cache ", ex);

		}
	}

	public void removeAll() {
		try {
			LOGGER.info("RemoveAll from cache: ");
			myConfigValues.getInternalCache().clear();
			LOGGER.info("RemoveAll from DB: ");
			employeesServiceImpl.removeAll();
		} catch (Exception ex) {
			CacheServiceExceptionHandler.handleException("removeAll from Cache and DB: ", ex);
		}
	}

	public Employees get(String employeeId) {
		try {
			LOGGER.info(" Check the internal cache first");
			if (myConfigValues.getInternalCache().containsKey(employeeId)) {
				LOGGER.info("Entity fetched from cache: {}", employeeId);
				return myConfigValues.getInternalCache().get(employeeId);
			}

			LOGGER.info(" Checking in DB");
			Employees fetchedEntity = employeesServiceImpl.getEmployees(employeeId);
			if (fetchedEntity != null) {
				if (myConfigValues.getInternalCache().size() >= myConfigValues.getMaxCacheSize()) {
					LOGGER.info("evicted Cache ");
					evictCache();
				}
				LOGGER.info(" Caching the data " + fetchedEntity.getEmployeeID());
				myConfigValues.getInternalCache().put(employeeId, fetchedEntity); // Cache it
				return fetchedEntity;
			}
			return null;
		} catch (Exception ex) {
			CacheServiceExceptionHandler.handleException("get", ex);
			return null;
		}
	}

	public void clear() {
		try {
			myConfigValues.getInternalCache().clear();
			LOGGER.info("Cache cleared, database unaffected.");
		} catch (Exception ex) {
			CacheServiceExceptionHandler.handleException("clear", ex);
		}
	}

	public void evictCache() {
		try {
			LOGGER.info("Evicting the value ");
			Iterator<Map.Entry<String, Employees>> iterator = myConfigValues.getInternalCache().entrySet().iterator();
			if (iterator.hasNext()) {
				Map.Entry<String, Employees> entry = iterator.next();
				LOGGER.info("Value evicted" + entry.getKey());
				myConfigValues.getInternalCache().remove(entry.getKey());
			}
		} catch (Exception ex) {
			CacheServiceExceptionHandler.handleException("Error during eviction", ex);
		}
	}
}
