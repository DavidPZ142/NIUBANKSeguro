package edu.escuelaing.niubank.services;

import edu.escuelaing.niubank.controller.auth.LoginDto;
import edu.escuelaing.niubank.controller.auth.TokenDto;
import edu.escuelaing.niubank.data.User;
import org.json.JSONObject;


public interface UserServices {


    TokenDto Login(LoginDto loginDto);
    JSONObject verMonto (String cedula);
    JSONObject verTransferencias();
    JSONObject solicitarSobregiro(String cedula, String monto);
    User loadInfoUser(String token);
    boolean registrarUser(String token, String cedula);
    boolean crearUser(String cedula, User user);

}
