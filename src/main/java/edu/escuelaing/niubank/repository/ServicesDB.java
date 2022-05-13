package edu.escuelaing.niubank.repository;

import edu.escuelaing.niubank.controller.auth.LoginDto;
import edu.escuelaing.niubank.data.User;

public interface ServicesDB {

    boolean verificarUser(LoginDto loginDto);


}
