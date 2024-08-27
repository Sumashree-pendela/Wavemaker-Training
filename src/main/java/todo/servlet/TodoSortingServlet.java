package todo.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import todo.dto.Todo;
import todo.service.TodoService;
import todo.service.impl.TodoServiceImpl;
import todo.util.GsonConfig;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/todoSort")
public class TodoSortingServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(TodoSortingServlet.class);

    TodoService todoService = null;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        System.out.println("Initialized");
        logger.info("TodoSorting Servlet initialized");
        todoService = new TodoServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Gson gson = GsonConfig.createGson();
        String jsonTodo = null;

        try {
            logger.info("Getting all todos List by sorting");
            List<Todo> todoList = todoService.sortByPriority();
            System.out.println("employee... list" + todoList);
            jsonTodo = gson.toJson(todoList);
            System.out.println("json.." + jsonTodo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println(jsonTodo);
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.println(jsonTodo);

    }
}
