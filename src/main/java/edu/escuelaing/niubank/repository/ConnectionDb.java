package edu.escuelaing.niubank.repository;

import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDb {


    private String url = "jdbc:postgresql://ec2-54-83-152-251.compute-1.amazonaws.com:5432/da1a3qsohc9ouu?sslmode=require";
    private String user = "qtkmbyupnclunf";
    private String password = "f12f226486335375b6843be0978330f97d3a1f2ebf8870657bbe7bb5433ed7a7";
    private Connection connect;

    public ConnectionDb() {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            while (connect == null){
                connect = DriverManager.getConnection(url,user,password);
            }

            //connect.setAutoCommit(false);

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Connection getConnect() {
        return connect;
    }
}
