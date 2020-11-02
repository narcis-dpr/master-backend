package com.howtodojava.rest.dao;

import com.howtodojava.rest.models.EmployeePro;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.Map;
import java.util.UUID;

public class EmployeeProImpl extends AbstractDAO<EmployeePro> implements EmployeeProDao {
    public EmployeeProImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    private Map<Integer, EmployeePro> store;


    @Override
    public EmployeePro findById(String id) {
        return super.get(id);
    }

    @Override
    public void save(EmployeePro employeePro) {
        String uniqueID = UUID.randomUUID().toString();
        if (employeePro.getOwner() == null)
            employeePro.setOwner((uniqueID));
        super.persist(employeePro);

    }

    @Override
    public void delete(EmployeePro employeePro) {
        super.currentSession().delete(employeePro);

    }

    private int generateNewId() {
        int currentMax = store.
                keySet().
                stream().
                max(Integer::compareTo).
                orElse(0);
        return currentMax + 1;
    }
}
