package todo.repository;

import todo.dto.Todo;
import todo.exception.LoggedInUserNotFoundException;
import todo.model.TodoRequest;

import java.util.List;

public interface TodoRepository {

    Todo create(TodoRequest todoRequest);
    Todo getById(int todoId);
    Todo update(int todoId, TodoRequest todoRequest);
    int delete(int todoId);
    List<Todo> findAll() throws LoggedInUserNotFoundException;

    Todo markAsComplete(int todoId);

    List<Todo> sortByPriority() throws LoggedInUserNotFoundException;
}
