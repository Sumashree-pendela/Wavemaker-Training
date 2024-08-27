package todo.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import todo.dto.TodoLoggedInUser;
import todo.model.TodoLoggedInUserRequest;
import todo.repository.TodoLoggedInUserRepository;
import todo.repository.impl.TodoLoggedInUserRepositoryImpl;
import todo.service.TodoLoggedInUserService;

import java.util.List;

public class TodoLoggedInUserServiceImpl implements TodoLoggedInUserService {
    private static final Logger logger = LoggerFactory.getLogger(TodoLoggedInUserServiceImpl.class);

    TodoLoggedInUserRepository todoLoggedInUserRepository = new TodoLoggedInUserRepositoryImpl();

    @Override
    public TodoLoggedInUser create(TodoLoggedInUserRequest todoLoggedInUserRequest) {
        logger.debug("Creating todo Logged in user using {}", todoLoggedInUserRequest);
        return todoLoggedInUserRepository.create(todoLoggedInUserRequest);
    }

    @Override
    public List<TodoLoggedInUser> getByUserName(String userName) {
        logger.debug("Getting list of todos by userName: {}", userName);
        return todoLoggedInUserRepository.getByUserName(userName);
    }

    @Override
    public int delete(int todoId) {
        logger.debug("Deleting logged in user by todo Id");
        return todoLoggedInUserRepository.delete(todoId);
    }
}
