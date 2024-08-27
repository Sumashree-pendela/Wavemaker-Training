package todo.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import todo.dto.Login;
import todo.exception.LoginNotFoundException;
import todo.repository.LoginRepository;
import todo.util.DatabaseConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginRepositoryImpl implements LoginRepository {

    private static final Logger logger = LoggerFactory.getLogger(LoginRepositoryImpl.class);

    @Override
    public Login getByUserName(String userName) throws LoginNotFoundException {
        logger.debug("Getting Login Details by userName: {}", userName);
        String selectQuery = "select * from login where user_name = ?";
        Login login = new Login();
        try {
            PreparedStatement preparedStatement = DatabaseConnector.connect().prepareStatement(selectQuery);
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                login.setUserName(userName);
                login.setPassword(resultSet.getString(2));
            }
        } catch (SQLException e) {
            throw new LoginNotFoundException("Invalid UserName");
        }
        return login;
    }

    @Override
    public Login create(Login login) {
        logger.debug("Adding Login Details into database with details {}", login);
        String query = "insert into login(user_name, password) values(?,?)";
        try {
            PreparedStatement preparedStatement = DatabaseConnector.connect().prepareStatement(query);
            preparedStatement.setString(1, login.getUserName());
            preparedStatement.setString(2, login.getPassword());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            logger.debug(e.getMessage());
        }

        return login;
    }
}
