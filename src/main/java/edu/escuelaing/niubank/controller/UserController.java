package edu.escuelaing.niubank.controller;

import com.google.gson.Gson;
import edu.escuelaing.niubank.controller.auth.LoginDto;
import edu.escuelaing.niubank.controller.auth.TokenDto;
import edu.escuelaing.niubank.services.UserServicesImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/v2/Banco")
public class UserController {
    public Gson gson = new Gson();

    public UserServicesImpl userServices = new UserServicesImpl();


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String hello() {
        return gson.toJson("Hello perez puto!!!");
    }

    @POST
    @Path("/Login/user")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public TokenDto login(LoginDto loginDto){
        return userServices.Login(loginDto);
    }


    @GET
    @Path("/verMonto/{cedula}")
    public String verMonto(@PathParam("cedula") String cedula){
        return gson.toJson(userServices.verMonto(cedula));
    }




}
