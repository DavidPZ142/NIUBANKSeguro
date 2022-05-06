package edu.escuelaing.niubank.controller;

import com.google.gson.Gson;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/perezputo")
public class test {
    public Gson gson = new Gson();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String hello() {
        return gson.toJson("Hello perez puto!!!");
    }
}
