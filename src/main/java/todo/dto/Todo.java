package todo.dto;

import todo.enums.TodoPriority;

import java.time.LocalDate;
import java.time.LocalTime;


public class Todo {
    private int id;
    private String name;
    private TodoPriority priority;
    private LocalDate todoDate;
    private LocalTime todoTime;
    private boolean completed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TodoPriority getPriority() {
        return priority;
    }

    public void setPriority(TodoPriority priority) {
        this.priority = priority;
    }

    public LocalDate getTodoDate() {
        return todoDate;
    }

    public void setTodoDate(LocalDate todoDate) {
        this.todoDate = todoDate;
    }

    public LocalTime getTodoTime() {
        return todoTime;
    }

    public void setTodoTime(LocalTime todoTime) {
        this.todoTime = todoTime;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", priority=" + priority +
                ", todoDate=" + todoDate +
                ", todoTime=" + todoTime +
                ", completed=" + completed +
                '}';
    }
}
