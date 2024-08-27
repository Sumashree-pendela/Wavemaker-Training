package todo.service;

import todo.dto.Login;
import todo.exception.LoginNotFoundException;

public interface LoginService {

    Login getByUserName(String userName) throws LoginNotFoundException;

    Login create(Login login);
}
