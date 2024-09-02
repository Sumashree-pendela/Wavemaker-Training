package com.wavemaker.leave_management.repository;

import com.wavemaker.leave_management.dto.Login;
import com.wavemaker.leave_management.exception.LoginNotFoundException;

import java.sql.SQLException;

public interface LoginRepository {
    Login getByUserName(String userName) throws LoginNotFoundException, SQLException;
    Login create(Login login);
}

