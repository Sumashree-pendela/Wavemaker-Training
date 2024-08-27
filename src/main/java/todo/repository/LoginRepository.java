package todo.repository;

import todo.dto.Login;
import todo.exception.LoginNotFoundException;

public interface LoginRepository {
    Login getByUserName(String userName) throws LoginNotFoundException;
    Login create(Login login);
}
