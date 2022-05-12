package edu.escuelaing.niubank.services;

import org.json.JSONObject;
import com.google.gson.JsonObject;
import edu.escuelaing.niubank.controller.auth.LoginDto;
import edu.escuelaing.niubank.controller.auth.TokenDto;

public interface UserServices {


    TokenDto Login(LoginDto loginDto);
    JSONObject verMonto (String cedula);

}
