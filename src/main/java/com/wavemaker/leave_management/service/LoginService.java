package com.wavemaker.leave_management.service;

import com.wavemaker.leave_management.dto.Login;
import com.wavemaker.leave_management.exception.LoginNotFoundException;

import java.sql.SQLException;

public interface LoginService {
    Login getByUserName(String userName) throws LoginNotFoundException, SQLException;

    Login create(Login login);
}
