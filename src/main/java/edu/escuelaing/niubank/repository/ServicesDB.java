package edu.escuelaing.niubank.repository;
import org.json.JSONObject;

import edu.escuelaing.niubank.controller.auth.LoginDto;
import edu.escuelaing.niubank.data.User;
import org.json.JSONObject;


public interface ServicesDB {

    boolean verificarUser(LoginDto loginDto);
    User findUser(String identificador);
    void registrarUser(String cedula);
    boolean buscarUser(String cedula);
    void crearUser(String cedula, User user);
    String verMonto(String cedula) throws Exception;
    JSONObject verTransferencias();
    JSONObject solicitarSobregiro(String cedula, String monto) throws Exception;
    JSONObject modificarMonto(String cedula, String Cantidad) throws Exception;
    JSONObject transferencia(String ccOrigen, String ccDestino, String monto) throws Exception;
    JSONObject mostrarAutorizaciones() throws  Exception;

}
