package edu.escuelaing.niubank.repository;

import com.google.gson.JsonObject;
import edu.escuelaing.niubank.controller.auth.LoginDto;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ControllerDb implements ServicesDB {

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

    @Override
    public JSONObject verMonto(String cedula) {
        JSONObject res = new JSONObject();
        String select = "SELECT nombre, fondos FROM usuario where usuario.cedula ='" + cedula + "';";
        try {
            ResultSet resultSet = connection.prepareStatement(select).executeQuery();
            resultSet.next();
            res.put("nombre", resultSet.getString("nombre"));
            res.put("fondos", resultSet.getString("fondos"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public JSONObject verTransferencias() {
        JSONObject res = new JSONObject();
        int key = 0;
        String select = "SELECT * FROM transaccion ;";
        try {
            ResultSet resultSet = connection.prepareStatement(select).executeQuery();
            while (resultSet.next()) {
                JSONObject res2 = new JSONObject();
                key += 1;
                res2.put("numtransaccion ", resultSet.getString("numtransaccion"));
                res2.put("cedulaemisor", resultSet.getString("cedulaemisor"));
                res2.put("cedulareceptor", resultSet.getString("cedulareceptor"));
                res2.put("cantidad", resultSet.getInt("cantidad"));
                res.put(String.valueOf(key), res2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }
}
