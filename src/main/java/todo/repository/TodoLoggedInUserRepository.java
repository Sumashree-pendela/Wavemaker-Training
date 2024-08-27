package todo.repository;

import todo.dto.TodoLoggedInUser;
import todo.model.TodoLoggedInUserRequest;

import java.util.List;

public interface TodoLoggedInUserRepository {
    TodoLoggedInUser create(TodoLoggedInUserRequest todoLoggedInUserRequest);
    List<TodoLoggedInUser> getByUserName(String userName);

    int delete(int todoId);
}
