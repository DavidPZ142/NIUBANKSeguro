package edu.escuelaing.niubank.services;

import org.json.JSONObject;
import com.google.gson.JsonObject;
import edu.escuelaing.niubank.controller.auth.LoginDto;
import edu.escuelaing.niubank.controller.auth.TokenDto;
import edu.escuelaing.niubank.data.User;
import org.json.JSONObject;


public interface UserServices {


    TokenDto Login(LoginDto loginDto);
    User loadInfoUser(String token);
    boolean registrarUser(String token, String cedula);
    boolean crearUser(String cedula, User user);
    JSONObject verMonto (String cedula) throws Exception;
    JSONObject verTransferencias();
    JSONObject solicitarSobregiro(String cedula, String monto) throws Exception;

}
