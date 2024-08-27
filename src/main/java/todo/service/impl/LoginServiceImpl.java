package todo.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import todo.dto.Login;
import todo.exception.LoginNotFoundException;
import todo.repository.LoginRepository;
import todo.repository.impl.LoginRepositoryImpl;
import todo.service.LoginService;

public class LoginServiceImpl implements LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    private LoginRepository loginRepository = new LoginRepositoryImpl();

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
