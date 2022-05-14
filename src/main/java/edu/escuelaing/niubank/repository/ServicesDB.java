package edu.escuelaing.niubank.repository;

import edu.escuelaing.niubank.controller.auth.LoginDto;
import edu.escuelaing.niubank.data.User;
import org.json.JSONObject;


public interface ServicesDB {

    boolean verificarUser(LoginDto loginDto);
    User findUser(String identificador);
    void registrarUser(String cedula);
    boolean buscarUser(String cedula);
    void crearUser(String cedula, User user);
    JSONObject verMonto(String cedula);
    JSONObject verTransferencias();
    JSONObject solicitarSobregiro(String cedula, String monto);


}
