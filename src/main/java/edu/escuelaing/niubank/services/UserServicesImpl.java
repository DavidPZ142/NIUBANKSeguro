package edu.escuelaing.niubank.services;

import edu.escuelaing.niubank.controller.auth.LoginDto;
import edu.escuelaing.niubank.controller.auth.TokenDto;
import edu.escuelaing.niubank.data.User;
import edu.escuelaing.niubank.repository.ControllerDb;
import edu.escuelaing.niubank.security.Tokenizer;
import org.json.JSONObject;
import java.util.Objects;


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

    public String verMonto(String token) throws Exception {
        Tokenizer tokenizer = new Tokenizer();
        System.out.println(token);
        return  controllerDb.verMonto(tokenizer.getInfoToken(token));
    }

    @Override
    public JSONObject verTransferencias() {
        return controllerDb.verTransferencias();
    }

    @Override
    public JSONObject solicitarSobregiro(String cedula, String monto) throws Exception {
        return controllerDb.solicitarSobregiro(cedula,monto);
    }

    @Override
    public User loadInfoUser(String token) {
        Tokenizer tokenizer = new Tokenizer();
        String key = tokenizer.getInfoToken(token);
        return controllerDb.findUser(key);
    }

    @Override
    public boolean registrarUser(String token, String cedula) {
        Tokenizer tokenizer = new Tokenizer();
        String key = tokenizer.getInfoToken(token);
        if(controllerDb.buscarUser(key) && !Objects.equals(cedula, "")){
            try {
                controllerDb.registrarUser(cedula);
                return true;
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }else return false;
    }

    @Override
    public boolean crearUser(String cedula, User user) {
        try {
            controllerDb.crearUser(cedula, user);
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
