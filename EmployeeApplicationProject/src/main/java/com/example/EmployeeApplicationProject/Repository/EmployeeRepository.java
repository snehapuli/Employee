package com.example.EmployeeApplicationProject.Repository;

import com.example.EmployeeApplicationProject.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long>{}