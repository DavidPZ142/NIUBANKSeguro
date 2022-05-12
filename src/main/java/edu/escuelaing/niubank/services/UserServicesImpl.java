package edu.escuelaing.niubank.services;

import edu.escuelaing.niubank.controller.auth.LoginDto;
import edu.escuelaing.niubank.controller.auth.TokenDto;
import edu.escuelaing.niubank.repository.ControllerDb;


public class UserServicesImpl implements UserServices{

    private ControllerDb controllerDb = new ControllerDb();

    public UserServicesImpl(){

    }

    @Override
    public TokenDto Login(LoginDto loginDto) {
        controllerDb.verificarUser(loginDto);
        return null;
    }
}
