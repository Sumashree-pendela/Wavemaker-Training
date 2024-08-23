package org.example.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.dto.EmployeeRequest;
import org.example.dto.EmployeeServletRequest;
import org.example.enums.StorageType;
import org.example.exception.AddressNotFoundException;
import org.example.exception.EmployeeNotFoundException;
import org.example.model.Employee;
import org.example.service.EmployeeService;
import org.example.service.impl.EmployeeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = "/employee")
public class EmployeeServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServlet.class);

    EmployeeService employeeService = null;


    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        System.out.println("Initialized");
        logger.info("EmployeeServlet initialized");
        employeeService = new EmployeeServiceImpl(StorageType.DATABASE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Gson gson = new Gson();
        String jsonEmployee = null;

        String id = req.getParameter("id");

        if (id == null || id.isEmpty()) {
            try {
                logger.info("Getting all employees List");
                List<Employee> employeeList = employeeService.getAllEmployees();
                jsonEmployee = gson.toJson(employeeList);
            } catch (EmployeeNotFoundException | AddressNotFoundException e) {
                throw new RuntimeException(e);
            }

            System.out.println(jsonEmployee);
            resp.setContentType("application/json");
            PrintWriter out = resp.getWriter();
            out.println(jsonEmployee);
        } else {
            try {
                int empId = Integer.parseInt(req.getParameter("id"));
                logger.info("Getting employee details with id: {}", empId);
                Employee employee = employeeService.getByEmployeeId(empId);
                jsonEmployee = gson.toJson(employee);

                System.out.println(jsonEmployee);
                resp.setContentType("application/json");
                PrintWriter out = resp.getWriter();
                out.println(jsonEmployee);
            } catch (Exception e) {
                logger.info(e.toString());
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter printWriter = resp.getWriter();
        boolean validated = cookieValidation(req);

        if (validated) {
            Gson gson = new Gson();

            BufferedReader reader = req.getReader();
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String jsonString = stringBuilder.toString();
            EmployeeRequest employeeRequest = gson.fromJson(jsonString, EmployeeRequest.class);

            try {
                logger.debug("Adding employee to table with details: {}", employeeRequest);
                employeeService.addEmployee(employeeRequest);
                printWriter.println("Employee added successfully");
            } catch (AddressNotFoundException | EmployeeNotFoundException e) {
                logger.debug(e.toString());
                throw new RuntimeException(e);
            }
        } else {
            resp.setStatus(400);
            throw new RuntimeException("Un authorized");
        }

    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        boolean validated = cookieValidation(req);

        if (validated) {
            Gson gson = new Gson();
            String jsonResponse = null;
            try {
                BufferedReader reader = req.getReader();
                StringBuilder jsonStringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonStringBuilder.append(line);
                }
                EmployeeServletRequest employee = gson.fromJson(jsonStringBuilder.toString(), EmployeeServletRequest.class);
                EmployeeRequest employeeRequest = new EmployeeRequest(employee.getName(), employee.getEmail(), employee.getLocation(), employee.getPin());
                logger.debug("Updating employee with details Id: {} and {}", employee.getId(), employeeRequest);

                String value = employeeService.updateEmployee(employee.getId(), employeeRequest);
            } catch (Exception e) {
                logger.error(e.toString());
            }

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter out = resp.getWriter();
            out.println("Updated Employee successfully");
        } else {
            resp.setStatus(400);
            throw new RuntimeException("Un authorized");
        }
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean validated = cookieValidation(req);

        if (!validated) {
            logger.debug("Unauthorized Login");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        Gson gson = new Gson();
        String jsonResponse = null;
        int id = 0;
        try {
            id = Integer.parseInt(req.getParameter("id"));
            logger.debug("Deleting employee with id : {}", id);
            employeeService.deleteEmployee(id);
            jsonResponse = gson.toJson(id);
        } catch (Exception e) {
            logger.info(e.toString());
        }
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("Deleted employee successfully");
    }

    private boolean cookieValidation(HttpServletRequest request) {
        boolean validated = false;
        Cookie[] cookies = request.getCookies();

        HttpSession session = request.getSession(true);
        String cookieValue = session.getAttribute("loginCookie").toString();

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("loginCookie") && cookie.getValue().equals(cookieValue)) {
                validated = true;
                break;
            }
        }

        return validated;

    }

}
