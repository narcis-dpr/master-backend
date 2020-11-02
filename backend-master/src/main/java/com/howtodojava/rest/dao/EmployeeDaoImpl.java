package com.howtodojava.rest.dao;

import com.howtodojava.rest.models.Employee;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class EmployeeDaoImpl extends AbstractDAO<Employee> implements EmployeeDao {

    public EmployeeDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<Employee> findAll() {
        return super.list(this.criteria());
    }

    @Override
    public Employee findById(String owner) {
        return super.get(owner);
    }

    @Override
    public void save(Employee employee) {
        super.persist(employee);
    }

    @Override
    public void delete(Employee employee) {
        super.currentSession().delete(employee);
    }

    @Override
    public void WriteTasvie(Employee employee) {
        System.out.println("la al lal al al al al");
        try {

            String content = "This is the content to write into file";

            File dir = new File("/usr/local/var/run/"+employee.getOwner() );

            if (!dir.exists()){
                dir.mkdir();
            }
            File file = new File(dir +"/"+employee.getOwner()+ ".txt");

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
           // bw.write(content);
            bw.write(employee.getFirstName());
            bw.newLine();
            bw.write(employee.getLastName());
            bw.newLine();
            bw.write(employee.getOwner());
            bw.newLine();
            bw.write(employee.getCredit());
            bw.newLine();
            bw.write(employee.getShaba());
            bw.newLine();
            bw.write(employee.getUserName());
            bw.newLine();
            bw.write(employee.getPhoneNumber());
            bw.newLine();
            bw.write(employee.getSellRecord());
            bw.newLine();

            bw.close();

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        }
       super.persist(employee);

    }
}
