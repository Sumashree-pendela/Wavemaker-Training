package todo.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import todo.dto.LoggedInUser;
import todo.exception.LoggedInUserNotFoundException;
import todo.repository.LoggedInUserRepository;
import todo.repository.impl.LoggedInUserRepositoryImpl;
import todo.service.LoggedInUserService;

public class LoggedInUserServiceImpl implements LoggedInUserService {

    private static final Logger logger = LoggerFactory.getLogger(LoggedInUserServiceImpl.class);

    private final LoggedInUserRepository loggedInUserRepository = new LoggedInUserRepositoryImpl();

    @Override
    public LoggedInUser getUserName() throws LoggedInUserNotFoundException {
        logger.debug("Getting LoggedInUser details ");
        return loggedInUserRepository.getUserName();
    }

    @Override
    public LoggedInUser update(LoggedInUser loggedInUser) throws LoggedInUserNotFoundException {
        logger.debug("Updating Logged in user with details : {}", loggedInUser);
        return loggedInUserRepository.update(loggedInUser);
    }
}
