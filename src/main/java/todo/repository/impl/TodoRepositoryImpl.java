package todo.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import todo.dto.LoggedInUser;
import todo.dto.Todo;
import todo.dto.TodoLoggedInUser;
import todo.enums.TodoPriority;
import todo.exception.LoggedInUserNotFoundException;
import todo.model.TodoRequest;
import todo.repository.LoggedInUserRepository;
import todo.repository.TodoLoggedInUserRepository;
import todo.repository.TodoRepository;
import todo.util.DatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TodoRepositoryImpl implements TodoRepository {

    LoggedInUserRepository loggedInUserRepository = new LoggedInUserRepositoryImpl();

    TodoLoggedInUserRepository todoLoggedInUserRepository = new TodoLoggedInUserRepositoryImpl();

    private static TodoRepositoryImpl instance;

    private static final Logger logger = LoggerFactory.getLogger(TodoRepositoryImpl.class);

    public static TodoRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new TodoRepositoryImpl();
        }
        return instance;
    }

    @Override
    public Todo create(TodoRequest todoRequest) {
        logger.debug("Adding todo into database with details {} ", todoRequest);
        String query = "insert into todo(name, priority, todo_date, todo_time) values(?,?,?,?)";
        Todo todo = new Todo();
        try {
            PreparedStatement preparedStatement = DatabaseConnector.connect().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, todoRequest.getName());
            preparedStatement.setString(2, todoRequest.getPriority().toString());
            preparedStatement.setDate(3, Date.valueOf(todoRequest.getTodoDate()));
            preparedStatement.setTime(4, Time.valueOf(todoRequest.getTodoTime()));
            int rows = preparedStatement.executeUpdate();
            logger.debug("Inserted into employee");

            if (rows > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    todo.setId(resultSet.getInt(1));
                    todo.setName(todoRequest.getName());
                    todo.setPriority(todoRequest.getPriority());
                    todo.setTodoDate(todoRequest.getTodoDate());
                    todo.setTodoTime(todoRequest.getTodoTime());
                    todo.setCompleted(false);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todo;
    }

    @Override
    public Todo getById(int todoId) {
        String selectQuery = "select * from todo where id = ?";
        Todo todo = new Todo();
        try {
            PreparedStatement preparedStatement = DatabaseConnector.connect().prepareStatement(selectQuery);
            preparedStatement.setInt(1, todoId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                todo.setId(todoId);
                todo.setName(resultSet.getString(2));
                todo.setPriority(TodoPriority.valueOf(resultSet.getString(3)));
                todo.setTodoDate(resultSet.getDate(4).toLocalDate());
                todo.setTodoTime(resultSet.getTime(5).toLocalTime());
                todo.setCompleted(resultSet.getBoolean(6));
            }
        } catch (SQLException e) {
            logger.debug(e.toString());
        }
        return todo;
    }

    @Override
    public Todo update(int todoId, TodoRequest todoRequest) {
        logger.debug("Updating todo with details {}", todoRequest);
        String query = "update todo set name = ?, priority = ?, todo_date = ?, todo_time = ? where id = ?";
        Todo todo = new Todo();
        try {
            PreparedStatement preparedStatement = DatabaseConnector.connect().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, todoRequest.getName());
            preparedStatement.setString(2, todoRequest.getPriority().toString());
            preparedStatement.setDate(3, Date.valueOf(todoRequest.getTodoDate()));
            preparedStatement.setTime(4, Time.valueOf(todoRequest.getTodoTime()));
            preparedStatement.setInt(5, todoId);
            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    todo.setId(resultSet.getInt(1));
                    todo.setName(todoRequest.getName());
                    todo.setPriority(todoRequest.getPriority());
                    todo.setTodoDate(todoRequest.getTodoDate());
                    todo.setTodoTime(todoRequest.getTodoTime());
                    todo.setCompleted(false);
                }
            }
        } catch (SQLException e) {
            logger.debug(e.toString());
        }
        return todo;
    }

    @Override
    public int delete(int todoId) {
        logger.debug("Deleting todo with id : {}", todoId);
        String deleteQuery = "delete from todo where id = ?";
        try {
            PreparedStatement preparedStatement = DatabaseConnector.connect().prepareStatement(deleteQuery);
            preparedStatement.setInt(1, todoId);
            preparedStatement.executeUpdate();
            logger.debug("Deleted todo successfully");
        } catch (SQLException e) {
            logger.debug(e.toString());
        }
        return todoId;
    }

    @Override
    public List<Todo> findAll() throws LoggedInUserNotFoundException {
        logger.debug("Finding all todos..");
        //get username
        LoggedInUser loggedInUser = loggedInUserRepository.getUserName();
        String userName = loggedInUser.getUserName();
        logger.debug("Username: {}", userName);

        logger.debug("Finding todos only that user..");

        //find todos related to that user
        List<TodoLoggedInUser> loggedInUsers = todoLoggedInUserRepository.getByUserName(userName);
        logger.debug("Todos.." + loggedInUsers);
        List<Todo> todoList = new ArrayList<>();

        //return the todos..
        for (TodoLoggedInUser todoLoggedInUser : loggedInUsers) {
            Todo todo = getById(todoLoggedInUser.getTodoId());
            System.out.println("Each todo" + todo);
            todoList.add(todo);
        }

        return todoList;
    }

    @Override
    public Todo markAsComplete(int todoId) {
        logger.debug("marking todo as completed : {}", todoId);
        String query = "update todo set completed = ? where id = ?";
        Todo todo = new Todo();
        try {
            PreparedStatement preparedStatement = DatabaseConnector.connect().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setBoolean(1, true);
            preparedStatement.setInt(2, todoId);
            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {

                todo = getById(todoId);
            }
        } catch (SQLException e) {
            logger.debug(e.toString());
        }
        return todo;
    }

    @Override
    public List<Todo> sortByPriority() throws LoggedInUserNotFoundException {
        List<Todo> todoList = findAll();

        return todoList;
    }
}
