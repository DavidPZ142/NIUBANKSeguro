package edu.escuelaing.niubank.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.escuelaing.niubank.controller.auth.LoginDto;
import edu.escuelaing.niubank.controller.auth.TokenDto;

import edu.escuelaing.niubank.data.User;
import edu.escuelaing.niubank.services.UserServicesImpl;

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
    public TokenDto login(LoginDto loginDto) {
        return userServices.Login(loginDto);
    }


    @GET
    @Path("/infoUser")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public User infoUser(@HeaderParam("authorization") String token) {
        return userServices.loadInfoUser(token);
    }


    @GET
    @Path("/verMonto/{cedula}")
    public String verMonto(@PathParam("cedula") String cedula) throws Exception {
        return gson.toJson(userServices.verMonto(cedula));
    }

    @GET
    @Path("/verTransferencia")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String verTransferencia() {
        return gson.toJson(userServices.verTransferencias());
    }

    @GET
    @Path("/solicitar/{cedula}/{monto}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String solicitarSobregiro(@PathParam("cedula") String cedula, @PathParam("monto") String monto) throws Exception {
        return gson.toJson(userServices.solicitarSobregiro(cedula, monto));
    }

    @GET
    @Path("/registrar/User/{cedula}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String registrarUser(@HeaderParam("authorization") String token, @PathParam("cedula") String cedula) {
        System.out.println(cedula);
        return gson.toJson(userServices.registrarUser(token, cedula));
    }

    @PUT
    @Path("/crear/User/{cedula}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String crearUser(@PathParam("cedula") String cedula, User user) {
        System.out.println(user.getIdentificador());
        return gson.toJson(userServices.crearUser(cedula, user));
    }

}
