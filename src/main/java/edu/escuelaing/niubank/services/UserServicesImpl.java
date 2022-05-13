package edu.escuelaing.niubank.services;

import edu.escuelaing.niubank.controller.auth.LoginDto;
import edu.escuelaing.niubank.controller.auth.TokenDto;
import edu.escuelaing.niubank.repository.ControllerDb;
import org.json.JSONObject;


public class UserServicesImpl implements UserServices{

    private ControllerDb controllerDb = new ControllerDb();


    public UserServicesImpl(){

    }

    @Override
    public TokenDto Login(LoginDto loginDto) {
        controllerDb.verificarUser(loginDto);
        return null;
    }

    @Override
    public JSONObject verMonto(String cedula) {
        return  controllerDb.verMonto(cedula);
    }

    @Override
    public JSONObject verTransferencias() {
        return controllerDb.verTransferencias();
    }

    @Override
    public JSONObject solicitarSobregiro(String cedula, String monto) {
        return controllerDb.solicitarSobregiro(cedula,monto);
    }


}
