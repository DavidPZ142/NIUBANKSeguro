package edu.escuelaing.niubank.repository;
import org.json.JSONObject;

import edu.escuelaing.niubank.controller.auth.LoginDto;
import edu.escuelaing.niubank.data.User;

public interface ServicesDB {

    boolean verificarUser(LoginDto loginDto);

    JSONObject verMonto(String cedula);

    JSONObject verTransferencias();

    JSONObject solicitarSobregiro(String cedula, String monto);


}
