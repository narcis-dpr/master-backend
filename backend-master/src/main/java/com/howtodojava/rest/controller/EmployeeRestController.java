package com.howtodojava.rest.controller;

import com.howtodojava.rest.App;
import com.howtodojava.rest.dao.EmployeeDao;
import com.howtodojava.rest.models.Employee;
import io.dropwizard.hibernate.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.util.List;

@Path("/employees")
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeRestController {
    private final Logger LOGGER = LoggerFactory.getLogger(App.class);
    private final EmployeeDao dao;
    @Context
    private UriInfo context;

    public EmployeeRestController(EmployeeDao dao) {
        this.dao = dao;
    }

    @UnitOfWork
    @GET
    public List<Employee> getEmployees() {
        return dao.findAll();
    }

    @UnitOfWork
    @GET
    @Path("/{owner}")
    public Response findById(@PathParam("owner") String owner) {

        Employee employee = this.dao.findById(owner);
        if (employee != null)
            return Response.ok(employee).build();
        else
            return Response.status(Status.NOT_FOUND).build();
    }

    // Tasvie hesab
    @UnitOfWork
    @GET
    @Path("tasvie/{owner}")
    public Response tasvie(@PathParam("owner") String owner) {
      Employee found = dao.findById(owner);
      if (found != null){
          dao.WriteTasvie(found);
          return Response.ok(found).build();
      }


      return Response.status(Status.NOT_FOUND).build();

    }

    @UnitOfWork
    @POST
    @Path("/posts")
    public Response create(@NotNull @Valid Employee employee) throws IOException {
        if (employee.getOwner() != null)
            // ids of new records are generated by DB automatically
            return Response.status(Status.BAD_REQUEST).build();
//        employee.saveImage();
        dao.save(employee);
        return Response.ok(employee.serialize()).build();
    }

    @UnitOfWork
    @PUT
    @Path("update/{owner}")
    public Response update(@NotNull @PathParam("owner") String owner, @NotNull @Valid Employee updateEmployee) {
        Employee found = dao.findById(owner);
        if (found==null)
            return Response.status(Response.Status.NOT_FOUND).build();

        found.setFirstName(updateEmployee.getFirstName());
        found.setLastName(updateEmployee.getLastName());
        found.setShaba(updateEmployee.getShaba());
        found.setUserName(updateEmployee.getUserName());
        return Response.ok(found).build();
    }

    @UnitOfWork
    @PUT
    @Path("update/credit/{owner}")
    public Response AfterSelling(@NotNull @PathParam("owner") String owner, @QueryParam("credit") Integer credit,
                                 @QueryParam("stars") double stars) {
       Employee found = dao.findById(owner);
        if (found==null)
            return Response.status(Response.Status.NOT_FOUND).build();

        Integer cCredit;
        if (found.getCredit()!=null)
         cCredit = found.getCredit() + credit;
        else
            cCredit = credit;

        found.setCredit(cCredit);
        found.setStars(stars);
        return Response.ok(found.serialize()).build();
    }


    @UnitOfWork
    @PUT
    @Path("update/label/{owner}")
    public Response lebels(@NotNull @PathParam("owner") String owner, @QueryParam("label") Integer label) {
        Employee found = dao.findById(owner);
        if (found==null)
            return Response.status(Response.Status.NOT_FOUND).build();
       found.setLable(label);
        return Response.ok(found.serialize()).build();
    }


}

