package todo.service;

import todo.dto.TodoLoggedInUser;
import todo.model.TodoLoggedInUserRequest;

import java.util.List;

public interface TodoLoggedInUserService {
    TodoLoggedInUser create(TodoLoggedInUserRequest todoLoggedInUserRequest);
    List<TodoLoggedInUser> getByUserName(String userName);

    int delete(int todoId);
}
