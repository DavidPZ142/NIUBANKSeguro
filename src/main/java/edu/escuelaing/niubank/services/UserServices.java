package edu.escuelaing.niubank.services;

import edu.escuelaing.niubank.controller.auth.LoginDto;
import edu.escuelaing.niubank.controller.auth.TokenDto;

public interface UserServices {


    TokenDto Login(LoginDto loginDto);

}
