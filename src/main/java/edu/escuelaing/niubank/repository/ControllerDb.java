package edu.escuelaing.niubank.repository;

import edu.escuelaing.niubank.controller.auth.LoginDto;

import java.sql.Connection;


public class ControllerDb implements ServicesDB{

    ConnectionDb connectionDb;
    Connection connection;

    public ControllerDb() {

        this.connectionDb = new ConnectionDb();
        connection = connectionDb.getConnect();
    }


    @Override
    public boolean verificarUser(LoginDto loginDto) {
        System.out.println("si estoy funcionando!!!!");
        return false;
    }
}
