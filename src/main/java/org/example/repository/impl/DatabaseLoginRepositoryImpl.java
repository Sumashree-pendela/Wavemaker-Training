package org.example.repository.impl;

import org.example.exception.LoginNotFoundException;
import org.example.model.Login;
import org.example.repository.LoginRepository;
import org.example.util.DatabaseConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseLoginRepositoryImpl implements LoginRepository {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseLoginRepositoryImpl.class);

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
                return login;
            }
        } catch (SQLException e) {
            throw new LoginNotFoundException("Invalid UserName");
        }
        return null;
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

            System.out.println("Inserted into login.....");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return login;
    }
}
