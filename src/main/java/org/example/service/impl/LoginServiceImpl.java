package org.example.service.impl;

import org.example.exception.LoginNotFoundException;
import org.example.model.Login;
import org.example.repository.LoginRepository;
import org.example.repository.impl.DatabaseLoginRepositoryImpl;
import org.example.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginServiceImpl implements LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    private LoginRepository loginRepository = new DatabaseLoginRepositoryImpl();

    @Override
    public Login getByUserName(String userName) throws LoginNotFoundException {
        logger.debug("Getting login details with userName: {}", userName);
        return loginRepository.getByUserName(userName);
    }

    @Override
    public Login create(Login login) {
        logger.debug("Adding into Login with details : {}", login);
        return loginRepository.create(login);
    }
}
