package com.howtodojava.rest.dao;

import com.howtodojava.rest.models.Employee;

import java.util.List;

public interface EmployeeDao {

    List<Employee> findAll();

    Employee findById(String owner);

    void save(Employee employee);

    void delete(Employee employee);

    void WriteTasvie(Employee employee);

}
