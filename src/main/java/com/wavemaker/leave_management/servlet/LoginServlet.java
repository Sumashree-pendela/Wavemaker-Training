package com.wavemaker.leave_management.servlet;

import com.google.gson.Gson;
import com.wavemaker.leave_management.dto.Employee;
import com.wavemaker.leave_management.dto.Login;
import com.wavemaker.leave_management.exception.LoginNotFoundException;
import com.wavemaker.leave_management.service.EmployeeService;
import com.wavemaker.leave_management.service.Impl.EmployeeServiceImpl;
import com.wavemaker.leave_management.service.Impl.LoginServiceImpl;
import com.wavemaker.leave_management.service.LoginService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.UUID;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);

    LoginService loginService = new LoginServiceImpl();
    EmployeeService employeeService = new EmployeeServiceImpl();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        BufferedReader reader = req.getReader();

        Gson gson = new Gson();
        Login reqLogin = gson.fromJson(reader, Login.class);
        PrintWriter printWriter = resp.getWriter();

        if (reqLogin == null) {
            logger.error("Invalid login request data");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid login data");
            return;
        }

        Login login = null;
        try {
            login = loginService.getByUserName(reqLogin.getUserName());
            logger.debug("Login.." + login);
        } catch (LoginNotFoundException | SQLException e) {
            logger.error(e.getMessage());
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            String errorJson = String.format("{\"error\": \"%s\"}", "Invalid Username");
            printWriter.println(errorJson);
            return;
        }

        // Check if password matches
        try {
            if (login.getPassword().equals(reqLogin.getPassword())) {
                UUID uuid = UUID.randomUUID();
                String cookieValue = uuid.toString();

                logger.debug("Password matched..");
                HttpSession httpSession = req.getSession(true);
                Cookie cookie = new Cookie("loginCookie", cookieValue);
                httpSession.setAttribute("loginCookie", cookieValue);
                cookie.setMaxAge(60 * 60);
                cookie.setHttpOnly(true);
                cookie.setPath("/");

                // Get employee id from login and send in the cookie
                Employee employee = employeeService.getByUserName(reqLogin.getUserName());
                String employeeId = String.valueOf(employee.getId());

                logger.debug("Adding user cookie");
                Cookie userCookie = new Cookie("userCookie", employeeId);
                httpSession.setAttribute("userCookie", employeeId);
                userCookie.setMaxAge(10 * 60);
                userCookie.setHttpOnly(true);
                userCookie.setPath("/");

                resp.addCookie(cookie);
                resp.addCookie(userCookie);
                printWriter.println("Logged In successfully");
            } else {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                printWriter.println(String.format("{\"error\": \"%s\"}", "Password is Incorrect"));
            }
        } catch (Exception e) {
            logger.error("Error during login: " + e.getMessage());
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            printWriter.println(String.format("{\"error\": \"%s\"}", e.getMessage()));
        }
    }


}

