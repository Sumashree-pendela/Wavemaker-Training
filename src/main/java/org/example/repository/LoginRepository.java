package org.example.repository;

import org.example.exception.LoginNotFoundException;
import org.example.model.Login;

public interface LoginRepository {
    Login getByUserName(String userName) throws LoginNotFoundException;
    Login create(Login login);
}
