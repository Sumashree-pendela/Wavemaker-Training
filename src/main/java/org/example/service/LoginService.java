package org.example.service;

import org.example.exception.LoginNotFoundException;
import org.example.model.Login;

public interface LoginService {

    Login getByUserName(String userName) throws LoginNotFoundException;

    Login create(Login login);
}
