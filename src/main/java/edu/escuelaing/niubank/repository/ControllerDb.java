package edu.escuelaing.niubank.repository;

import edu.escuelaing.niubank.controller.auth.LoginDto;
import edu.escuelaing.niubank.data.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ControllerDb implements ServicesDB{

    ConnectionDb connectionDb;
    Connection connection;

    public ControllerDb() {

        this.connectionDb = new ConnectionDb();
        connection = connectionDb.getConnect();
    }


    @Override
    public boolean verificarUser(LoginDto loginDto) {

        String select = "select * from usuario where usuario.cedula = '1' and usuario.contrasena = 'ADMIN'";
        try {
            ResultSet resultSet = connection.prepareStatement(select).executeQuery();
            if(resultSet.next()){
                //User user = new User(resultSet.getString("cedula"), resultSet.getString("apellido"), resultSet.getString("nombre"), resultSet.getString("correo"),
                        //resultSet.getString("password"), resultSet.getString("rol"));
                connection.close();
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
