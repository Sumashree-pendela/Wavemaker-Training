package todo.model;

public class TodoLoggedInUserRequest {
    private int todoId;
    private String userName;

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
        return "TodoLoggedInUserRequest{" +
                "todoId=" + todoId +
                ", userName='" + userName + '\'' +
                '}';
    }
}
