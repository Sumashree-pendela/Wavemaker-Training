package todo.service;

import todo.dto.LoggedInUser;
import todo.dto.Login;
import todo.exception.LoggedInUserNotFoundException;
import todo.exception.LoginNotFoundException;

public interface LoggedInUserService {

    LoggedInUser getUserName() throws LoggedInUserNotFoundException;

    LoggedInUser update(LoggedInUser loggedInUser) throws LoggedInUserNotFoundException;
}
