package com.howtodojava.rest.controller;


import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.howtodojava.rest.dao.FoodDao;
import com.howtodojava.rest.models.Food;
import com.howtodojava.rest.models.Point;
import io.dropwizard.hibernate.UnitOfWork;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.stream.Collectors;

@Path("/food")
@Produces(MediaType.APPLICATION_JSON)
public class FoodRestController {

    private final FoodDao dao;
    private static final int MAX_LABELS = 3;
    public  List<EntityAnnotation> a;
    public  boolean r = false;

    public FoodRestController(FoodDao dao) {
        this.dao = dao;
    }

    @UnitOfWork
    @GET
    public List<Object> getFood(@QueryParam("lat") float lat, @QueryParam("lng") float lng) {
        int numbers = dao.findNeighbours(new Point(lat,lng)).size();
//        dao.findNeighbours(new Point(lat,lng));
        //dao.findNeighbours(new Point(lat,lng)).size();
        System.out.println("the Total number="+ numbers);
       // for (int i=0; i<numbers; i++){
            List<Object> foo = dao.findNeighbours(new Point(lat,lng));
        for (Object o : foo) {
            System.out.println(o);
        }
        //  System.out.println(foo.get(i));

       // }
        return (List<Object>) dao.findNeighbours(new Point(lat,lng)).stream().collect(Collectors.toList());
    }

    @UnitOfWork
    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") String id) {
        Food food = this.dao.findById(id);
        if (food != null)
            return Response.ok(food).build();
        else
            return Response.status(Response.Status.NOT_FOUND).build();
    }

    @UnitOfWork
    @POST
    @Path("/postFood")
    public Response create(Food food) throws IOException, GeneralSecurityException {
       // System.out.println(1);
        //System.out.println(food.getImageBase64());
        if (food.getId() != null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }


        CloudVisionUtils cloudVisionUtils = new CloudVisionUtils(CloudVisionUtils.getVisionService());
        a = cloudVisionUtils.labelImage(food.getImageBase64(),MAX_LABELS);
        Float b;



        for (int i = 0;i <MAX_LABELS;i++){
            String c = a.get(i).getDescription();
            System.out.println("$$$$$$$$$$$$$$$$$$ the c is : " + c);
            if (c.equals("Food")) {
                System.out.println("  vision is :   " + c);
                b = a.get(i).getScore();

                if (b >= 0.90){
                    System.out.println("***********" + "    b:= " +b + "    Food    " + c);
                    r = true;
                }


                food.saveImage();
                dao.save(food);
                return Response.ok(food.serialize()).build();
           }
        }
        if (!r)
            return Response.status(800).build();

        return Response.status(Response.Status.BAD_GATEWAY).build();
    }


    @UnitOfWork
    @PUT
    @Path("/update/{id}")
    public Response update(@NotNull @PathParam("id") String id, @QueryParam("porsNumber") Integer porsNumber ) {
        Food found = dao.findById(id);
        if (found==null)
            return Response.status(Response.Status.NOT_FOUND).build();

      found.setPorsNumber(porsNumber);
      return Response.ok(found.serialize()).build();

    }

    @UnitOfWork
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") String id) {
        Food found = dao.findById(id);
        if (found == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        dao.delete(found);
        return Response.ok().build();
    }
}
