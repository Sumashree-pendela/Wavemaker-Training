package com.wavemaker.leave_management.service.Impl;

import com.wavemaker.leave_management.dto.Login;
import com.wavemaker.leave_management.exception.LoginNotFoundException;
import com.wavemaker.leave_management.repository.Impl.LoginRepositoryImpl;
import com.wavemaker.leave_management.repository.LoginRepository;
import com.wavemaker.leave_management.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class LoginServiceImpl implements LoginService {
    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    private final LoginRepository loginRepository =LoginRepositoryImpl.getInstance();

    @Override
    public Login getByUserName(String userName) throws LoginNotFoundException, SQLException {
        logger.debug("Getting login details with userName: {}", userName);
        return loginRepository.getByUserName(userName);
    }

    @Override
    public Login create(Login login) {
        logger.debug("Adding into Login with details : {}", login);
        return loginRepository.create(login);
    }
}
