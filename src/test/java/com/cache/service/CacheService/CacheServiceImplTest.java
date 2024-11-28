package com.cache.service.CacheService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cache.service.Dao.Employees;
import com.cache.service.Exception.CacheServiceExceptionHandler;
import com.cache.service.Service.impl.EmployeesServiceImpl;
import com.cache.service.config.MyConfigValues;

class CacheServiceImplTest {

	@Mock
	private MyConfigValues myConfigValues;

	@Mock
	private EmployeesServiceImpl employeesServiceImpl;

	@Mock
	private CacheServiceExceptionHandler cacheServiceExceptionHandler;

	@InjectMocks
	private CacheServiceImpl cacheServiceImpl;

	private Employees employee;
	private Employees employee1;
	private Employees employee2;
	private Employees employee3;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		employee = new Employees("1", "Eshita", "Delhi", "1221");
		Map<String, Employees> internalCache = new HashMap<>();
		when(myConfigValues.getInternalCache()).thenReturn(internalCache);

		when(myConfigValues.getMaxCacheSize()).thenReturn(2);

		myConfigValues.setMaxCacheSize(2); // Set max size for easy testing

		employee1 = new Employees("1", "Employee One", "Address 1", "1234567890");
		employee2 = new Employees("2", "Employee Two", "Address 2", "2345678901");
		employee3 = new Employees("3", "Employee Three", "Address 3", "3456789012");
	}

	@Test
	void testAddEntityWhenCacheIsNotFull() {
		cacheServiceImpl.add(employee);

		verify(employeesServiceImpl, times(1)).add(employee);

		assertTrue(myConfigValues.getInternalCache().containsKey(employee.getEmployeeID()));
	}

	@Test
	void testAddEntityWhenCacheIsFull() {
		Map<String, Employees> fullCache = new HashMap<>();

		fullCache.put("1", employee);
		when(myConfigValues.getInternalCache()).thenReturn(fullCache);

		cacheServiceImpl.add(employee);
		assertTrue(myConfigValues.getInternalCache().containsKey(employee.getEmployeeID()));
	}

	@Test
	void testRemoveEntity() {
		cacheServiceImpl.remove(employee.getEmployeeID());
		verify(employeesServiceImpl, times(1)).remove(employee.getEmployeeID());
		assertFalse(myConfigValues.getInternalCache().containsKey(employee.getEmployeeID()));
	}

	@Test
	void testGetEntityWhenPresentInCache() {
		myConfigValues.getInternalCache().put(employee.getEmployeeID(), employee);
		Employees result = cacheServiceImpl.get(employee.getEmployeeID());
		assertNotNull(result);
		assertEquals(employee.getEmployeeID(), result.getEmployeeID());
	}

	@Test
	void testGetEntityWhenNotInCache() {
		when(employeesServiceImpl.getEmployees(employee.getEmployeeID())).thenReturn(employee);

		Employees result = cacheServiceImpl.get(employee.getEmployeeID());
		assertNotNull(result);
		assertEquals(employee.getEmployeeID(), result.getEmployeeID());
		assertTrue(myConfigValues.getInternalCache().containsKey(employee.getEmployeeID()));
	}

	@Test
	void testClearCache() {
		cacheServiceImpl.clear();
		assertTrue(myConfigValues.getInternalCache().isEmpty());
	}

	@Test
	public void testEvictIfNeededNoEviction() {
		myConfigValues.getInternalCache().put(employee1.getEmployeeID(), employee1);
		myConfigValues.getInternalCache().put(employee2.getEmployeeID(), employee2);
		cacheServiceImpl.add(employee1);
		cacheServiceImpl.add(employee2);
		assertFalse(myConfigValues.getInternalCache().containsKey(employee1.getEmployeeID()));
		assertTrue(myConfigValues.getInternalCache().containsKey(employee2.getEmployeeID()));
	}

	@Test
	public void testEvictIfNeededEvictionHappens() {
		myConfigValues.getInternalCache().put(employee1.getEmployeeID(), employee1);
		myConfigValues.getInternalCache().put(employee2.getEmployeeID(), employee2);
		myConfigValues.getInternalCache().put(employee3.getEmployeeID(), employee3);
		cacheServiceImpl.add(employee1);
		cacheServiceImpl.add(employee2);
		cacheServiceImpl.add(employee);
		assertTrue(myConfigValues.getInternalCache().containsKey(employee1.getEmployeeID()));
		assertFalse(myConfigValues.getInternalCache().containsKey(employee2.getEmployeeID()));
		assertTrue(myConfigValues.getInternalCache().containsKey(employee3.getEmployeeID()));
	}
	
	@Test
	void testGetEmployee_InvalidEmployeeId() {
	    String invalidEmployeeId = "invalid123";
	    when(myConfigValues.getInternalCache()).thenReturn(new HashMap<>());
	    when(employeesServiceImpl.getEmployees(invalidEmployeeId)).thenReturn(null);
	    Employees result = cacheServiceImpl.get(invalidEmployeeId);
	    assertNull(result, "The employee should not be found and should return null.");
	}
	
	@Test
	void testifEmployeeISNull() {
	    Employees employee4 = new Employees();
	    when(myConfigValues.getInternalCache()).thenReturn(new HashMap<>());
	    cacheServiceImpl.add(employee4);
	}
}
