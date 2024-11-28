package com.cache.service.Service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cache.service.Dao.Employees;
import com.cache.service.cacheRepo.EmployeesRepository;

class EmployeesServiceImplTest {

    @Mock
    private EmployeesRepository employeesRepository;

    @InjectMocks
    private EmployeesServiceImpl employeesServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employeesServiceImpl = new EmployeesServiceImpl(employeesRepository);
    }

    @Test
    void testAdd() {
        Employees employee = new Employees("1", "Eshita", "Delhi", "1221");

        when(employeesRepository.save(employee)).thenReturn(employee);

        String result = employeesServiceImpl.add(employee);
        assertEquals("Successfully saved", result);
    }

    @Test
    void testRemove() {
        doNothing().when(employeesRepository).deleteById("1");

        String result = employeesServiceImpl.remove("1");
        assertEquals("Successfully removed", result);
    }

    @Test
    void testGetEmployees() {
        Employees employee = new Employees("1", "Eshita", "Delhi", "1221");

        when(employeesRepository.findById("1")).thenReturn(java.util.Optional.of(employee));

        Employees result = employeesServiceImpl.getEmployees("1");
        assertNotNull(result);
        assertEquals("1", result.getEmployeeID());
    }

    @Test
    void testRemoveAll() {
        doNothing().when(employeesRepository).deleteAll();

        employeesServiceImpl.removeAll();
        verify(employeesRepository, times(1)).deleteAll();
    }
}
