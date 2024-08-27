package todo.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import todo.dto.TodoLoggedInUser;
import todo.model.TodoLoggedInUserRequest;
import todo.repository.TodoLoggedInUserRepository;
import todo.util.DatabaseConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TodoLoggedInUserRepositoryImpl implements TodoLoggedInUserRepository {

    private static final Logger logger = LoggerFactory.getLogger(TodoLoggedInUserRepositoryImpl.class);

    private static TodoLoggedInUserRepositoryImpl instance;

    public TodoLoggedInUserRepositoryImpl() {
        System.out.println("Database Todo Logged In User instance created");
    }

    public static TodoLoggedInUserRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new TodoLoggedInUserRepositoryImpl();
        }
        return instance;
    }

    @Override
    public TodoLoggedInUser create(TodoLoggedInUserRequest todoLoggedInUserRequest) {
        logger.debug("Adding todoLoggedInUser into database with details {}", todoLoggedInUserRequest);
        String query = "insert into todo_logged_in_user(todo_id, user_name) values(?,?)";
        TodoLoggedInUser todoLoggedInUser = new TodoLoggedInUser();
        try {
            PreparedStatement preparedStatement = DatabaseConnector.connect().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, todoLoggedInUserRequest.getTodoId());
            preparedStatement.setString(2, todoLoggedInUserRequest.getUserName());
            int rows = preparedStatement.executeUpdate();
            logger.debug("Rows..." + rows);

            if (rows > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    todoLoggedInUser.setId(resultSet.getInt(1));
                    todoLoggedInUser.setTodoId(todoLoggedInUserRequest.getTodoId());
                    todoLoggedInUser.setUserName(todoLoggedInUserRequest.getUserName());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return todoLoggedInUser;
    }

    @Override
    public List<TodoLoggedInUser> getByUserName(String userName) {
        logger.debug("Getting loggedInUser by id: {}", userName);
        String selectQuery = "select * from todo_logged_in_user where user_name = ?";
        List<TodoLoggedInUser> todoLoggedInUserList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = DatabaseConnector.connect().prepareStatement(selectQuery);
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                // Create a new TodoLoggedInUser object for each row
                TodoLoggedInUser todoLoggedInUser = new TodoLoggedInUser();
                todoLoggedInUser.setId(resultSet.getInt("id"));
                todoLoggedInUser.setTodoId(resultSet.getInt("todo_id"));
                todoLoggedInUser.setUserName(resultSet.getString("user_name"));

                // Add the object to the list
                todoLoggedInUserList.add(todoLoggedInUser);
            }
        } catch (SQLException e) {
            logger.debug(e.toString());
        }
        return todoLoggedInUserList;
    }

    @Override
    public int delete(int todoId) {
        logger.debug("Deleting todo logged in user with todoId : {}", todoId);
        String deleteQuery = "delete from todo_logged_in_user where todo_id = ?";
        try {
            PreparedStatement preparedStatement = DatabaseConnector.connect().prepareStatement(deleteQuery);
            preparedStatement.setInt(1, todoId);
            preparedStatement.executeUpdate();
            logger.debug("Deleted todo Logged in user successfully");
        } catch (SQLException e) {
           logger.debug(e.getMessage());
        }
        return todoId;
    }
}
