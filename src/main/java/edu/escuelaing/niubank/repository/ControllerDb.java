package edu.escuelaing.niubank.repository;

import com.google.gson.JsonObject;
import edu.escuelaing.niubank.controller.auth.LoginDto;

import edu.escuelaing.niubank.data.User;
import edu.escuelaing.niubank.security.Tokenizer;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;


public class ControllerDb implements ServicesDB {

    ConnectionDb connectionDb;
    Connection connection;
    private String nola = "hola";

    public ControllerDb() {

        this.connectionDb = new ConnectionDb();
        connection = connectionDb.getConnect();
    }


    @Override
    public boolean verificarUser(LoginDto loginDto) {
        Tokenizer tokenizer = new Tokenizer();
        String select = "select * from usuario where usuario.cedula = '"+loginDto.getEmail()+"' and usuario.contrasena ='"+tokenizer.encriptar(loginDto.getPassword())+"';";
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

    public User findUser(String identificador) {
        String select = "SELECT * FROM usuario where usuario.cedula = '"+identificador+"';";
        try {
            ResultSet resultSet = connection.prepareStatement(select).executeQuery();
            if (resultSet.next()){
                User user = new User(resultSet.getString("cedula"), resultSet.getString("apellido"), resultSet.getString("nombre"), resultSet.getString("correo"),
                resultSet.getString("contrasena"), resultSet.getString("rol"));
                System.out.println(user.getNombre());
                return user;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void registrarUser(String cedula) {
        String insert = "insert into usuario values ( ?,'','','','',0,'',true)";
        try {
            PreparedStatement stmt = connection.prepareStatement(insert);
            stmt.setString(1, cedula);
            stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean buscarUser(String cedula) {
        String select = "SELECT rol FROM usuario WHERE usuario.cedula = '"+cedula+"';";
        try {

            ResultSet resultSet = connection.prepareStatement(select).executeQuery();
            if(resultSet.next()){
                return resultSet.getString("rol").equals("ADMIN");
            }return false;

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void crearUser(String cedula, User user) {
        Tokenizer tokenizer = new Tokenizer();
        String update = "update usuario set nombre = ?, apellido = ?, correo = ?, contrasena = ?, rol = 'USER' where cedula = ?";

        try {

            PreparedStatement stmt = connection.prepareStatement(update);
            stmt.setString(1, user.getNombre());
            stmt.setString(2, user.getApellido());
            stmt.setString(3, user.getCorreo());
            stmt.setString(4, tokenizer.encriptar(user.getPassword()));
            stmt.setString(5, cedula);
            stmt.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    public String verMonto(String cedula) throws Exception {
        System.out.println("sdasdsadasdasd");
        boolean bool = evitarSql(cedula);
        if (bool){
            throw new Exception("No se permiten caracteres especiales en este espacio :v");
        }
        String select = "SELECT fondos FROM usuario where usuario.cedula ='" + cedula + "';";
        try {
            ResultSet resultSet = connection.prepareStatement(select).executeQuery();
            if(resultSet.next()){
                System.out.println(String.valueOf(resultSet.getInt("fondos"))+ "sdasdsadasdasd");
                return "0";
            }return "0";

        } catch (SQLException e) {
            e.printStackTrace();
            return "0";
        }
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
    public JSONObject solicitarSobregiro(String cedula, String monto) throws Exception {
        boolean bool = evitarSql(cedula);
        boolean bool1 = evitarSql(monto);
        if (bool || bool1){
            throw new Exception("No se permiten caracteres especiales en este espacio ");
        }

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

    public JSONObject modificarMonto(String cedula, String cantidad) throws Exception {
        boolean bool = evitarSql(cedula);
        boolean bool1 = evitarSql(cantidad);
        if (bool || bool1){
            throw new Exception("No se permiten caracteres especiales en este espacio ");
        }

        JSONObject res = new JSONObject();
        String update = "UPDATE usuario SET fondos = ? where cedula = ? ;";

        try {
            PreparedStatement stmt = connection.prepareStatement(update);
            stmt.setInt(1, Integer.parseInt(cantidad));
            stmt.setString(2, cedula);
            stmt.executeUpdate();
            return res.put("modificacion", true);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res.put("modificacion", false);
    }

    @Override
    public JSONObject transferencia(String ccOrigen, String ccDestino, String monto) throws Exception {
        if(0 > Integer.parseInt(monto)){
            throw new Exception("No puede transferir numeros negativos");
        }
        UUID uuid = UUID.randomUUID();
        JSONObject res = new JSONObject();
        String ccOrigenSelect = "UPDATE usuario SET fondos = fondos + ? where cedula = ?";
        String ccDestinoSelect = "UPDATE usuario SET fondos = fondos - ? where cedula = ?";
        String transaccion = "INSERT INTO transaccion values (?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(ccOrigenSelect);
            stmt.setInt(1, Integer.parseInt(monto));
            stmt.setString(2, ccDestino);
            PreparedStatement stmt2 = connection.prepareStatement(ccDestinoSelect);
            stmt2.setInt(1, Integer.parseInt(monto));
            stmt2.setString(2, ccOrigen);
            PreparedStatement stmt3 = connection.prepareStatement(transaccion);
            stmt3.setString(1, uuid.toString());
            stmt3.setString(2, ccOrigen);
            stmt3.setString(3, ccDestino);
            stmt3.setInt(4, Integer.parseInt(monto));
            System.out.println(stmt.executeUpdate());
            System.out.println(stmt2.executeUpdate());
            System.out.println(stmt3.executeUpdate());

            return res.put("transferencia", true);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res.put("transferencia", false);

    }

    @Override
    public JSONObject mostrarAutorizaciones() throws Exception {
        JSONObject res = new JSONObject();
        int key = 0;
        String select = "SELECT * FROM autorizacion;";
        try {
            ResultSet resultSet = connection.prepareStatement(select).executeQuery();
            while (resultSet.next()){
                JSONObject res2 = new JSONObject();
                key += 1;
                res2.put("id",resultSet.getString("id"));
                res2.put("cedula",resultSet.getString("cedula"));
                res2.put("monto",resultSet.getInt("monto"));
                res.put(String.valueOf(key), res2);
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
        return res;
    }

    private boolean evitarSql(String cadena){
        boolean bool = false;
        char[] caracter = {';', '(', ')', '!', '"', '?', '$' };
        for (int i = 0; i< cadena.length(); i++){
            char ch = cadena.charAt(i);
            for (int j = 0; j< caracter.length ; j++){
                if ( caracter[i] == ch){
                    bool = true;
                }
            }
        }
        return bool;
    }
}
