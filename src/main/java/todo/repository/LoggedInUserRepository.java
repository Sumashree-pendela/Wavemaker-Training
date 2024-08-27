package todo.repository;

import todo.dto.LoggedInUser;
import todo.dto.Login;
import todo.exception.LoggedInUserNotFoundException;
import todo.exception.LoginNotFoundException;

public interface LoggedInUserRepository {
    LoggedInUser getUserName() throws LoggedInUserNotFoundException;
    LoggedInUser update(LoggedInUser login) throws LoggedInUserNotFoundException;
}
