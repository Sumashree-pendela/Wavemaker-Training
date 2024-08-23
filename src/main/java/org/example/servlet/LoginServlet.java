package org.example.servlet;


import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.exception.LoginNotFoundException;
import org.example.model.Login;
import org.example.service.LoginService;
import org.example.service.impl.LoginServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);

    LoginService loginService = new LoginServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Gson gson = new Gson();

        BufferedReader reader = req.getReader();
        StringBuilder stringBuilder = new StringBuilder();
        PrintWriter printWriter = resp.getWriter();
        String line;
        Login login = null;

        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }

        String jsonString = stringBuilder.toString();
        Login reqLogin = gson.fromJson(jsonString, Login.class);

        //verify is this valid user or not
        try {
            login = loginService.getByUserName(reqLogin.getUserName());
        } catch (LoginNotFoundException e) {
            logger.error("Invalid userName: {}", e.getMessage());
            printWriter.println("Invalid UserName");
        }

        //check if password is same
        try {
            if (login.getPassword().equals(reqLogin.getPassword())) {
                UUID uuid = UUID.randomUUID();
                String cookieValue = uuid.toString();


                HttpSession httpSession = req.getSession(true);
                Cookie cookie = new Cookie("loginCookie", cookieValue);
                httpSession.setAttribute("loginCookie", cookieValue);

                cookie.setMaxAge(2*60);
                cookie.setHttpOnly(true);
                cookie.setPath("/");
                resp.addCookie(cookie);
                printWriter.println("Logged In successfully");
            }
        } catch (Exception e) {
            logger.error("Password doesn't match");
            printWriter.println("Wrong Password");
        }

    }

}
