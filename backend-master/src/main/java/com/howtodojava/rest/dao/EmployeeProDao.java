package com.howtodojava.rest.dao;

import com.howtodojava.rest.models.EmployeePro;

public interface EmployeeProDao {

    EmployeePro findById(String id);
    void save(EmployeePro employeePro);

    void delete(EmployeePro employeePro);
}
