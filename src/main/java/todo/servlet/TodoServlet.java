package todo.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import todo.dto.Todo;
import todo.model.TodoRequest;
import todo.service.TodoService;
import todo.service.impl.TodoServiceImpl;
import todo.util.GsonConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/todo")
public class TodoServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(TodoServlet.class);

    TodoService todoService = null;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        System.out.println("Initialized");
        logger.info("TodoServlet initialized");
        todoService = new TodoServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        boolean validated = cookieValidation(req);

        if (validated) {
            Gson gson = GsonConfig.createGson();
            String jsonTodo = null;

            String id = req.getParameter("id");

            if (id == null || id.isEmpty()) {
                try {
                    logger.info("Getting all todos List");
                    List<Todo> todoList = todoService.findAll();
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
            } else {
                try {
                    int todoId = Integer.parseInt(req.getParameter("id"));
                    logger.info("Getting todo details with id: {}", todoId);
                    Todo todo = todoService.getById(todoId);
                    jsonTodo = gson.toJson(todo);

                    System.out.println(jsonTodo);
                    resp.setContentType("application/json");
                    PrintWriter out = resp.getWriter();
                    out.println(jsonTodo);
                } catch (Exception e) {
                    logger.debug(e.toString());
                }
            }
        } else {
            resp.sendRedirect("login.html");
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String todoId = req.getParameter("id");

        boolean validated = cookieValidation(req);
        PrintWriter out = resp.getWriter();

        if (validated) {
            if (todoId == null || todoId.isEmpty()) {

                Gson gson = GsonConfig.createGson();
                String jsonTodo = null;

                BufferedReader reader = req.getReader();
                TodoRequest todoRequest = gson.fromJson(reader, TodoRequest.class);
                System.out.println("todo request.." + todoRequest);

                try {
                    logger.debug("Adding todo to table with details: {}", todoRequest);
                    Todo todo = todoService.create(todoRequest);
                    jsonTodo = gson.toJson(todo);

                    System.out.println(jsonTodo);
                    resp.setContentType("application/json");

                    out.println(jsonTodo);
                } catch (Exception e) {
                    logger.debug(e.toString());
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println("About to mark as complete..");
                todoService.markAsComplete(Integer.parseInt(todoId));
                out.println("marked as completed");
            }
        } else {
            resp.sendRedirect("login.html");
            resp.setStatus(400);
            throw new RuntimeException("Un authorized");

        }

    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        boolean validated = cookieValidation(req);
        int todoId = Integer.parseInt(req.getParameter("id"));

        if (validated) {

            Gson gson = GsonConfig.createGson();
            String jsonTodo = null;

            try {
                BufferedReader reader = req.getReader();

                TodoRequest todoRequest = gson.fromJson(reader, TodoRequest.class);
                logger.debug("Updating todo with details Id: {} and {}", todoId, todoRequest);

                Todo todo = todoService.update(todoId, todoRequest);
                jsonTodo = gson.toJson(todo);

                resp.setContentType("application/json");
                PrintWriter out = resp.getWriter();
                out.println(jsonTodo);
            } catch (Exception e) {
                logger.error(e.toString());
            }

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter out = resp.getWriter();
            out.println("Updated Employee successfully");
        } else {
            resp.sendRedirect("login.html");
            resp.setStatus(400);
            throw new RuntimeException("Un authorized");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        boolean validated = cookieValidation(req);

        System.out.println("validation.." + validated);
        if (!validated) {
            resp.sendRedirect("login.html");
            logger.debug("Unauthorized Login");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        int id = 0;
        try {
            id = Integer.parseInt(req.getParameter("id"));
            logger.debug("Deleting todo with id : {}", id);
            todoService.delete(id);
        } catch (Exception e) {
            logger.info(e.toString());
        }
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("Deleted employee successfully id: " + id);
    }

    private boolean cookieValidation(HttpServletRequest request) {
        boolean validated = false;
        Cookie[] cookies = request.getCookies();

        HttpSession session = request.getSession(true);
        Object cookieValue = session.getAttribute("loginCookie");

        if (cookieValue != null) {
            System.out.println("Cookie value.." + cookieValue.toString());

            for (Cookie cookie : cookies) {
                System.out.println("From Request.." + cookie.getValue());
                if (cookie.getName().equals("loginCookie") && cookie.getValue().equals(cookieValue.toString())) {
                    validated = true;
                    break;
                }
            }
            System.out.println("Validated.." + validated);
        }

        return validated;


    }
}
