package todo.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import todo.dto.LoggedInUser;
import todo.dto.Todo;
import todo.exception.LoggedInUserNotFoundException;
import todo.model.TodoLoggedInUserRequest;
import todo.model.TodoRequest;
import todo.repository.LoggedInUserRepository;
import todo.repository.TodoLoggedInUserRepository;
import todo.repository.TodoRepository;
import todo.repository.impl.LoggedInUserRepositoryImpl;
import todo.repository.impl.TodoLoggedInUserRepositoryImpl;
import todo.repository.impl.TodoRepositoryImpl;
import todo.service.TodoService;

import java.util.List;

public class TodoServiceImpl implements TodoService {
    private static final Logger logger = LoggerFactory.getLogger(TodoServiceImpl.class);


    TodoRepository todoRepository = new TodoRepositoryImpl();
    TodoLoggedInUserRepository todoLoggedInUserRepository = new TodoLoggedInUserRepositoryImpl();
    LoggedInUserRepository loggedInUserRepository = new LoggedInUserRepositoryImpl();


    @Override
    public Todo create(TodoRequest todoRequest) throws LoggedInUserNotFoundException {
        logger.debug("Creating todo with request {}", todoRequest);
        Todo todo =  todoRepository.create(todoRequest);

        LoggedInUser loggedInUser = loggedInUserRepository.getUserName();
        logger.debug("User name: {}", loggedInUser);

        TodoLoggedInUserRequest request = new TodoLoggedInUserRequest();
        request.setTodoId(todo.getId());
        request.setUserName(loggedInUser.getUserName());

        logger.debug("adding into todo logged in user.. request {}", request);
        todoLoggedInUserRepository.create(request);
        return todo;

    }

    @Override
    public Todo getById(int todoId) {
        logger.debug("Getting todo by id: {}", todoId);
        return todoRepository.getById(todoId);
    }

    @Override
    public Todo update(int todoId, TodoRequest todoRequest) {
        logger.debug("Updating todo with details {}", todoRequest);
        return todoRepository.update(todoId, todoRequest);
    }

    @Override
    public int delete(int todoId) {
        logger.debug("Deleting todo with id: {}", todoId);
        todoLoggedInUserRepository.delete(todoId);

        return todoRepository.delete(todoId);
    }

    @Override
    public List<Todo> findAll() throws LoggedInUserNotFoundException {
        logger.debug("Finding all todos");
        System.out.println("in todo service..");
        return todoRepository.findAll();
    }

    @Override
    public Todo markAsComplete(int todoId) {
        logger.debug("Making this todo as completed id: {}", todoId);
        return todoRepository.markAsComplete(todoId);
    }

    @Override
    public List<Todo> sortByPriority() throws LoggedInUserNotFoundException {
        logger.debug("Sorting todos based on priority");
        return todoRepository.sortByPriority();
    }
}
