package com.howtodojava.rest.dao;

import com.howtodojava.rest.models.Employee;
import com.howtodojava.rest.models.Food;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class InMemoryEmployeeDao implements EmployeeDao {

    private Map<Integer, Employee> store;


    @Override
    public List<Employee> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Employee findById(String owner) {
        return store.get(owner);
    }

    @Override
    public void save(Employee employee) {
//        if (employee.getOwner() == null)
//            employee.setOwner(generateNewId());
//        store.put(employee.getOwner(), employee);

        String uniqueID = UUID.randomUUID().toString();
        if (employee.getOwner() == null)
            employee.setOwner((uniqueID));

    }


    @Override
    public void delete(Employee employee) {

    }

    @Override
    public void WriteTasvie(Employee employee) {
        System.out.println("la al lal al al al al");
       // FileUtils.writeFile(new File("filename"), "textToWrite");


 //           writer.println(employee.getOwner());
//            writer.println(employee.getFirstName());
//            writer.println(employee.getLastName());
//            writer.println(employee.getPhoneNumber());
//            writer.println(employee.getUserName());
//            writer.println(employee.getShaba());
//            writer.println(employee.getCredit());


    }

    public List<Food> findNeighbours(float lat, float lng) {
        throw new RuntimeException("WAT");
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
