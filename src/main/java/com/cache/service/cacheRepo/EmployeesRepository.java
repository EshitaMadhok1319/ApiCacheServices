package com.cache.service.cacheRepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cache.service.Dao.Employees;

@Repository
public interface EmployeesRepository extends JpaRepository<Employees, String>{

}
