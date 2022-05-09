package edu.escuelaing.niubank.controller;

import com.google.gson.Gson;
import edu.escuelaing.niubank.services.UserServices;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/perezputo")
public class UserController {
    public Gson gson = new Gson();

    @Inject
    public UserServices userServices;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String hello() {
        return gson.toJson("Hello perez puto!!!");
    }

    @POST
    public String login(){
        return "hello";
    }

}
