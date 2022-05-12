package edu.escuelaing.niubank.repository;

import edu.escuelaing.niubank.controller.auth.LoginDto;

public interface ServicesDB {

    boolean verificarUser(LoginDto loginDto);


}
