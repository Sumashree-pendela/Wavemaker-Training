package todo.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import todo.dto.LoggedInUser;
import todo.exception.LoggedInUserNotFoundException;
import todo.repository.LoggedInUserRepository;
import todo.util.DatabaseConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoggedInUserRepositoryImpl implements LoggedInUserRepository {

    private static final Logger logger = LoggerFactory.getLogger(LoggedInUserRepositoryImpl.class);

    @Override
    public LoggedInUser getUserName() throws LoggedInUserNotFoundException {
        logger.debug("Getting Login Details by userName:");
        String selectQuery = "select * from logged_in_user";
        LoggedInUser loggedInUser = new LoggedInUser();
        try {
            PreparedStatement preparedStatement = DatabaseConnector.connect().prepareStatement(selectQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                loggedInUser.setUserName(resultSet.getString(1));
                loggedInUser.setPassword(resultSet.getString(2));
            }
        } catch (SQLException e) {
            throw new LoggedInUserNotFoundException("No user found");
        }
        return loggedInUser;
    }

    @Override
    public LoggedInUser update(LoggedInUser loggedInUser) throws LoggedInUserNotFoundException {
        logger.debug("Adding or updating Login Details in the database with details {}", loggedInUser);

        String checkQuery = "select * from logged_in_user";
        String updateQuery = "update logged_in_user set user_name = ?, password = ?";
        String insertQuery = "insert into logged_in_user (user_name, password) values (?, ?)";

        try {
            PreparedStatement checkStatement = DatabaseConnector.connect().prepareStatement(checkQuery);
            ResultSet resultSet = checkStatement.executeQuery();


            if (resultSet.next()) {
                // If user exists, update the existing record
                PreparedStatement updateStatement = DatabaseConnector.connect().prepareStatement(updateQuery);
                updateStatement.setString(1, loggedInUser.getUserName());
                updateStatement.setString(2, loggedInUser.getPassword());
                updateStatement.executeUpdate();
                logger.debug("Updated existing loggedInUser: {}", loggedInUser.getUserName());
            } else {
                // If user does not exist, insert a new record
                PreparedStatement insertStatement = DatabaseConnector.connect().prepareStatement(insertQuery);
                insertStatement.setString(1, loggedInUser.getUserName());
                insertStatement.setString(2, loggedInUser.getPassword());
                insertStatement.executeUpdate();
                logger.debug("Inserted new loggedInUser: {}", loggedInUser.getUserName());
            }

        } catch (SQLException e) {
            logger.error("Error while updating or inserting loggedInUser: {}", e.getMessage());
        }

        return loggedInUser;
    }

}
