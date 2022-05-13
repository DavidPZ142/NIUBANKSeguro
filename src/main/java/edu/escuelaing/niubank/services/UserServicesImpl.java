package edu.escuelaing.niubank.services;

import edu.escuelaing.niubank.controller.auth.LoginDto;
import edu.escuelaing.niubank.controller.auth.TokenDto;
import edu.escuelaing.niubank.data.User;
import edu.escuelaing.niubank.repository.ControllerDb;

import edu.escuelaing.niubank.security.Tokenizer;

import org.json.JSONObject;



public class UserServicesImpl implements UserServices{

    private ControllerDb controllerDb = new ControllerDb();


    public UserServicesImpl(){

    }

    @Override
    public TokenDto Login(LoginDto loginDto) {
        if(controllerDb.verificarUser(loginDto)){
            Tokenizer tokenizer = new Tokenizer();
            return tokenizer.generateToken(loginDto.getEmail());
        }
        return null;
    }

    @Override
    public JSONObject verMonto(String cedula) throws Exception {
        return  controllerDb.verMonto(cedula);
    }

    @Override
    public JSONObject verTransferencias() {
        return controllerDb.verTransferencias();
    }

    @Override
    public JSONObject solicitarSobregiro(String cedula, String monto) throws Exception {
        return controllerDb.solicitarSobregiro(cedula,monto);
    }


}
