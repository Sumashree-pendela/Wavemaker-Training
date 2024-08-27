package todo.model;

import todo.enums.TodoPriority;

import java.time.LocalDate;
import java.time.LocalTime;

public class TodoRequest {
    private String name;
    private TodoPriority priority;
    private LocalDate todoDate;
    private LocalTime todoTime;

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

    @Override
    public String toString() {
        return "Todo{" +
                ", name='" + name + '\'' +
                ", priority=" + priority +
                ", todoDate=" + todoDate +
                ", todoTime=" + todoTime +
                '}';
    }
}
