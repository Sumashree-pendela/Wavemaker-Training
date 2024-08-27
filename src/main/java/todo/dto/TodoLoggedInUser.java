package todo.dto;

import java.util.Objects;

public class TodoLoggedInUser {

    private int id;
    private int todoId;
    private String userName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTodoId() {
        return todoId;
    }

    public void setTodoId(int todoId) {
        this.todoId = todoId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "TodoLoggedInUser{" +
                "id=" + id +
                ", todoId=" + todoId +
                ", userName='" + userName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TodoLoggedInUser that = (TodoLoggedInUser) o;
        return id == that.id && todoId == that.todoId && Objects.equals(userName, that.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, todoId, userName);
    }
}
