package edu.escuelaing.niubank.repository;

import com.google.gson.JsonObject;
import edu.escuelaing.niubank.controller.auth.LoginDto;
<<<<<<< HEAD
import edu.escuelaing.niubank.data.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
=======
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
>>>>>>> aca866f8aa96169bb859a0876652b187ab3c7a13


public class ControllerDb implements ServicesDB {

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


    @Override
    public JSONObject solicitarSobregiro(String cedula, String monto) {
        JSONObject res = new JSONObject();
        UUID uuid = UUID.randomUUID();
        String insert = "INSERT INTO autorizacion values (?,?,?);";
        try {
            PreparedStatement stmt = connection.prepareStatement(insert);
            stmt.setString(1, uuid.toString());
            stmt.setString(2, cedula);
            stmt.setInt(3, Integer.parseInt(monto));
            System.out.println(stmt.executeUpdate());
            return res.put("Sobregiro", true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res.put("Sobregiro", false);

    }

}
