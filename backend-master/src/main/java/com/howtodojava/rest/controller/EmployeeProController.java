package com.howtodojava.rest.controller;

import com.howtodojava.rest.dao.EmployeeProDao;
import com.howtodojava.rest.models.EmployeePro;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/employeePro")
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeProController {
    private final EmployeeProDao proDao;

    public EmployeeProController(EmployeeProDao proDao) {
        this.proDao = proDao;
    }

    @UnitOfWork
    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") String id){
        EmployeePro employeePro = this.proDao.findById(id);
        if (employeePro!=null){
            return Response.ok(employeePro.getAccountStage()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @UnitOfWork
    @POST
    @Path("/postPro")
    public Response create(EmployeePro employeePro) throws IOException{
        if (employeePro.getId() != null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        employeePro.saveImage();
   //     System.out.println("    image is ok ");
        employeePro.saveSalamatImage();
        employeePro.saveCardImage();
//        System.out.println("    salamat is ok");
        proDao.save(employeePro);
        return Response.ok(employeePro.serialize()).build();
    }



    @UnitOfWork
    @PUT
    @Path("update/{id}")
    public Response update(@PathParam("id") String id, int accountStg) {
        EmployeePro found = proDao.findById(id);
        if (found == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        found.setAccountStage(accountStg);
        return Response.ok(found.getAccountStage()).build();

//        dao.save(employee);
//        return Response.ok(employee).build();
    }

    @UnitOfWork
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") String id) {
        EmployeePro found = proDao.findById(id);
        if (found == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        proDao.delete(found);
        return Response.ok().build();
    }



}
