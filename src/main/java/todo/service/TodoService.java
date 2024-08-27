package todo.service;

import todo.dto.Todo;
import todo.exception.LoggedInUserNotFoundException;
import todo.model.TodoRequest;

import java.util.List;

public interface TodoService {

    Todo create(TodoRequest todoRequest) throws LoggedInUserNotFoundException;
    Todo getById(int todoId);
    Todo update(int id, TodoRequest todoRequest);
    int delete(int todoId);
    List<Todo> findAll() throws LoggedInUserNotFoundException;
    Todo markAsComplete(int todoId);

    List<Todo> sortByPriority() throws LoggedInUserNotFoundException;


}
