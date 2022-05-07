package edu.escuelaing.niubank.repository;

import java.sql.Connection;

public class ControllerDb {

    ConnectionDb connectionDb;
    Connection connection;

    public ControllerDb() {

        this.connectionDb = new ConnectionDb();
        connection = connectionDb.getConnect();
    }



}
