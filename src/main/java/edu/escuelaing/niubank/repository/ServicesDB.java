package edu.escuelaing.niubank.repository;
import org.json.JSONObject;

import edu.escuelaing.niubank.controller.auth.LoginDto;

public interface ServicesDB {

    boolean verificarUser(LoginDto loginDto);

    JSONObject verMonto(String cedula);

    JSONObject verTransferencias();


}
